import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PostResponse {
    private static Set<String> accessible = new HashSet<String>();
    static ContentResponse returnPage(List<String> request) throws IOException {
        accessible.add("first");
        accessible.add("second");
        accessible.add("third");
        String requestSplit[] = request.get(0).split(" ");
        String path = requestSplit[1].substring(1);
        if(!accessible.contains(path)){
            return DefaultResponses.getNotFoundPage();
        }

        String str = "<html><body>\r\n";
        boolean paramEx = false;
        for (int i = 0; i < request.size(); i++) {
            if(!request.get(i).equals("")) {
                if (paramEx) {
                    paramEx = false;
                    str += request.get(i) + "</span><br>";
                } else if (request.get(i).length() >= 19 && request.get(i).substring(0, 19).equals("Content-Disposition")) {
                    paramEx = true;
                    String parameter = request.get(i).substring(request.get(i).indexOf('"') + 1, request.get(i).length() - 1);
                    str += "<span>" + parameter + ": ";
                }
            }
        }
        str += "</body></html>";
        ContentResponse contentResponse = new ContentResponse();
        contentResponse.setContentType("text/html");
        contentResponse.setContent(str.getBytes());
        contentResponse.setResponse(HttpCodes.CREATED);

        return contentResponse;
    }
}
