import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class HttpServer {

    public static void main(String[] args) throws Throwable {
        ServerSocket ss = new ServerSocket(8080);
        while (true) {
            Socket s = ss.accept();
            System.err.println("Client accepted");
            new Thread(new SocketProcessor(s)).start();
        }
    }

    private static class SocketProcessor implements Runnable {

        private Socket s;
        private InputStream is;
        private OutputStream os;
        private BufferedOutputStream dataOut;

        private SocketProcessor(Socket s) throws Throwable {
            this.s = s;
            this.is = s.getInputStream();
            this.os = s.getOutputStream();
        }

        public void run() {
            try {
                List<String> request = readInputHeaders();
                String requestSplit[] = request.get(0).split(" ");
                if(requestSplit[0].equals("GET")) {
                    System.out.println(request);
                    ContentResponse response = new ContentResponse(Response.returnPage(request.get(0)));
                    os.write(response.getResponse().getBytes());
                    os.write(response.getContent());
                    os.flush();
                }else {

                }
            } catch (Throwable t) {
                /*do nothing*/
            } finally {
                try {
                    s.close();
                } catch (Throwable t) {
                    /*do nothing*/
                }
            }
            System.err.println("Client processing finished");
        }

        private void writeResponse(String s) throws Throwable {
            String response = "HTTP/1.1 200 OK\r\n" +
                    "Server: YarServer/2009-09-09\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: " + s.length() + "\r\n" +
                    "Connection: close\r\n\r\n";
            String result = response + s;
            System.out.println(result);
            os.write(result.getBytes());
            os.flush();
        }

        private List<String> readInputHeaders() throws Throwable {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            List<String> request = new ArrayList<String>();
            while(true) {
                String line = br.readLine();
                request.add(line);
                if(!br.ready()/*line != null || line.trim().length() != 0*/) {
                    break;
                }
            }
            return request;
        }
    }
}
