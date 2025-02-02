package objects;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends Entity {
    GamePanel gp;

    public OBJ_Heart(GamePanel gp){
        super(gp);
        this.gp = gp;
        type = type_pickupOnly;
        name = "Heart";
        value = 2;
        down1 = setUp("objects/stats/Full_Heart", gp.tileSize, gp.tileSize);
        image =setUp("objects/stats/Full_Heart", gp.tileSize, gp.tileSize);
        image1 = setUp("objects/stats/Half_Heart", gp.tileSize, gp.tileSize);
        image2 = setUp("objects/stats/Empty_heart", gp.tileSize, gp.tileSize);

    }
    public boolean use(Entity entity){
        gp.playSoundEffect(2);
        gp.ui.addMessage("Life +" + value);
        entity.life += value;
        return true;
    }
}
