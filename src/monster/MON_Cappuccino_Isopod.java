package monster;

import entity.Entity;
import main.GamePanel;
import objects.OBJ_Rock;

import java.util.Random;

public class MON_Cappuccino_Isopod extends Entity {
    GamePanel gp;
    public MON_Cappuccino_Isopod(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_monster;
        name = "Cappuccino Isopod";
        speed = 1;
        maxLife = 8;
        life = maxLife;
        attack = 10;
        defense = 2;
        exp = 4;
        projectile = new OBJ_Rock(gp);

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }
    public void getImage() {

        int i =5;
        up1 = setUp("monster/Cappuccino_Isopod/Capps_Left_1", gp.tileSize*3, gp.tileSize*3);
        up2 = setUp("monster/Cappuccino_Isopod/Capps_Left_2", gp.tileSize*3, gp.tileSize*3);
        down1 = setUp("monster/Cappuccino_Isopod/Capps_Right_1", gp.tileSize*3, gp.tileSize*3);
        down2 = setUp("monster/Cappuccino_Isopod/Capps_Right_2", gp.tileSize*3, gp.tileSize*3);
        right1 = setUp("monster/Cappuccino_Isopod/Capps_Right_1", gp.tileSize*3, gp.tileSize*3);
        right2 = setUp("monster/Cappuccino_Isopod/Capps_Right_2", gp.tileSize*3, gp.tileSize*3);
        left1 = setUp("monster/Cappuccino_Isopod/Capps_Left_1", gp.tileSize*3, gp.tileSize*3);
        left2 = setUp("monster/Cappuccino_Isopod/Capps_Left_2", gp.tileSize*3, gp.tileSize*3);
    }
    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;
            if (i <= 25) {
                Direction = "up";
            }
            if (i > 25 && i <= 50) {
                Direction = "down";
            }
            if (i > 50 && i <= 75) {
                Direction = "left";
            }
            if (i > 75 && i <= 100) {
                Direction = "right";
            }
            actionLockCounter = 0;

        }
        int i = new Random().nextInt(100)+1;
        if (i > 99 && projectile.alive == false && shootAvailableCounter == 30){
            projectile.set(worldX, worldY, Direction, true, this);
            gp.projectileList.add(projectile);
            shootAvailableCounter = 0;
        }
    }
    public void damageReaction(){
        actionLockCounter = 0;
        Direction = gp.player.Direction;
    }
}