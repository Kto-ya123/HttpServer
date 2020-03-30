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
                    System.out.println(request);
                    ContentResponse response = new ContentResponse(Response.returnPage(request.get(0)));
                    os.write(response.getResponse().getBytes());
                    os.write(response.getContent());
                    os.flush();
                }else if(requestSplit[0].equals("POST")) {
                    String str = "<html><body>\r\n";
                    boolean paramEx = false;
                    Thread.sleep(1000 / 100);
                    for (int i = 0; i < request.size(); i++) {
                        if(i == 13 || request.get(i) == null || request.get(i).equals("")){
                            continue;
                        }
                        if(paramEx){
                            paramEx = false;
                            str += request.get(i).substring(1, request.get(i).length() - 1) + "</span>";
                        }
                        if(request.get(i).substring(0, 19).equals("Content-Disposition")){
                            paramEx = true;
                            String parameter = request.get(i).substring(request.get(i).indexOf('"') + 1, request.get(i).length()-1);
                            str += "<span>" + parameter + ": ";
                        }
                    }
                    str += "</body></html>";
                    writeResponse(str, HttpCodes.OK);
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

        private void writeResponse(String s, HttpCodes httpCodes) throws Throwable {
            String response = "HTTP/1.1 "+ httpCodes +" \r\n" +
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
