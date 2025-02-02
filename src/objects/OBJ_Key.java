package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {
    GamePanel gp;
    public OBJ_Key(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_consumable;
        name = "Key";
        down1 = setUp("objects/consumables/key", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nA Basic Key";
        price = 15;

    }
    public boolean use(Entity entity){
        gp.gameState = gp.dialogueState;
        int objIndex = getDetected(entity, gp.obj, "Door");

        if(objIndex != 999){
            gp.ui.currentDialogue = "You use the " + name + " and open the door";
            gp.playSoundEffect(3);
            gp.obj[gp.currentMap][objIndex] = null;
            return true;
        }
        else{
            gp.ui.currentDialogue = "You can't use that right now!";
            return false;
        }
    }
}
