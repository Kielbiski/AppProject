package quest;

public class Card {
    private String name = "";
    private String imageFilename = "";

    public Card(String paramName, String paramImageFilename){
        name = paramName;
        imageFilename = paramImageFilename;
    }
    public String getName(){
        return name;
    }
    public String getImageFilename(){
        return imageFilename;
    }
}
