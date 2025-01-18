package objects;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends Entity {


    public OBJ_Heart(GamePanel gp){
        super(gp);
        name = "Heart";
        image =setUp("objects/stats/Full_Heart", gp.tileSize, gp.tileSize);
        image1 = setUp("objects/stats/Half_Heart", gp.tileSize, gp.tileSize);
        image2 = setUp("objects/stats/Empty_heart", gp.tileSize, gp.tileSize);

    }


}
