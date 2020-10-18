package bhserver.http.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ++++ ______ @author       liubinhao   ______             ______
 * +++/     /|                         /     /|           /     /|
 * +/_____/  |                       /_____/  |         /_____/  |
 * |     |   |                      |     |   |        |     |   |
 * |     |   |                      |     |   |        |     |   |
 * |     |   |                      |     |   |        |     |   |
 * |     |   |                      |     |   |________|     |   |
 * |     |   |                      |     |  /         |     |   |
 * |     |   |                      |     |/___________|     |   |
 * |     |   |                      |     |            |     |   |
 * |     |   |                      |     |____________|     |   |
 * |     |   | ___________________  |     |   |        |     |   |
 * |     |  /                  / |  |     |   |        |     |   |
 * |     |/ _________________/   |  |     |   |        |     |   |
 * |                         |  /   |     |  /         |     |  /
 * |_________________________|/b    |_____|/           |_____|/
 *
 * @date 2020/9/23
 */

public class PropertiesReader {

    private static final String mimePropertiesLocation =
            "D:\\company\\myself\\app-server\\" +
            "file-server\\src\\bhserver\\http\\contentype.properties";
    public static String readProperties(String key) {
        System.out.println(","+key);
        Properties prop = new Properties();
        try { prop.load(new FileInputStream(mimePropertiesLocation));
                return prop.get(key).toString();
        } catch (IOException e) {
                e.printStackTrace();
        }
        return null;
    }
}
