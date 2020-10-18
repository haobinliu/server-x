package bhserver.http.connection;

import bhserver.http.request.DefaultHttpRequest;
import bhserver.http.request.HttpRequest;

import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

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

public class HttpCollection implements Callable<HttpRequest> {

    private int state;

    private Socket conn;

    public HttpCollection(Socket conn){
        this.conn = conn;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Socket getConn() {
        return conn;
    }
    public void setConn(Socket conn) {
        this.conn = conn;
    }

    @Override
    public HttpRequest call() throws Exception {
        InputStream is  = conn.getInputStream();
        byte[] httpRequestStream = new byte[1024];
        int read = is.read(httpRequestStream);
        StringBuilder request = new StringBuilder();
        if (read != -1) {
            for (byte b:httpRequestStream) {
                request.append((char)b);
            }
        }
        return DefaultHttpRequest.parse(request.toString());
    }
}
