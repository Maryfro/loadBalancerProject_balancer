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
        DatagramSocket udpSocket;
        try {
            udpSocket = new DatagramSocket(udp_port);
        } catch (SocketException e) {
            e.printStackTrace();
            return;
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
        String host = packetIn.getAddress().getHostName();
        System.out.println(host);
        String loadAndPort = new String(dataIn, 0, packetIn.getLength());
        String[] loadAndPortArr = loadAndPort.split(":");
        int load = Integer.parseInt(loadAndPortArr[0]);
        //System.out.println(load);
        int serverPort = Integer.parseInt(loadAndPortArr[1]);
        System.out.println(serverPort);
        serverSource.update(new ServerData(host, serverPort, load, System.currentTimeMillis()),
                load);
    }
}
