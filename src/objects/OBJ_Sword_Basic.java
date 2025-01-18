package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Basic extends Entity{


    public OBJ_Sword_Basic(GamePanel gp){
        super(gp);
        type = type_sword;
        name = "Basic Sword";
        down1 = setUp("objects/weapons/Sword", gp.tileSize, gp.tileSize);
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name + "]\nA Basic Sword";
    }


}
