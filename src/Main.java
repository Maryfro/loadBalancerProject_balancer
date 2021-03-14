import java.io.IOException;

public class Main {
    private static final String DEFAULT_PROPS_PATH = "config/application.props";
    private static final String BALANCER_UDP_PORT_KEY = "balancer.udp.port";
    private static final String GATEWAY_UDP_PORT_KEY = "gateway.udp.port";
    private static final String GATEWAY_HOST_KEY = "gateway.host";
    private static final String CLEANER_TIMESTAMP_KEY = "cleaner.timestamp";
    private static final String SENDER_TIMESTAMP_KEY = "sender.timestamp";


    public static void main(String[] args) throws IOException {
        String propsPath = args.length > 0 ? args[0] : DEFAULT_PROPS_PATH;
        ApplicationProperties properties = new ApplicationProperties(propsPath);

        int balancerUdpPort = Integer.parseInt(properties.getProperty(BALANCER_UDP_PORT_KEY));
        int gatewayUdpPort = Integer.parseInt(properties.getProperty(GATEWAY_UDP_PORT_KEY));
        String gatewayHost = properties.getProperty(GATEWAY_HOST_KEY);
        int cleanerTimestamp = Integer.parseInt(properties.getProperty(CLEANER_TIMESTAMP_KEY));
        int senderTimestamp = Integer.parseInt(properties.getProperty(SENDER_TIMESTAMP_KEY));

        ServerSource serverSource = new ServerSource();
        LoadReceiver loadReceiver = new LoadReceiver(balancerUdpPort, serverSource);
        OptimalPortSender sender = new OptimalPortSender(gatewayUdpPort, gatewayHost, serverSource, senderTimestamp);
        Cleaner cleaner = new Cleaner(cleanerTimestamp, serverSource);
        new Thread(loadReceiver).start();
        new Thread(sender).start();
        new Thread(cleaner).start();
    }
}
