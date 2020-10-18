package bhserver.http.request;

import java.util.Arrays;
import java.util.HashMap;
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
public class DefaultHttpRequest implements HttpRequest {
    private String method;
    private String uri;
    private String version;
    private byte[] body;
    private Map<String,String> headers;
    @Override
    public String getMethod() {
        return method;
    }
    @Override
    public String getUri() {
        return uri;
    }
    @Override
    public String getVersion() {
        return version;
    }
    @Override
    public byte[] getBody() {
        return body;
    }
    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public void setMethod(String method) {
        this.method = method;
    }
    @Override
    public void setUri(String uri) {
        this.uri = uri;
    }
    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
    @Override
    public void setBody(byte[] body) {
        this.body = body;
    }
    public static HttpRequest parse(String request) {
        HttpRequest httpRequest = new DefaultHttpRequest();
        String[] httpInfo = request.split(Symbol.CONTENT_SEPARATE_SIGN);
        if (httpInfo.length==0){
            return null;
        }
        HashMap<String,String> headers = new HashMap<>(12);
        String[] content  = httpInfo[0].split(Symbol.LINE_SEPARATE_SIGN);
        // http first row of a http request
        String[] firstRow = content[0].split(Symbol.SPACE_SEPARATE_SIGN);
        // GET /uri http/1.1
        httpRequest.setMethod (firstRow[0]);
        httpRequest.setUri    (firstRow[1]);
        httpRequest.setVersion(firstRow[2]);
        for (int i = 1; i < content.length;i++){
            String[] keyValue = content[i].split(Symbol.KV_SEPARATE_SIGN);
            headers.put(keyValue[0],keyValue[1]);
        }
        if (httpInfo.length > 1){
            httpRequest.setBody(httpInfo[1].getBytes());
        }
        httpRequest.setHeaders(headers);
        return httpRequest;
    }


    @Override
    public String toString() {
        return "DefaultHttpRequest{" +
                "method='" + method + '\'' +
                ", uri='" + uri + '\'' +
                ", version='" + version + '\'' +
                ", body=" + Arrays.toString(body) +
                ", headers=" + headers +
                '}';
    }
}
