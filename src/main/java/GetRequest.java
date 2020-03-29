import java.io.FileNotFoundException;

public class GetRequest {
    static byte[] returnPage(String request) throws Throwable {
        String requestSplit[] = request.split(" ");
        HttpCodes answer = HttpCodes.OK;
        byte[] contentFile = new byte[0];
        String filename = requestSplit[1].substring(1);

        try{
            FileReaderService fileReaderService = new FileReaderService(filename);
            contentFile = fileReaderService.getContentFile();

            ImageReaderService imageReaderService = new ImageReaderService(filename);
            contentFile = imageReaderService.read();
        }catch(FileNotFoundException exception){
            answer = HttpCodes.NOT_FOUND;
        }

        String response = "HTTP/1.1 " + answer + " OK\r\n" +
                "Server: YarServer/2010-09-09\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + contentFile.length + "\r\n" +
                "Connection: close\r\n\r\n";
        byte[] mass = new byte[response.getBytes().length + contentFile.length];
        System.arraycopy(response.getBytes(), 0, mass, 0, response.getBytes().length);
        System.arraycopy(contentFile, 0, mass, response.getBytes().length, contentFile.length);

        return mass;
    }
}
