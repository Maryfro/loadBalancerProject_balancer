import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class LoadReceiver implements Runnable {
    private int udp_port;
    private final int PACKET_SIZE = 1024;
    ServerSource serverSource;

    public LoadReceiver(int udp_port, ServerSource serverSource) {
        this.udp_port = udp_port;
        this.serverSource = serverSource;
    }

    @Override
    public void run() {
        DatagramSocket udpSocket = null;
        try {
            udpSocket = new DatagramSocket(udp_port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        byte[] dataIn = new byte[PACKET_SIZE];
        DatagramPacket packetIn = new DatagramPacket(dataIn, PACKET_SIZE);
        while (true) {
            try {
                udpSocket.receive(packetIn);
            } catch (IOException e) {
                e.printStackTrace();
            }
            getServerData(packetIn, dataIn);
        }
    }

    void getServerData(DatagramPacket packetIn, byte[] dataIn) {
        int serverPort = packetIn.getPort();
        String host = packetIn.getAddress().getHostName();
        String load = new String(dataIn, 0, packetIn.getLength());
        serverSource.update(new ServerData(host, serverPort, Integer.parseInt(load), System.currentTimeMillis()),
                Integer.parseInt(load));
    }
}
