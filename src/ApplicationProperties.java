import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ApplicationProperties {

    Properties properties;

    ApplicationProperties(String filepath) throws IOException {
        properties = new Properties();
        properties.load(new FileReader(filepath));
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
