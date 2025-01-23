package main;

import java.awt.*;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][][];

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;


    public EventHandler(GamePanel gp){
        this.gp = gp;

        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;
        while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow){
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height= 2;

            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if(col == gp.maxWorldCol){
                col=0;
                row++;
                if(row == gp.maxWorldRow){
                    row = 0;
                    map++;
                }
            }
        }


    }

    public void checkEvent(){

        // Check if the player is 1 tile away from the last event
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);

        int distance = Math.max(xDistance, yDistance);
        if(distance > gp.tileSize){
            canTouchEvent = true;
        }

        if(canTouchEvent == true) {
            // NOTE ON MAP -1 FOR BOTH COL AND ROW FOR DESIRED TILE
            if(hit(0,26,20, "right") == true){ damagePit(gp.dialogueState);}
            // heal
            else if(hit(0,23, 20, "left") == true){healingPool(gp.dialogueState);}
            // TELEPORT
            else if(hit(0,29, 25, "right") == true){teleport(29, 25, gp.dialogueState);}
            // GO TO ANOTHER MAP
            else if(hit(0, 33, 20, "any")==true){teleportMap(1, 25, 25);}
            else if(hit(1, 25, 25, "any")==true){teleportMap(0, 33, 20);}

        }



    }

    public boolean hit(int map, int col, int row, String reqDirection){
        boolean hit = false;

        if(map == gp.currentMap){
            gp.player.solidArea.x = gp.player.worldX+ gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY+ gp.player.solidArea.y;
            eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;

            if(gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {
                if(gp.player.Direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                    hit = true;

                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;

                }
            }

            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;

        }

        return hit;
    }


    public void damagePit(int gameState){
        gp.gameState = gameState;
        gp.playSoundEffect(4);
        gp.ui.currentDialogue = "You fall into a pit!";
        gp.player.life -= 1;
      //  eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }

    public void healingPool(int gameState){

        if(gp.keyH.enterPressed == true){
            gp.player.attackCanceled = true;
            gp.gameState = gameState;
            gp.ui.currentDialogue = "You get FULL life/mana back!";
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            // reset monsters
            gp.aSetter.setMonster();
        }

    }
    public void teleport(int col, int row, int gameState){

        gp.gameState = gameState;
        gp.ui.currentDialogue = "Teleport!";
        gp.player.worldX = gp.tileSize*37;
        gp.player.worldY = gp.tileSize*10;
    }

    public void teleportMap(int map, int col, int row){
        gp.currentMap = map;
        gp.player.worldX = gp.tileSize*col;
        gp.player.worldY = gp.tileSize*row;
        previousEventX = gp.player.worldX;
        previousEventY = gp.player.worldY;
        canTouchEvent = false;
        gp.playSoundEffect(12);
    }


}
