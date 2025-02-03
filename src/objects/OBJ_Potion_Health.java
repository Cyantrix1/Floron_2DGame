package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Health extends Entity {
    GamePanel gp;

    public OBJ_Potion_Health(GamePanel gp){
        super(gp);
        this.gp=gp;
        type = type_consumable;
        name = "Health Potion";
        value = 5;
        down1 = setUp("objects/consumables/health_potion", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nA Health Potion\nHeals " + value + ".";
        price = 1;
        stackable = true;
    }
    public boolean use(Entity entity){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drink the " + name + "!\n" + "Your life has been recovered by " + value + ".";
        entity.life += value;
        gp.playSoundEffect(2);
        return true;
    }
}
