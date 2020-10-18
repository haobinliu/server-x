package bhserver.http.response;

import bhserver.http.request.Symbol;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
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

public class DefaultHttpResponse implements HttpResponse {


    private Socket conn;

    public Socket getConn() {
        return conn;
    }

    public void setConn(Socket conn) throws IOException {
        setOut(conn.getOutputStream());
        this.conn = conn;
    }

    private OutputStream out;
    /** http/1.1 200 OK
     * the first row of response
     */
    private String topRow;
    private Map<String,String> headers;
    private byte[] body;
    @Override
    public OutputStream getOut() {
        return out;
    }
    @Override
    public void setOut(OutputStream out) {
        this.out = out;
    }
    @Override
    public String getTopRow() {
        return topRow;
    }
    @Override
    public void setTopRow(String topRow) {
        this.topRow = topRow;
    }
    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }
    @Override
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
    @Override
    public byte[] getBody() {
        return body;
    }
    @Override
    public void setBody(byte[] body) {
        this.body = body;
    }

    @Override
    public void service() throws IOException {
        out.write(topRow.getBytes());
        out.write(Symbol.LINE_SEPARATE_SIGN_BYTES);
        for (Map.Entry<String,String> entry : headers.entrySet()){
            out.write(entry.getKey().getBytes());
            out.write(Symbol.KV_SEPARATE_SIGN_BYTES);
            out.write(entry.getValue().getBytes());
            out.write(Symbol.LINE_SEPARATE_SIGN_BYTES);
        }
        out.write(Symbol.LINE_SEPARATE_SIGN_BYTES);
        out.write(body);
    }
}
