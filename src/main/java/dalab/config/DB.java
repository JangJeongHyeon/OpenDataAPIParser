package dalab.config;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by Jang Jeong Hyeon on 2017-08-05.
 */
public class DB {
    private static Properties properties = new Properties();
    private static String filePath = "src/main/resources/DBConfig.properties";

    public DB(){
        try{
            FileInputStream fileReader = new FileInputStream(filePath);
            properties.load(new BufferedInputStream(fileReader));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // return database configuration data
    public  String getDatabasePath() {return properties.getProperty("DriverPath");}
    public  String getURL() {return properties.getProperty("URL");}
    public  String getUSER() {return properties.getProperty("USER");}
    public  String getPassword() {return properties.getProperty("PASSWORD");}
}
