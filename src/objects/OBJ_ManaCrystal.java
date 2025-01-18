package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity {
    GamePanel gp;

    public OBJ_ManaCrystal(GamePanel gp){
        super(gp);
        this.gp = gp;
        name = "Mana";
        image =setUp("objects/stats/manacrystal_blank", gp.tileSize, gp.tileSize);
        image1 = setUp("objects/stats/manacrystal_full", gp.tileSize, gp.tileSize);

    }


}
