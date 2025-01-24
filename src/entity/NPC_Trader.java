package entity;

import main.GamePanel;
import objects.OBJ_Blue_Shield;
import objects.OBJ_Key;
import objects.OBJ_OP_Axe;
import objects.OBJ_Potion_Health;

import java.util.Random;


public class NPC_Trader extends Entity{
    GamePanel gp;
    public NPC_Trader(GamePanel gp){
        super(gp);
        this.gp = gp;
        Direction = "down";
        speed = 0;
        getImage();
        setDialogue();
        setItems();

        solidArea.x = 3;
        solidArea.y = 30;
        solidArea.width = 50;
        solidArea.height = 66;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }


    // get player image
    public void getImage(){

        up1 = setUp("npc/Sprite", gp.tileSize*2, gp.tileSize*2);
        up2 = setUp("npc/Sprite", gp.tileSize*2, gp.tileSize*2);
        down1 = setUp("npc/Sprite", gp.tileSize*2, gp.tileSize*2);
        down2 = setUp("npc/Sprite", gp.tileSize*2, gp.tileSize*2);
        left1 = setUp("npc/Sprite", gp.tileSize*2, gp.tileSize*2);
        left2 = setUp("npc/Sprite", gp.tileSize*2, gp.tileSize*2);
        right1 = setUp("npc/Sprite", gp.tileSize*2, gp.tileSize*2);
        right2 = setUp("npc/Sprite", gp.tileSize*2, gp.tileSize*2);

    }
    public void setDialogue(){
        dialogues[0] = "Hello, I'm a traveling merchant!";
        dialogues[1] = "I have wares if you have coin \n.";
        dialogues[2] = "Care to buy anything?";
        dialogues[3] = "What?! You ARE POOR!?!?";
        dialogues[4] = "Goodbye.";
    }

    public void speak() {
        super.speak();
        gp.gameState = gp.tradeState;
        gp.ui.trader = this;
    }

    public void setItems(){
        inventory.add(new OBJ_Potion_Health(gp));
        inventory.add(new OBJ_Potion_Health(gp));
        inventory.add(new OBJ_OP_Axe(gp));
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Blue_Shield(gp));

    }


}