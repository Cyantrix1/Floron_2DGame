package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_BronzeCoin extends Entity {
    GamePanel gp;
    public OBJ_BronzeCoin(GamePanel gp){
        super(gp);
        this.gp = gp;

        type = type_pickupOnly;
        name = "Bronze Coin";
        value = 1;
        down1 = setUp("objects/consumables/coin_bronze", gp.tileSize, gp.tileSize);


    }
    public boolean use(Entity entity){
        gp.playSoundEffect(1);
        gp.ui.addMessage("Coin +" + value);
        gp.player.coin += value;
        return true;
    }
}
