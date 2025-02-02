package objects;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends Entity {

    GamePanel gp;
    Entity loot;
    boolean opened = false;
    public OBJ_Chest(GamePanel gp, Entity loot){
        super(gp);
        this.gp = gp;
        this.loot = loot;
        type = type_obstacle;
        name = "Chest";
        image = setUp("objects/structure/Chest", gp.tileSize, gp.tileSize);
        image1 = setUp("objects/structure/ChestOpen", gp.tileSize, gp.tileSize);
        down1 = image;
        collision = true;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;


    }

    public void interact(){
        gp.gameState = gp.dialogueState;

        if(opened == false){
            gp.playSoundEffect(3);
            StringBuilder sb = new StringBuilder();
            sb.append("You opened the chest and find a " + loot.name + "!");

            if(gp.player.inventory.size() == gp.player.inventorySize){
                sb.append("\n...But you cannot carry any more!");
            }
            else{
                sb.append("\nYou obtain the " + loot.name + "!");
                gp.player.inventory.add(loot);
                down1 = image1;
                opened = true;
            }
            gp.ui.currentDialogue = sb.toString();
        }
        else{
            gp.ui.currentDialogue = "It's empty";
        }
    }
}
