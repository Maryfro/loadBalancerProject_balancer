import java.util.Objects;

public class ServerData {
   private  String host;
    private int port;
    private int load;
   private long lastUpdate;

    public ServerData(String host, int port, int load,long lastUpdate) {
        this.host = host;
        this.port = port;
        this.load = load;
        this.lastUpdate = lastUpdate;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getLoad() {
        return load;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerData that = (ServerData) o;
        return port == that.port &&
                host.equals(that.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port);
    }

    @Override
    public String toString() {
        return "ServerData{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", load=" + load +
                ", lastUpdate=" + lastUpdate +
                '}';
    }


}
