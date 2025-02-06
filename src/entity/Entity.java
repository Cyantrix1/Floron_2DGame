package entity;
import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;


// this class is the super class that all other entities will implement
public class Entity {
    GamePanel gp;
    // describe their x and y location
    public int worldX, worldY;
    // how fast they move
    public int speed;

    // show the images
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    // direction string
    public String Direction = "down";

    // these two are to simulate movement while idle
    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    // override the rectangle based on weapon
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    String dialogues[] = new String[20];
    int dialogueIndex = 0;
    public BufferedImage image, image1, image2;
    public boolean collision = false;
    public boolean alive = true;
    public boolean dying = false;
    int dyingCounter =0;
    boolean hpBarOn = false;
    int hpBarCounter = 0;

    public boolean knockBack = false;
    // life

    public boolean onPath = false;
    boolean attacking = false;

    // COUNTER
    public int shootAvailableCounter = 0;
    int knockBackCounter = 0;
    // CHARACTER STATS
    public String name;
    public int defaultSpeed;
    public int maxMana;
    public int maxLife;
    public int mana;
    public int ammo;
    public int life;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;
    public Entity currentLight;

    // ITEM ATTRIBUTES
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;
    public int value;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int inventorySize = 20;
    public int price;
    public int knockBackPower = 0;
    public boolean stackable = false;
    public int amount = 1;
    public int lightRadius;

    // TYPE
    public int type; // 0 = player 1 = npc 2 = monster
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield =5;
    public final int type_consumable = 6;
    public final int type_pickupOnly = 7;
    public final int type_obstacle = 8;
    public final int type_light = 9;


    public Entity(GamePanel gp){
        this.gp = gp;
    }
    public int getLeftX(){
        return worldX + solidArea.x;
    }
    public int getRightX(){
        return worldX + solidArea.x + solidArea.width;
    }
    public int getTopY(){
        return worldY + solidArea.y;
    }
    public int getBottomY(){
        return worldY + solidArea.y + solidArea.height;
    }
    public int getCol(){
        return (worldX + solidArea.x)/gp.tileSize;
    }
    public int getRow(){
        return (worldY + solidArea.y)/gp.tileSize;
    }

