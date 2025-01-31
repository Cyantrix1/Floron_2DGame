package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;


public class NPC_Wanderer extends Entity{

    public NPC_Wanderer(GamePanel gp){
        super(gp);

        Direction = "down";
        speed = 2;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 30;
        solidArea.height = 30;
        getImage();
        setDialogue();
    }


    // get player image
    public void getImage(){

        up1 = setUp("player/GrimUp", gp.tileSize, gp.tileSize);
        up2 = setUp("player/GrimUp2", gp.tileSize, gp.tileSize);
        down1 = setUp("player/GrimDown", gp.tileSize, gp.tileSize);
        down2 = setUp("player/GrimDown2", gp.tileSize, gp.tileSize);
        left1 = setUp("player/GrimLeft", gp.tileSize, gp.tileSize);
        left2 = setUp("player/GrimLeft2", gp.tileSize, gp.tileSize);
        right1 = setUp("player/GrimRight", gp.tileSize, gp.tileSize);
        right2 = setUp("player/GrimRight2", gp.tileSize, gp.tileSize);

    }
    public void setDialogue(){
        dialogues[0] = "Hello, I'm a Grim Reaper!";
        dialogues[1] = "I come to those who are about to \ndie.";
        dialogues[2] = "Then I take their souls!";
        dialogues[3] = "Don't worry!, they go somewhere \nnice";
        dialogues[4] = "Goodbye.";
    }
    public void setAction(){
        if(onPath == true){
            int goalCol = 10;
            int goalRow = 25;
            // player position follow
            // int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            // int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;

            searchPath(goalCol, goalRow);
        }
        else{
            actionLockCounter++;

            if(actionLockCounter == 120){

                Random random = new Random();
                int i = random.nextInt(100)+1;

                if(i <= 25){
                    Direction =  "up";

                }
                if(i > 25 && i <= 50){
                    Direction =  "down";

                }
                if(i > 50 && i <= 75){
                    Direction =  "left";

                }
                if(i > 75 && i <= 100){
                    Direction =  "right";

                }
                actionLockCounter = 0;
            }
        }


    }
    public void speak() {

        super.speak();
        onPath = true;
    }


}
