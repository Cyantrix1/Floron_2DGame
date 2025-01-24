package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_OP_Axe extends Entity{

    public OBJ_OP_Axe(GamePanel gp){
        super(gp);
        type = type_axe;
        name = "OP Axe";
        down1 = setUp("objects/weapons/OP_Axe", gp.tileSize, gp.tileSize);
        attackValue = 5;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]\nAn overpowered Axe \nEvil doers beware!";
        price = 75;
    }

}