    public void checkDrop(){

    }
    public void dropItem(Entity droppedItem){
        for (int i = 0; i < gp.obj[1].length; i++){
            if(gp.obj[gp.currentMap][i] == null){
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX;
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }
    public Color getParticleColor(){
        Color color = null;
        return color;
    }
    public int getParticleSize(){
        int size = 0; // 6 pixels
        return size;
    }
    public int getParticleSpeed(){
        int speed = 0;
        return speed;
    }
    public int getParticleMaxLife(){
        int maxLife = 0;
        return maxLife;
    }
    public void generateParticle(Entity generator, Entity target){
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particles p1 = new Particles(gp, target, color, size, speed, maxLife, -2, -1);
        Particles p2 = new Particles(gp, target, color, size, speed, maxLife, 2, -1);
        Particles p3 = new Particles(gp, target, color, size, speed, maxLife, -2, 1);
        Particles p4 = new Particles(gp, target, color, size, speed, maxLife, 2, 1);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }

    public void setAction(){}
    public void damageReaction(){}
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

    public void interact(){

    }

    public boolean use(Entity entity){
        return false;
    }

    public void checkCollision(){
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkEntity(this, gp.iTile);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);
        if(this.type == type_monster && contactPlayer == true){
            damagePlayer(attack);
        }

    }

    public void update(){

        if(knockBack == true){
            checkCollision();
            if(collisionOn == true){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
            else if(collisionOn == false){
                switch(gp.player.Direction){
                    case "up": worldY -= speed;break;
                    case "down": worldY += speed;break;
                    case "left": worldX -= speed;break;
                    case "right": worldX += speed;break;
                }
            }

            knockBackCounter++;
            // the bigger the number the bigger the knockback
            if(knockBackCounter == 10){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
        }
        else {
            setAction();
            checkCollision();


            if (collisionOn == false) {
                switch(Direction){
                    case "up": worldY -= speed;break;
                    case "down": worldY += speed;break;
                    case "left": worldX -= speed;break;
                    case "right": worldX += speed;break;
                }
            }
        }

        spriteCounter++;
        if(spriteCounter > 24){
            if(spriteNum ==1){
                spriteNum = 2;;
            }
            else if( spriteNum ==2){
                spriteNum =1;
            }
            spriteCounter = 0;
        }
        // This needs to be oustide of key if statement!
        if (invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 40){
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(shootAvailableCounter < 30){
            shootAvailableCounter++;
        }
    }
    public void damagePlayer(int attack){
        if(gp.player.invincible == false){
            //we can give damage
            gp.playSoundEffect(5);
            int damage = attack - gp.player.defense;
            if(damage < 0){
                damage = 0;
            }
            gp.player.life -= damage;

            gp.player.life -= 1;
            gp.player.invincible = true;
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
                    if(spriteNum == 1){image = up1;}
                    if(spriteNum ==2){image = up2;}
                    break;
                case "down":
                    if(spriteNum == 1){image = down1;}
                    if(spriteNum ==2){image = down2;}
                    break;
                case "left":
                    if(spriteNum == 1){image = left1;}
                    if(spriteNum ==2){image = left2;}
                    break;
                case "right":
                    if(spriteNum == 1){image = right1;}
                    if(spriteNum ==2){image = right2;}
                    break;
            }

            //MONSTER HEALTH
            if(type == 2 && hpBarOn == true){
                double oneScale = (double)gp.tileSize/maxLife;
                double hpBarValue = oneScale*life;

                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-1, screenY-16,gp.tileSize+2, 12);

                g2.setColor(new Color(255,0,30));
                g2.fillRect(screenX, screenY -15, (int)hpBarValue, 10);

                hpBarCounter++;
                if(hpBarCounter > 600){
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }



            if(invincible ==true){
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2,0.4F);

            }
            if(dying == true){
                dyingAnimation(g2);
            }

            g2.drawImage(image, screenX, screenY,null);
            changeAlpha(g2, 1F);
        }
    }
    public void dyingAnimation(Graphics2D g2){
        dyingCounter++;

        int i = 5;

        if(dyingCounter <= i){changeAlpha(g2, 0f);}
        if(dyingCounter > i && dyingCounter <= i*2){changeAlpha(g2, 1f);}
        if(dyingCounter > i*2 && dyingCounter <= i*3){changeAlpha(g2, 0f);}
        if(dyingCounter > i*3 && dyingCounter <= i*4){changeAlpha(g2, 1f);}
        if(dyingCounter > i*4 && dyingCounter <= i*5){changeAlpha(g2, 0f);}
        if(dyingCounter > i*5 && dyingCounter <= i*6){changeAlpha(g2, 1f);}
        if(dyingCounter > i*6 && dyingCounter <= i*7){changeAlpha(g2, 0f);}
        if(dyingCounter > i*7 && dyingCounter <= i*8){changeAlpha(g2, 1f);}
        if(dyingCounter > i*8){
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));

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
    public void searchPath(int goalCol, int goalRow){
        int startCol = (worldX + solidArea.x)/gp.tileSize;
        int startRow = (worldY + solidArea.y)/gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if(gp.pFinder.search() == true){
            //
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;
            // Entitiy's solidArea Position
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY+solidArea.y + solidArea.height;

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                Direction = "up";
            }
            else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX +gp.tileSize){
                Direction = "down";
            }
            else if (enTopY >= nextY && enBottomY <  nextY + gp.tileSize){
                if(enLeftX > nextX){
                    Direction = "left";
                }
                if(enLeftX < nextX){
                    Direction = "right";
                }
            }
            else if(enTopY> nextY && enLeftX > nextX){
                Direction = "up";
                checkCollision();
                if(collisionOn == true){
                    Direction = "left";
                }
            }
            else if(enTopY > nextY && enLeftX < nextX){
                Direction = "up";
                checkCollision();
                if(collisionOn == true){
                    Direction = "right";
                }
            }
            else if(enTopY < nextY && enLeftX > nextX){
                Direction = "down";
                if(collisionOn == true){
                    Direction = "left";
                }
            }
            else if(enTopY < nextY && enLeftX < nextX){
                Direction = "down";
                if(collisionOn == true){
                    Direction = "right";
                }
            }
            // If it reaches the goal, stop the search
            // omit this if you want the entity to follow you
          //  int nextCol = gp.pFinder.pathList.get(0).col;
           // int nextRow = gp.pFinder.pathList.get(0).row;
           // if(nextCol == goalCol && nextRow == goalRow){
             //   onPath = false;
           // }
        }
    }
    public int getDetected(Entity user, Entity target[][], String targetName){
        int index = 999;

        //
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch(user.Direction){
            case "up": nextWorldY = user.getTopY()-1; break;
            case "down": nextWorldY = user.getBottomY()+1;break;
            case "left": nextWorldX = user.getLeftX()-1; break;
            case "right": nextWorldX = user.getRightX()+1; break;
        }
        int col = nextWorldX/gp.tileSize;
        int row = nextWorldY/gp.tileSize;

        for(int i = 0; i < target[1].length; i++){
            if(target[gp.currentMap][i] != null){
                if (target[gp.currentMap][i].getCol() == col
                        && target[gp.currentMap][i].getRow() == row
                        && target[gp.currentMap][i].name.equals(targetName)) {
                    index = i;
                    break;

                }
            }
        }
        return index;
    }
}
