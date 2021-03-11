public class Cleaner implements Runnable {
    int timestamp;
    ServerSource source;

    public Cleaner(int timestamp, ServerSource source) {
        this.timestamp = timestamp;
        this.source = source;
    }

    @Override
    public void run() {
        while (true) {
            source.removeUnused(timestamp);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
