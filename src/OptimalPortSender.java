import java.io.IOException;
import java.net.*;

public class OptimalPortSender implements Runnable {
    private int gateway_port;
    private String gateway_host;
    private ServerSource source;
    private int timestampToSend;


    public OptimalPortSender(int gateway_port, String gateway_host, ServerSource source, int timestampToSend) {
        this.gateway_port = gateway_port;
        this.gateway_host = gateway_host;
        this.source = source;
        this.timestampToSend = timestampToSend;
    }

    @Override
    public void run() {
        try {
            InetAddress inetAddress = InetAddress.getByName(gateway_host);
            DatagramSocket udpSocket = new DatagramSocket();
            while (true) {
                if (source.getBest() == null)
                    continue;
                String portAndHost = source.getBest().getHost() + " : " + source.getBest().getPort();
                byte[] outputData = portAndHost.getBytes();
                DatagramPacket packetOut = new DatagramPacket(outputData,
                        outputData.length,
                        inetAddress,
                        gateway_port);
                udpSocket.send(packetOut);
                Thread.sleep(timestampToSend);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
