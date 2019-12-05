package Common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadProperty {
    String value = null;
    public String getPropertyValue(String fileName, String key) {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/" + fileName));
        } catch (FileNotFoundException file) {
            System.out.println("Failed to find property file: " + file.getMessage());
        } catch (IOException IO) {
            System.out.println("Failed to open property file" + IO.getMessage());
        }
        value = prop.getProperty(key);
        return value;
    }
}
