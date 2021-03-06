package bhserver.http.request;

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

public class Symbol {
    public static final String CONTENT_SEPARATE_SIGN = "\r\n\r\n";
    public static final String LINE_SEPARATE_SIGN    = "\r\n";
    public static final String KV_SEPARATE_SIGN      = ":";
    public static final String SPACE_SEPARATE_SIGN   = "\\s+";

    public static final byte[] CONTENT_SEPARATE_SIGN_BYTES = "\r\n\r\n".getBytes();
    public static final byte[] LINE_SEPARATE_SIGN_BYTES    = "\r\n".getBytes();
    public static final byte[] KV_SEPARATE_SIGN_BYTES      = ":".getBytes();



    public static final String TOP_ROW_200 = "HTTP/1.1 200 OK";
    public static final String TOP_ROW_206 = "HTTP/1.1 206 Partial Content";
    /**
     * http headers
     */
    public static final String IF_RANGE = "Range";
    public static final String SERVER_X = "Server";
    public static final String RES_DATE = "Date";
    public static final String EXPIRES  = "Expires";

    public static final String ACCEPT_RANGES  = "Accept-Ranges";
    public static final String CONTENT_TYPE   = "Content-Type";
    public static final String CONTENT_RANGE  = "Content-Range";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONNECTION = "Connection";


    /**
     * the value of header
     */
    public static final String KEEP_ALIVE = "Keep-Alive";


}
