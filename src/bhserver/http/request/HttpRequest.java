package bhserver.http.request;

import java.util.Map;

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
public interface HttpRequest {

    /**
     * Get Post Put...
     * @return the request method
     */
    String getMethod();

    void setMethod(String method);

    /**
     * /uri
     * @return the request uri
     */
    String getUri();

    void setUri(String uri);

    /**
     * http/1.1
     * @return the version of a http request
     */
    String getVersion();

    void setVersion(String version);

    /**
     * e.g
     * content-type: text/html
     * server: Liu's server
     * @return the headers of http request
     */
    Map<String, String> getHeaders();

    void setHeaders(Map<String, String> headers);

    /**
     * the body of a request
     * @return body
     */
    byte[] getBody();

    void setBody(byte[] body);



}
