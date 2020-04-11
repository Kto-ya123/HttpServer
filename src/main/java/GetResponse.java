import java.io.FileNotFoundException;

public class GetResponse {
    static ContentResponse returnPage(String request) throws Throwable {
        String requestSplit[] = request.split(" ");
        ContentResponse contentResponse = new ContentResponse();
        String filename = requestSplit[1].substring(1);

        try{
            String contentType = filename.substring(filename.indexOf('.') + 1);
            if(contentType.equals("jpg")) {
                contentResponse.setContentType("image/" + contentType);
                ImageReaderService imageReaderService = new ImageReaderService(filename);
                contentResponse.setContent(imageReaderService.read());
                contentResponse.setResponse(HttpCodes.OK);
            }else {
                if(contentType.equals("js")) {
                    contentType = "javascript";
                }else if(contentType.equals("svg")) {
                    contentType = "html";
                }
                contentResponse.setContentType("text/" + contentType);
                FileReaderService fileReaderService = new FileReaderService(filename);
                contentResponse.setContent(fileReaderService.getContentFile());
                contentResponse.setResponse(HttpCodes.OK);
            }
        }catch(FileNotFoundException exception){

        }

        return contentResponse;
    }
}
