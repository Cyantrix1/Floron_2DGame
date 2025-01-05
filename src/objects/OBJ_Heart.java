package objects;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends Entity {


    public OBJ_Heart(GamePanel gp){
        super(gp);
        name = "Heart";
        image =setUp("objects/Full_Heart", gp.tileSize, gp.tileSize);
        image1 = setUp("objects/Half_Heart", gp.tileSize, gp.tileSize);
        image2 = setUp("objects/Empty_heart", gp.tileSize, gp.tileSize);

    }


}
