package entity;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import objects.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends Entity{

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public boolean attackCanceled = false;


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
        getPlayerAttackImage();
        setItems();
    }

    // get player image
    public void getPlayerImage(){

        up1 = setUp("player/GrimUp", gp.tileSize, gp.tileSize);
        up2 = setUp("player/GrimUp2", gp.tileSize, gp.tileSize);
        down1 = setUp("player/GrimDown", gp.tileSize, gp.tileSize);
        down2 = setUp("player/GrimDown2", gp.tileSize, gp.tileSize);
        left1 = setUp("player/GrimLeft", gp.tileSize, gp.tileSize);
        left2 = setUp("player/GrimLeft2", gp.tileSize, gp.tileSize);
        right1 = setUp("player/GrimRight", gp.tileSize, gp.tileSize);
        right2 = setUp("player/GrimRight2", gp.tileSize, gp.tileSize);

    }
    public void getPlayerAttackImage(){
        if(currentWeapon.type == type_sword){
            attackUp1 = setUp("player/grim_attack_up_1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setUp("player/grim_attack_up_2", gp.tileSize, gp.tileSize*2);
            attackDown1 = setUp("player/grim_attack_down_1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setUp("player/grim_attack_down_2", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setUp("player/grim_attack_left_1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setUp("player/grim_attack_left_2", gp.tileSize*2, gp.tileSize);
            attackRight1 = setUp("player/grim_attack_right_1", gp.tileSize*2, gp.tileSize);
            attackRight2 = setUp("player/grim_attack_right_2", gp.tileSize*2, gp.tileSize);
        }
        if(currentWeapon.type == type_axe){
            attackUp1 = setUp("player/grim_attack_up_1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setUp("player/grim_attack_up_2", gp.tileSize, gp.tileSize*2);
            attackDown1 = setUp("player/grim_attack_down_1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setUp("player/grim_attack_down_2", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setUp("player/grim_attack_left_1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setUp("player/grim_attack_left_2", gp.tileSize*2, gp.tileSize);
            attackRight1 = setUp("player/grim_attack_right_1", gp.tileSize*2, gp.tileSize);
            attackRight2 = setUp("player/grim_attack_right_2", gp.tileSize*2, gp.tileSize);
        }

    }


    // default starting location
    public void setDefaultValues(){
        worldX = gp.tileSize * 25;
        worldY = gp.tileSize * 25;
        defaultSpeed = 4;
        speed = defaultSpeed;
        Direction = "down";

        // PLAYER STATUS
        level = 1;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 500;
        maxLife = 6;
        maxMana = 4;
        ammo = 10;
        mana = maxMana;
        life = maxLife;
        currentWeapon = new OBJ_Sword_Basic(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        projectile = new OBJ_Fireball(gp);
        //projectile = new OBJ_Rock(gp);
        attack = getAttack();
        defense = getDefense();

    }
    public void setDefaultPositions(){
        worldX = gp.tileSize * 25;
        worldY = gp.tileSize * 25;
        Direction = "down";
    }
    public void restoreLifeAndMana(){
        life = maxLife;
        mana = maxMana;
        invincible = false;
    }
    // default items the player gets
    public void setItems(){

        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Key(gp));

    }
    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }
    public int getDefense(){
        return defense = dexterity*currentShield.defenseValue;
    }

    // update where the user moves
    public void update(){

        // could add an if(keyH.upPressed == true || etc...etc...etc... if you want the sprite not to move

        if(attacking == true){
            attacking();
        }
        else if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true
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

            // CHECK INTERACTIVE TILE COLLISION
            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
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
            if(keyH.enterPressed == true && attackCanceled == false){
                attacking = true;
                spriteCounter = 0;
            }
            attackCanceled = false;
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
        // if key is pressed and projectile one at a time
        if(gp.keyH.shootKeyPressed == true && projectile.alive == false
                && shootAvailableCounter == 30 && projectile.haveResource(this) == true){
            // SET DEFAULT coordinates, direction and user
            projectile.set(worldX, worldY, Direction, true, this);
            // SUBTRACT THE COST
            projectile.subtractResource(this);

            // add it to the list
            for (int i = 0; i < gp.projectile[1].length; i++){
                if(gp.projectile[gp.currentMap][i] == null){
                    gp.projectile[gp.currentMap][i] = projectile;
                    break;
                }
            }
            shootAvailableCounter = 0;
            // play fireball soundeffect
            gp.playSoundEffect(9);
        }
        // This needs to be oustide of key if statement!
        if (invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(shootAvailableCounter < 30){
            shootAvailableCounter++;
        }
        if(life > maxLife){
            life = maxLife;
        }
        if(mana > maxMana){
            mana = maxMana;
        }
        if(life <= 0){
            gp.gameState = gp.gameOverState;
            gp.ui.commandNum = -1;
            gp.stopMusic();
            gp.playSoundEffect(10);
        }

    }

    public void attacking(){
        spriteCounter++;
        if(spriteCounter <= 5){
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25){
            spriteNum=2;

            // save the current world x y and solid area
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // adjust player's worldx/y for the attack area
            switch(Direction){
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }
            // attack area becomes solid area
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            // check monster collision with the updated world X, worldY, and solid area
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex, attack, currentWeapon.knockBackPower);

            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
            damageInteractiveTile(iTileIndex);

            int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
            damageProjectile(projectileIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if(spriteCounter > 25){
            spriteNum =1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    public void pickUpObject(int i){
        if(i != 999){
            // PICKUP ONLY ITEMS
            if(gp.obj[gp.currentMap][i].type == type_pickupOnly){
                gp.obj[gp.currentMap][i].use(this);
                gp.obj[gp.currentMap][i] = null;
            }
            // INVENTORY ITEMS
            else{
                String text;
                if(inventory.size() != inventorySize){
                    inventory.add(gp.obj[gp.currentMap][i]);
                    gp.playSoundEffect(1);
                    text = "Got a " + gp.obj[gp.currentMap][i].name +"!";

                }
                else{
                    text = "You can't carry anymore!";
                }
                gp.ui.addMessage(text);
                gp.obj[gp.currentMap][i] = null;
            }
            }
    }
    public void interactNPC(int i){
        if(gp.keyH.enterPressed == true){
            if(i != 999){
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentMap][i].speak();

            }
            else{
                gp.playSoundEffect(6);
                attacking = true;
            }
        }
    }
    public void contactMonster(int i){
        if(i != 999){
            if(invincible == false && gp.monster[gp.currentMap][i].dying == false){
                gp.playSoundEffect(5);
                int damage = gp.monster[gp.currentMap][i].attack - defense;
                if(damage < 0){
                    damage = 0;
                }
                life -= damage;
                invincible = true;
            }

        }
    }
    public void damageMonster(int i, int attack, int knockBackPower){
        if(i != 999){
            if(gp.monster[gp.currentMap][i].invincible == false){
                gp.playSoundEffect(4);
                if(knockBackPower >0){
                    knockBack(gp.monster[gp.currentMap][i], knockBackPower);
                }

                int damage = attack - gp.monster[gp.currentMap][i].defense;
                if(damage < 0){
                    damage = 0;
                }

                gp.monster[gp.currentMap][i].life -= damage;
                gp.ui.addMessage(damage + " damage!");
                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();

                if(gp.monster[gp.currentMap][i].life <= 0){
                    gp.monster[gp.currentMap][i].dying = true;
                    gp.ui.addMessage("Killed the " + gp.monster[gp.currentMap][i].name + "!");
                    gp.ui.addMessage("Exp + " + gp.monster[gp.currentMap][i].exp + "!");
                    exp += gp.monster[gp.currentMap][i].exp;
                    checkLevelUp();
                }
            }
        }
    }
    public void knockBack(Entity entity, int knockBackPower){
        entity.Direction = Direction;
        entity.speed += knockBackPower;
        entity.knockBack = true;
    }
    public void damageInteractiveTile(int i){
        if(i != 999 && gp.iTile[gp.currentMap][i].destructible == true && gp.iTile[gp.currentMap][i].isCorrectItem(this) == true
        && gp.iTile[gp.currentMap][i].invincible == false){
            gp.iTile[gp.currentMap][i].playSoundEffect();
            gp.iTile[gp.currentMap][i].life--;
            gp.iTile[gp.currentMap][i].invincible = true;
            // generate particle
            generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);
            if(gp.iTile[gp.currentMap][i].life == 0){
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
            }
        }
    }
    public void damageProjectile(int i ){
        if (i != 999){
            gp.projectile[gp.currentMap][i].alive = false;
            generateParticle(gp.projectile[gp.currentMap][i], gp.projectile[gp.currentMap][i]);
        }
    }
    public void checkLevelUp(){
        if(exp >= nextLevelExp){
            level++;
            nextLevelExp = nextLevelExp *2;
            maxLife +=2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();

            gp.playSoundEffect(7);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = ("You are level " + level + " now!\n");
        }
    }
    public void selectItem(){
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);

        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);
            if(selectedItem.type == type_sword || selectedItem.type == type_axe){
                currentWeapon = selectedItem;
                attack=getAttack();
                getPlayerAttackImage();
            }
            if(selectedItem.type == type_shield){
                currentShield = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_consumable){
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        switch(Direction){
            case "up":
                if (attacking == false) {
                    if(spriteNum == 1){image = up1;}
                    if(spriteNum == 2){image = up2;}
                }
                if(attacking == true){
                    tempScreenY = screenY - gp.tileSize;
                    if(spriteNum == 1){image = attackUp1;}
                    if(spriteNum == 2){image = attackUp2;}
                }
                break;
            case "down":
                if (attacking == false) {
                    if(spriteNum == 1){image = down1;}
                    if(spriteNum == 2){image = down2;}
                }
                if(attacking == true){
                    if(spriteNum == 1){image = attackDown1;}
                    if(spriteNum == 2){image = attackDown2;}
                }
                break;
            case "left":
                if (attacking == false) {
                    if(spriteNum == 1){image = left1;}
                    if(spriteNum == 2){image = left2;}
                }
                if(attacking == true){
                    tempScreenX = screenX - gp.tileSize;
                    if(spriteNum == 1){image = attackLeft1;}
                    if(spriteNum == 2){image = attackLeft2;}
                }
                break;
            case "right":
                if (attacking == false) {
                    if(spriteNum == 1){image = right1;}
                    if(spriteNum == 2){image = right2;}
                }
                if(attacking == true){
                    if(spriteNum == 1){image = attackRight1;}
                    if(spriteNum == 2){image = attackRight2;}
                }
                break;
        }
        // make player a little transparaent when invincible
        if(invincible ==true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);

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

