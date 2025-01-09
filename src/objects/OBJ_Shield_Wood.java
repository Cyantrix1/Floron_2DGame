package objects;

import entity.Entity;
import main.GamePanel;
public class OBJ_Shield_Wood extends Entity{

    public OBJ_Shield_Wood(GamePanel gp){
        super(gp);

        name = "Basic Shield";
        down1 = setUp("objects/Shield", gp.tileSize, gp.tileSize);
        defenseValue = 1;
        description = "[" + name + "]\nA Wooden Shield";
    }

}
