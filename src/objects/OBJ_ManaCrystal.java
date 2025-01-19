package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity {
    GamePanel gp;

    public OBJ_ManaCrystal(GamePanel gp){
        super(gp);
        this.gp = gp;
        name = "Mana";
        type = type_pickupOnly;
        value = 1;
        down1 = setUp("objects/stats/manacrystal_full", gp.tileSize, gp.tileSize);
        image =setUp("objects/stats/manacrystal_blank", gp.tileSize, gp.tileSize);
        image1 = setUp("objects/stats/manacrystal_full", gp.tileSize, gp.tileSize);

    }
    public void use(Entity entity){
        gp.playSoundEffect(2);
        gp.ui.addMessage("Mana +" + value);
        entity.mana += value;
    }


}
