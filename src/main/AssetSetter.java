package main;

import entity.NPC_Wanderer;
import monster.MON_Cappuccino_Isopod;
import monster.MON_Papaya_Isopod;
import monster.MON_RubberDucky_Isopod;
import objects.*;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        int i = 0;
        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = gp.tileSize*25;
        gp.obj[i].worldY = gp.tileSize*19;
        i++;
        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = gp.tileSize*26;
        gp.obj[i].worldY = gp.tileSize*19;
        i++;
        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = gp.tileSize*27;
        gp.obj[i].worldY = gp.tileSize*19;
        i++;
        gp.obj[i] = new OBJ_OP_Axe(gp);
        gp.obj[i].worldX = gp.tileSize*30;
        gp.obj[i].worldY = gp.tileSize*19;
        i++;
        gp.obj[i] = new OBJ_Blue_Shield(gp);
        gp.obj[i].worldX = gp.tileSize*31;
        gp.obj[i].worldY = gp.tileSize*19;
        i++;
        gp.obj[i] = new OBJ_Potion_Health(gp);
        gp.obj[i].worldX = gp.tileSize*32;
        gp.obj[i].worldY = gp.tileSize*19;
        i++;

    }


    public void setNPC(){
        gp.npc[0] = new NPC_Wanderer(gp);
        gp.npc[0].worldX = gp.tileSize * 27;
        gp.npc[0].worldY = gp.tileSize * 23;
    }
    public void setMonster(){
        int i = 0;
        gp.monster[i] = new MON_Papaya_Isopod(gp);
        gp.monster[i].worldX = gp.tileSize*25;
        gp.monster[i].worldY = gp.tileSize*21;
        i++;
        gp.monster[i] = new MON_Papaya_Isopod(gp);
        gp.monster[i].worldX = gp.tileSize*26;
        gp.monster[i].worldY = gp.tileSize*22;
        i++;
        gp.monster[i] = new MON_Papaya_Isopod(gp);
        gp.monster[i].worldX = gp.tileSize*28;
        gp.monster[i].worldY = gp.tileSize*22;
        i++;
        gp.monster[i] = new MON_Cappuccino_Isopod(gp);
        gp.monster[i].worldX = gp.tileSize*40;
        gp.monster[i].worldY = gp.tileSize*22;
        i++;
        gp.monster[i] = new MON_RubberDucky_Isopod(gp);
        gp.monster[i].worldX = gp.tileSize*42;
        gp.monster[i].worldY = gp.tileSize*22;


    }
}
