package bhserver.http.server;

import bhserver.http.connection.HttpCollection;
import bhserver.http.request.HttpRequest;
import bhserver.http.resource.FileResource;
import bhserver.http.util.ShowLogo;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

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
public class QuuQer {

    ThreadFactory myThreadFactory = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r);
        }
    };

    ExecutorService pool = new ThreadPoolExecutor(1, 25,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), myThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    private boolean deamon = true;

    public void setDeamon(boolean deamon) {
        this.deamon = deamon;
    }

    private ServerSocket server;

    public QuuQer(int port) throws IOException {
        this.server = new ServerSocket(port);
    }

    public ServerSocket getServer() {
        return server;
    }

    public void setServer(ServerSocket server) {
        this.server = server;
    }

    public void start(){

        while (deamon){
            try {
                Socket socket = this.server.accept();

                HttpCollection req = new HttpCollection(socket);

                Future<HttpRequest> future = pool.submit(req);

                HttpRequest request = future.get();
                System.out.println("当前请求报文为:"+request);
                FileResource fileResource = new FileResource();
                fileResource.setConn(socket);
                fileResource.setRequest(request);
                Future<?> submit = pool.submit(fileResource);
            } catch (InterruptedException | ExecutionException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        QuuQer quuQer = new QuuQer(80);
        ShowLogo.printLogo();
        ShowLogo.startSuccess();
        quuQer.start();
    }
}
