import java.io.FileNotFoundException;
import java.io.IOException;

public class DefaultResponses {
    public static ContentResponse getNotFoundPage() throws IOException {
        return getPages("404.html", "text/html", HttpCodes.NOT_FOUND);
    }

    public static ContentResponse getBadOperationsPage() throws IOException {
        return getPages("501.html", "text/html", HttpCodes.NOT_IMPLEMENTED);
    }

    private static ContentResponse getPages(String filename, String contentType, HttpCodes httpCodes) throws IOException {
        ContentResponse contentResponse = new ContentResponse();
        contentResponse.setContentType(contentType);
        FileReaderService fileReaderService = new FileReaderService(filename);
        contentResponse.setContent(fileReaderService.getContentFile());
        contentResponse.setResponse(httpCodes);
        return contentResponse;
    }
}
