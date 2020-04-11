public class ContentResponse {
    private String response;
    private byte[] content;
    private String contentType;

    public ContentResponse() {
    }

    public ContentResponse(ContentResponse contentResponse) {
        this.response = contentResponse.response;
        this.content = contentResponse.content;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(HttpCodes answer) {
        this.response = ("HTTP/1.1 " + answer.getCode() + " " + answer.getDescription() +"\r\n" +
                "Server: HttpServer/2020-03-30\r\n" +
                "Content-Type: " + this.getContentType() + "\r\n" +
                "Content-Length: " + this.getContent().length + "\r\n" +
                "Access-Control-Allow-Origin: localhost" +
                "Access-Control-Allow-Methods: GET, POST, OPTIONS" +
                "Connection: close\r\n\r\n");
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
