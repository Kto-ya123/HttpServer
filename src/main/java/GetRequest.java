import java.io.FileNotFoundException;

public class GetRequest {
    static String returnPage(String request) throws Throwable {
        String path = "C:/Users/Artur/eclipse-workspace/HttpServer/src/pages/";
        String requstSplit[] = request.split(" ");
        HttpCodes answer = HttpCodes.OK;
        String contentFile = new String();
        String filename = requstSplit[1].substring(1);

        try{
            FileReaderService fileReaderService = new FileReaderService(path + filename);
            contentFile = fileReaderService.getContentFile();
        }catch(FileNotFoundException exception){
            answer = HttpCodes.NOT_FOUND;
        }

        String response = "HTTP/1.1 " + answer + " OK\r\n" +
                "Server: YarServer/2009-09-09\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + contentFile.length() + "\r\n" +
                "Connection: close\r\n\r\n" +
                contentFile;

        return response;
    }
}
