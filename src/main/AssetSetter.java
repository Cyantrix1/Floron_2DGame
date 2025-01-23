package main;

import entity.NPC_Wanderer;
import monster.MON_Cappuccino_Isopod;
import monster.MON_Papaya_Isopod;
import monster.MON_RubberDucky_Isopod;
import monster.MON_TEST;
import objects.*;
import tile_iteractive.IT_DryTree;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        int mapNum = 0;
        int i = 0;
        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*25;
        gp.obj[mapNum][i].worldY = gp.tileSize*19;
        i++;
        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*26;
        gp.obj[mapNum][i].worldY = gp.tileSize*19;
        i++;
        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*27;
        gp.obj[mapNum][i].worldY = gp.tileSize*19;
        i++;
        gp.obj[mapNum][i] = new OBJ_OP_Axe(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*30;
        gp.obj[mapNum][i].worldY = gp.tileSize*19;
        i++;
        gp.obj[mapNum][i] = new OBJ_Blue_Shield(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*31;
        gp.obj[mapNum][i].worldY = gp.tileSize*19;
        i++;
        gp.obj[mapNum][i] = new OBJ_Potion_Health(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*32;
        gp.obj[mapNum][i].worldY = gp.tileSize*19;
        i++;
        gp.obj[mapNum][i] = new OBJ_ManaCrystal(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*37;
        gp.obj[mapNum][i].worldY = gp.tileSize*32;
        i++;
        gp.obj[mapNum][i] = new OBJ_Heart(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*38;
        gp.obj[mapNum][i].worldY = gp.tileSize*32;
        i++;
        gp.obj[mapNum][i] = new OBJ_ChopAxe(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*29;
        gp.obj[mapNum][i].worldY = gp.tileSize*19;
        i++;

    }


    public void setNPC(){
        int mapNum = 0;
        int i = 0;
        gp.npc[mapNum][i] = new NPC_Wanderer(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 27;
        gp.npc[mapNum][i].worldY = gp.tileSize * 23;
    }
    public void setMonster(){
        int mapNum = 0;
        int i = 0;
        gp.monster[mapNum][i] = new MON_Papaya_Isopod(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*25;
        gp.monster[mapNum][i].worldY = gp.tileSize*21;
        i++;
        gp.monster[mapNum][i] = new MON_Papaya_Isopod(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*26;
        gp.monster[mapNum][i].worldY = gp.tileSize*22;
        i++;
        gp.monster[mapNum][i] = new MON_Papaya_Isopod(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*28;
        gp.monster[mapNum][i].worldY = gp.tileSize*22;
        i++;
        gp.monster[mapNum][i] = new MON_Cappuccino_Isopod(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*40;
        gp.monster[mapNum][i].worldY = gp.tileSize*22;
        i++;
        gp.monster[mapNum][i] = new MON_RubberDucky_Isopod(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*42;
        gp.monster[mapNum][i].worldY = gp.tileSize*22;

    }
    public void setiTile(){
        int mapNum = 0;
        int i = 0;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 10, 10); i++;

    }
}
