package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Health extends Entity {
    GamePanel gp;
    int value = 5;
    public OBJ_Potion_Health(GamePanel gp){
        super(gp);
        this.gp=gp;
        type = type_consumable;
        name = "Health Potion";
        down1 = setUp("objects/consumables/health_potion", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nA Health Potion\nHeals " + value + ".";
    }
    public void use(Entity entity){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drink the " + name + "!\n" + "Your life has been recovered by " + value + ".";
        entity.life += value;
        if(gp.player.life > gp.player.maxLife){
            gp.player.life = gp.player.maxLife;
        }
        gp.playSoundEffect(2);
    }
}
