package entity;

import main.GamePanel;

import java.util.Random;


public class NPC_Wanderer extends Entity{

    public NPC_Wanderer(GamePanel gp){
        super(gp);

        Direction = "down";
        speed = 1;
        getImage();
        setDialogue();
    }


    // get player image
    public void getImage(){

        up1 = setUp("player/GrimUp");
        up2 = setUp("player/GrimUp2");
        down1 = setUp("player/GrimDown");
        down2 = setUp("player/GrimDown2");
        left1 = setUp("player/GrimLeft");
        left2 = setUp("player/GrimLeft2");
        right1 = setUp("player/GrimRight");
        right2 = setUp("player/GrimRight2");

    }
    public void setDialogue(){
        dialogues[0] = "Hello, I'm a Grim Reaper!";
        dialogues[1] = "I come to those who are about to \ndie.";
        dialogues[2] = "Then I take their souls!";
        dialogues[3] = "Don't worry!, they go somewhere \nnice";
        dialogues[4] = "Goodbye.";
    }
    public void setAction(){

        actionLockCounter ++;

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
    public void speak() {
        super.speak();
    }


}