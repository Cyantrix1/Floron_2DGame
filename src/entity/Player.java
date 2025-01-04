package entity;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

// extends entity
public class Player extends Entity{

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    // create the player that takes in gp and keyH (gp will say where the player is and keyH will be what the user inputs)
    // constructor
    public Player(GamePanel gp, KeyHandler keyH){

        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        // x, y, width, height
        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        // get starting location
        setDefaultValues();
        // get player image
        getPlayerImage();
    }

    // get player image
    public void getPlayerImage(){

        up1 = setUp("player/GrimUp");
        up2 = setUp("player/GrimUp2");
        down1 = setUp("player/GrimDown");
        down2 = setUp("player/GrimDown2");
        left1 = setUp("player/GrimLeft");
        left2 = setUp("player/GrimLeft2");
        right1 = setUp("player/GrimRight");
        right2 = setUp("player/GrimRight2");

    }


    // default starting location
    public void setDefaultValues(){
        worldX = gp.tileSize * 25;
        worldY = gp.tileSize * 25;
        speed = 4;
        Direction = "down";

        // PLAYER STATUS
        maxLife = 6;
        life = maxLife;

    }

    // update where the user moves
    public void update(){

        // could add an if(keyH.upPressed == true || etc...etc...etc... if you want the sprite not to move

        if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true
        || keyH.enterPressed == true){
            if(keyH.upPressed == true){
                Direction = "up";
            }
            else if(keyH.downPressed == true){
                Direction = "down";
            }
            else if(keyH.leftPressed == true){
                Direction = "left";
            }else if(keyH.rightPressed == true){
                Direction = "right";
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // CHECK  MONSTER COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);
            // CHECK EVENT
            gp.eHandler.checkEvent();



            if (collisionOn == false && keyH.enterPressed == false) {
                switch(Direction){
                    case "up": worldY -= speed;break;
                    case "down": worldY += speed;break;
                    case "left": worldX -= speed;break;
                    case "right": worldX += speed;break;
                }
            }

            gp.keyH.enterPressed = false;

            spriteCounter++;
            if(spriteCounter > 12){
                if(spriteNum ==1){
                    spriteNum = 2;;
                }
                else if( spriteNum ==2){
                    spriteNum =1;
                }
                spriteCounter = 0;
            }
        }
        // This needs to be oustide of key if statement!
        if (invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }

    }
    public void pickUpObject(int i){
        if(i != 999){

        }
    }
    public void interactNPC(int i){
        if(i != 999){
            if(gp.keyH.enterPressed == true){
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }

    }
    public void contactMonster(int i){
        if(i != 999){
            if(invincible == false){
                life -= 1;
                invincible = true;
            }

        }
    }
    public void draw(Graphics2D g2){


        BufferedImage image = null;
        switch(Direction){
            case "up":
                image = up1;
                if(spriteNum == 1){
                    image = up1;

                }
                if(spriteNum ==2){
                    image = up2;
                }
                break;
            case "down":
                image = down1;
                if(spriteNum == 1){
                    image = down1;

                }
                if(spriteNum ==2){
                    image = down2;
                }
                break;
            case "left":
                image = left1;
                if(spriteNum == 1){
                    image = left1;

                }
                if(spriteNum ==2){
                    image = left2;
                }
                break;
            case "right":
                image = right1;
                if(spriteNum == 1){
                    image = right1;

                }
                if(spriteNum ==2){
                    image = right2;
                }
                break;
        }
        // make player a little transparaent when invincible
        if(invincible ==true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, screenX, screenY, null);

        // reset the transparaencey
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // SHOW COLLISION RECT
        g2.setColor(Color.red);
        g2.drawRect(screenX+solidArea.x, screenY+solidArea.y, solidArea.width, solidArea.height);

        //DEBUG (invincible)
        //g2.setFont(new Font("Arial", Font.PLAIN, 26));
        //g2.setColor(Color.white);
        //g2.drawString("Invincible:" + invincibleCounter, 10, 400);

    }




    }

