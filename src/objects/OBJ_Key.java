package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {

    public OBJ_Key(GamePanel gp) {
        super(gp);

        name = "Key";
        down1 = setUp("objects/consumables/key", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nA Basic Key";

    }
}
