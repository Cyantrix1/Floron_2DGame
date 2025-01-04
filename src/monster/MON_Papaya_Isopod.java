package monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class MON_Papaya_Isopod extends Entity {

    public MON_Papaya_Isopod(GamePanel gp) {
        super(gp);
        type = 2;
        name = "Papaya Isopod";
        speed = 1;
        maxLife = 4;
        life = maxLife;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();


    }

    public void getImage() {
        up1 = setUp("monster/Papaya_Isopod_Idle_Normal");
        up2 = setUp("monster/Papaya_Isopod_Idle_Normal_1");
        down1 = setUp("monster/Papaya_Isopod_Idle_Normal");
        down2 = setUp("monster/Papaya_Isopod_Idle_Normal_1");
        right1 = setUp("monster/Papaya_Isopod_Idle_Normal");
        right2 = setUp("monster/Papaya_Isopod_Idle_Normal_1");
        left1 = setUp("monster/Papaya_Isopod_Idle_Normal");
        left2 = setUp("monster/Papaya_Isopod_Idle_Normal_1");
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

    }
}
