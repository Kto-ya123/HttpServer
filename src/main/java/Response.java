import java.io.FileNotFoundException;

public class Response {
    static ContentResponse returnPage(String request) throws Throwable {
        String requestSplit[] = request.split(" ");
        HttpCodes answer = HttpCodes.OK;
        ContentResponse contentResponse = new ContentResponse();
        String filename = requestSplit[1].substring(1);

        try{
            String contentType = filename.substring(filename.indexOf('.') + 1);
            if(contentType.equals("jpg")) {
                contentResponse.setContentType("image/" + contentType);
                ImageReaderService imageReaderService = new ImageReaderService(filename);
                contentResponse.setContent(imageReaderService.read());
            }else {
                if(contentType.equals("js")) {
                    contentType = "javascript";
                }else if(contentType.equals("svg")) {
                    contentType = "html";
                }
                contentResponse.setContentType("text/" + contentType);
                FileReaderService fileReaderService = new FileReaderService(filename);
                contentResponse.setContent(fileReaderService.getContentFile());
            }


        }catch(FileNotFoundException exception){
            answer = HttpCodes.NOT_FOUND;
            contentResponse.setContentType("text/html");
            FileReaderService fileReaderService = new FileReaderService("404.html");
            contentResponse.setContent(fileReaderService.getContentFile());
        }

        contentResponse.setResponse("HTTP/1.1 " + answer + " OK\r\n" +
                "Server: HttpServer/2020-03-30\r\n" +
                "Content-Type: " + contentResponse.getContentType() + "\r\n" +
                "Content-Length: " + contentResponse.getContent().length + "\r\n" +
                "Access-Control-Allow-Origin: localhost" +
                "Access-Control-Allow-Methods: GET, POST, OPTIONS" +
                "Connection: close\r\n\r\n");
        return contentResponse;
    }
}
