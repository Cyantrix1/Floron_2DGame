package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_ChopAxe extends Entity{

    public OBJ_ChopAxe(GamePanel gp){
        super(gp);
        type = type_axe;
        name = "Basic Axe";
        down1 = setUp("objects/tiles_interact/axe", gp.tileSize, gp.tileSize);
        attackValue = 5;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]\nAn Axe for Chopping \nChop Chop Chop!";
    }

}
