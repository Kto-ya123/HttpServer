import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class HttpServer {
    public static void main(String[] args) throws Throwable {
        ServerSocket ss = new ServerSocket(8000);
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
                    ContentResponse response = new ContentResponse(GetResponse.returnPage(request.get(0)));
                    writeResponse(response);
                }else if(requestSplit[0].equals("POST") || requestSplit[0].equals("OPTIONS")) {
                    ContentResponse response = new ContentResponse(PostResponse.returnPage(request));
                    writeResponse(response);
                }else{
                    writeResponse(DefaultResponses.getBadOperationsPage());
                }
            } catch (Throwable t) {
                System.out.println(t.toString());
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

        private void writeResponse(ContentResponse response) throws Throwable {
            os.write(response.getResponse().getBytes());
            os.write(response.getContent());
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
