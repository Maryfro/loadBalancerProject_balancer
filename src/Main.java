
public class Main {
    public static void main(String[] args) {
        ServerSource serverSource = new ServerSource();
        LoadReceiver loadReceiver = new LoadReceiver(serverSource);
        OptimalPortSender sender = new OptimalPortSender(serverSource);
        Cleaner cleaner = new Cleaner(5000, serverSource);
        new Thread(loadReceiver).start();
        new Thread(sender).start();
        new Thread(cleaner).start();
    }
}
