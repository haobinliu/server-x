package bhserver.http.resource;

import bhserver.http.request.HttpRequest;
import bhserver.http.request.Symbol;
import bhserver.http.response.DefaultHttpResponse;
import bhserver.http.response.HttpResponse;
import bhserver.http.util.PropertiesReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.SQLOutput;
import java.util.Date;
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

public class FileResource implements Runnable{

    public final String RESOURCE_PATH = "D://image";

    private Socket conn;

    private HttpRequest request;

    private HttpResponse response;


    public Socket getConn() {
        return conn;
    }

    public void setConn(Socket conn) {
        this.conn = conn;
    }

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public HttpResponse getResponse() {
        return response;
    }

    public void setResponse(HttpResponse response) {
        this.response = response;
    }

    @Override
    public void run() {
        try {
            HttpResponse response = resourceHandle(this.request);
            this.response = response;
            response.service();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            closeResponse();
        }
    }

    void closeResponse() {
        if (conn!=null) {
            System.out.println("conn："+this.conn+"不为null");
            try {
                if (!response.getTopRow().equals(Symbol.TOP_ROW_206)) {
                    System.out.println("conn："+this.conn+"关闭");
                    this.conn.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    HttpResponse resourceHandle(HttpRequest request) throws IOException {
        HttpResponse response = new DefaultHttpResponse();
        response.setOut(this.conn.getOutputStream());
        response.setHeaders(new HashMap<>(12));
        Map<String, String> headers = request.getHeaders();
        if (headers.get(Symbol.IF_RANGE)!=null){
            response.setTopRow(Symbol.TOP_ROW_206);
            byte[] bytes = queryPartialResource(request,response);
            response.setBody(bytes);
        }
        else{
            response.setTopRow(Symbol.TOP_ROW_200);
            byte[] bytes = queryIntegerResource(request,response);
            response.setBody(bytes);
        }
        return response;
    }

    ByteBuffer resourceBuffer(HttpRequest request) throws IOException {
        String uri = request.getUri();
        File file = new File(RESOURCE_PATH + uri);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            try {
                ResourceNotFound.noSuchFile(conn.getOutputStream());

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }
        FileChannel channel = fis.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
        channel.read(buffer);
        buffer.rewind();
        return buffer;
    }

    byte[] queryIntegerResource(HttpRequest request,HttpResponse response) throws IOException {
        ByteBuffer buffer = resourceBuffer(request);

        response.getHeaders().put(Symbol.CONTENT_LENGTH,String.valueOf(buffer.capacity()));
        response.getHeaders().put(Symbol.CONTENT_TYPE, PropertiesReader.readProperties(getType(request.getUri())));
        response.getHeaders().put(Symbol.RES_DATE,new Date().toString());
        response.getHeaders().put(Symbol.SERVER_X,"server-lbh");
        System.out.println(buffer);
        return buffer.array();
    }

    byte[] queryPartialResource(HttpRequest request,HttpResponse response) throws IOException {
        ByteBuffer buffer = resourceBuffer(request);
        Map<String, String> headers = request.getHeaders();
        Range range = requestRange(headers, buffer.capacity());
        byte[] bytes = new byte[range.getOffset()-range.getStart()];
        buffer.get(bytes,range.getStart(),range.getOffset());
        response.getHeaders().put(Symbol.ACCEPT_RANGES,"bytes");
        response.getHeaders().put(Symbol.CONTENT_LENGTH,String.valueOf(buffer.capacity()));
        response.getHeaders().put(Symbol.CONTENT_TYPE, PropertiesReader.readProperties(getType(request.getUri())));
        response.getHeaders().put(Symbol.CONTENT_RANGE,range.getStart()+"-"+range.getOffset()+"/"+range.getOffset()+1);
        response.getHeaders().put(Symbol.CONNECTION,Symbol.KEEP_ALIVE);
        response.getHeaders().put(Symbol.RES_DATE,new Date().toString());
        response.getHeaders().put(Symbol.SERVER_X,"server-lbh");
        return bytes;
    }

    private static class Range{
        private int start;
        private int offset;
        private int size;

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            if (offset==0){
                offset = 1024*1024;
            }
            this.offset = offset;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    private Range requestRange(Map<String,String> headers,int size){
        int hasOffset = 2;
        Range range = new Range();
        range.setSize(size);
        String value = headers.get(Symbol.IF_RANGE);
        String[] split = value.split("\\-");
        System.out.println(split.length);
        range.setStart(Integer.parseInt(split[0].replace("bytes=","").trim()));
        //only the start
        if (split.length == hasOffset){
            range.setOffset(Integer.parseInt(split[1]));
        }else {
            range.setOffset(1024*1024);
        }
        return range;
    }

    /**
     * 获取请求的文件
     * @param uri 请求内容
     * @return 请求文件类型
     */
    public static String getType(String uri){
        System.out.println("uri:"+uri);
        String stringDot = ".";
        String dot = "\\.";
        String[] fileName;
        if (uri.contains(stringDot)) {
            fileName = uri.split(dot);
            System.out.println(fileName.length);
            return fileName[fileName.length - 1];
        }
        return null;
    }

//    public static void main(String[] args) {
//        String a = "abc=0";
//        System.out.println(a.replace("abc=",""));
//    }

}
