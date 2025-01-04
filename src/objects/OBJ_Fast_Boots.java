package objects;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Fast_Boots extends Entity {

    public OBJ_Fast_Boots(GamePanel gp){
        super(gp);
        name = "FastBoots";
        down1 = setUp("objects/FastBoots");

    }
}
