package main;

import entity.NPC_Wanderer;
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
        gp.npc[0].worldX = gp.tileSize * 23;
        gp.npc[0].worldY = gp.tileSize * 24;
    }
}