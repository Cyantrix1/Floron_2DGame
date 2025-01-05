package entity;
import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


// this class is the super class that all other entities will implement
public class Entity {
    GamePanel gp;
    // describe their x and y location
    public int worldX, worldY;
    // how fast they move

    // buffered images
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2,
            attackLeft1, attackLeft2, attackRight1, attackRight2;
    public BufferedImage image, image1, image2;
    String dialogues[] = new String[20];
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;


    // COUNTERS
    public int actionLockCounter = 0;
    public int spriteCounter = 0;
    public int invincibleCounter = 0;


    // STATES
    public boolean invincible = false;
    public String Direction = "down";
    public boolean collisionOn = false;
    int dialogueIndex = 0;
    public boolean collision = false;
    public int spriteNum = 1;
    boolean attacking = false;



    // PLAYER STATES
    public int type; // 0 = player 1 = npc 2 = monster
    public String name;
    public int speed;
    public int maxLife;
    public int life;


    public Entity(GamePanel gp){
        this.gp = gp;
    }

    public void setAction(){

    }
    public void speak(){
        if(dialogues[dialogueIndex] == null){
            dialogueIndex = 4;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch(gp.player.Direction){
            case"up":
                Direction = "down";
                break;
            case"down":
                Direction = "up";
                break;
            case "left":
                Direction = "right";
                break;
            case "right":
                Direction = "left";
                break;
        }
    }


    public void update(){
        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);
        if(this.type == 2 && contactPlayer == true){
            if(gp.player.invincible == false){
                //we can give damage
                gp.player.life -= 1;
                gp.player.invincible = true;
            }
        }

        if (collisionOn == false) {
            switch(Direction){
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }

        spriteCounter++;
        if(spriteCounter > 10){
            if(spriteNum ==1){
                spriteNum = 2;;
            }
            else if( spriteNum ==2){
                spriteNum =1;
            }
            spriteCounter = 0;
        }


    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize> gp.player.worldX - gp.player.screenX &&
                worldX -gp.tileSize< gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize> gp.player.worldY - gp.player.screenY &&
                worldY -gp.tileSize< gp.player.worldY+gp.player.screenY){
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
            g2.drawImage(image, screenX, screenY,gp.tileSize,gp.tileSize,null);

        }
    }

    public BufferedImage setUp(String imageName, int width, int height){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(imageName+".png"));
            image = uTool.scaleImage(image, width, height);
        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }
}
