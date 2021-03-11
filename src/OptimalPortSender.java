import java.io.IOException;
import java.net.*;

public class OptimalPortSender implements Runnable {
    private final static int GATEWAY_PORT = 3001;
    // what is InetAddress here?
    private final static String UDP_HOST = "localhost";
    ServerSource source;

    public OptimalPortSender(ServerSource source) {
        this.source = source;
    }

    @Override
    public void run() {
        try {
            //is it known from gateway?
            InetAddress inetAddress = InetAddress.getByName(UDP_HOST);
            DatagramSocket udpSocket = new DatagramSocket();
            String portAndHost = source.getBest().getHost() + " : " + source.getBest().getPort();
            byte[] outputData = portAndHost.getBytes();
            DatagramPacket packetOut = new DatagramPacket(outputData,
                    outputData.length,
                    inetAddress,
                    GATEWAY_PORT);
            while (true) {
                Thread.sleep(100);
                udpSocket.send(packetOut);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
