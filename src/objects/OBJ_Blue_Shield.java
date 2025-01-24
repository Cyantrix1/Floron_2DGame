package objects;

import entity.Entity;
import main.GamePanel;
public class OBJ_Blue_Shield extends Entity{
    public OBJ_Blue_Shield(GamePanel gp){
        super(gp);
        type = type_shield;
        name = "Blue Shield";
        down1 = setUp("objects/weapons/Blue_Shield", gp.tileSize, gp.tileSize);
        defenseValue = 2;
        description = "[" + name + "]\nA Blue Shield";
        price = 20;
    }


}
