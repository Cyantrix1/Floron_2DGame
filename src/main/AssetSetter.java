package main;

import entity.NPC_Wanderer;
import monster.MON_Papaya_Isopod;
import objects.OBJ_Chest;
import objects.OBJ_Door;
import objects.OBJ_Fast_Boots;
import objects.OBJ_Key;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){

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

    }
}
