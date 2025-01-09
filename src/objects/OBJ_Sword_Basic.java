package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Basic extends Entity{


    public OBJ_Sword_Basic(GamePanel gp){
        super(gp);

        name = "Basic Sword";
        down1 = setUp("objects/Sword", gp.tileSize, gp.tileSize);
        attackValue = 1;
        description = "[" + name + "]\nA Basic Sword";
    }


}
