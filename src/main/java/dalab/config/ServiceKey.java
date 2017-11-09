package dalab.config;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by Jang Jeong Hyeon on 2017-08-05.
 */
public class ServiceKey {
    private static Properties apiKey = new Properties();
    private static String path = "src/dalab.main/resources/APIKey.properties";

    public ServiceKey() {
        try {
            FileInputStream config = new FileInputStream(path);
            apiKey.load(new BufferedInputStream(config));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getAPIKey(final String apiName) {
        return apiKey.getProperty(apiName+"_API_KEY");
    }
}


