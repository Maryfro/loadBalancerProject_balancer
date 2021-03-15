import java.util.PriorityQueue;

public class ServerSource {
    PriorityQueue<ServerData> source = new PriorityQueue<>();

    synchronized void update(ServerData serverData, int load) {
        if (!source.contains(serverData)) {
            source.add(serverData);
        } else {
            serverData.setLoad(load);
            serverData.setLastUpdate(System.currentTimeMillis());
        }
    }

    synchronized ServerData getBest() {
        return source.stream().min((o1, o2) -> -(o1.getLoad() - o2.getLoad())).orElse(null);
    }

    synchronized void removeUnused(int millis) {
        source.removeIf(data -> System.currentTimeMillis() - data.getLastUpdate() > millis);
    }
}
