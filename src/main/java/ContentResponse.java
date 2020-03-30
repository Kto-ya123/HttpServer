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

    public void setResponse(String response) {
        this.response = response;
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
