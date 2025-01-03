package objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends SuperObject{

    GamePanel gp;
    public OBJ_Heart(GamePanel gp){
        this.gp = gp;
        name = "Heart";
        try{
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/Full_Heart.png"));
            image1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/Half_Heart.png"));
            image2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/Empty_heart.png"));
            image = uTool.scaleImage(image,gp.tileSize, gp.tileSize);
            image1 = uTool.scaleImage(image1,gp.tileSize, gp.tileSize);
            image2 = uTool.scaleImage(image2,gp.tileSize, gp.tileSize);

        }catch(IOException e){
            e.printStackTrace();
        }
    }


}
