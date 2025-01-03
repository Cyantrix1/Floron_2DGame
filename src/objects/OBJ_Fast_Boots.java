package objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Fast_Boots extends SuperObject{
    GamePanel gp;
    public OBJ_Fast_Boots(GamePanel gp){
        this.gp = gp;
        name = "FastBoots";

        try{
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/FastBoots.png"));
            uTool.scaleImage(image,gp.tileSize, gp.tileSize);

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
