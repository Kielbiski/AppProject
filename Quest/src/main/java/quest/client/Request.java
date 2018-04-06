package quest.client;

public class Request {
    private String name;
    private String attribute;

    public Request(String name, String attribute){
        this.name = name;
        this.attribute = attribute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String message) {
        this.attribute = message;
    }
}
