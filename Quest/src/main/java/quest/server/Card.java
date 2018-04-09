package quest.server;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quest.client.App;

public class Card {
    private static final Logger logger = LogManager.getLogger(App.class);

    public String name;
    public String imageFilename;
    Card(){}
    Card(String paramName, String paramImageFilename)
    {

        name = paramName;
        imageFilename = paramImageFilename;
        logger.info("Successfully called Card: "+ name+" constructor");

    }

    public String getName(){
        logger.info("Returning Card: "+name);
        return name;

    }

    public String getImageFilename(){

        logger.info("Returning Card: "+name+ " image");
        return imageFilename;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageFilename(String imageFilename) {
        this.imageFilename = imageFilename;
    }
}
