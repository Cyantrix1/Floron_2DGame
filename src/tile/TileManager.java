package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ICC_ColorSpace;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    // tile array
    public Tile[] tile;
    // 2D arrray
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[40];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("maps/New_Map.txt");

    }

    public void getTileImage() {

            setUp(0, "Dirt", false);
            setUp(1, "earth", false);
            setUp(2, "floor01", false);
            setUp(3, "Grass", false);
            setUp(4, "grass00", false);
            setUp(5, "grass01", false);
        setUp(6, "hut", true);
        setUp(7, "road00", false);
        setUp(8, "road01", false);
        setUp(9, "road02", false);
        setUp(10, "road03", false);
        setUp(11, "road04", false);
        setUp(12, "road05", false);
        setUp(13, "road06", false);
        setUp(14, "road07", false);
        setUp(15, "road08", false);
        setUp(16, "road09", false);
        setUp(17, "road10", false);
        setUp(18, "road11", false);
        setUp(19, "road12", false);
        setUp(20, "table01", true);
        setUp(21, "Tree", true);
        setUp(22, "wall", true);
        setUp(23, "water", true);
        setUp(24, "water00", true);
        setUp(25, "water01", true);
        setUp(26, "water02", true);
        setUp(27, "water03", true);
        setUp(28, "water04", true);
        setUp(29, "water05", true);
        setUp(30, "water06", true);
        setUp(31, "water07", true);
        setUp(32, "water08", true);
        setUp(33, "water09", true);
        setUp(34, "water10", true);
        setUp(35, "water11", true);
        setUp(36, "water12", true);
        setUp(37, "water13", true);





    }

    public void setUp(int index, String imageName, boolean collision){

        UtilityTool uTool = new UtilityTool();

        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/"+ imageName +".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize,  gp.tileSize);
            tile[index].collision = collision;

        }catch(IOException e){
            e.printStackTrace();
        }

    }

    // method that reads in the world map in loadMap and saves it into our map tile
    public void loadMap(String file){
    try{
        InputStream is = getClass().getClassLoader().getResourceAsStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        int col = 0;
        int row = 0;

        while (col< gp.maxWorldCol && row < gp.maxWorldRow){
            String line = br.readLine();
            while(col<gp.maxWorldCol){
                String[] numbers = line.split(" ");

                int num = Integer.parseInt(numbers[col]);

                mapTileNum[col][row] = num;
                col++;
            }
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
        br.close();
    }
    catch(Exception e)

    {

    }
}
    // draw the map
    public void draw(Graphics2D g2){
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol<gp.maxWorldCol && worldRow < gp.maxWorldRow){

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol*gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize> gp.player.worldX - gp.player.screenX &&
                    worldX -gp.tileSize< gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize> gp.player.worldY - gp.player.screenY &&
                    worldY -gp.tileSize< gp.player.worldY+gp.player.screenY){

                g2.drawImage(tile[tileNum].image, screenX, screenY,null);

            }
            worldCol++;
            if(worldCol==gp.maxWorldCol){
                worldCol = 0;
                worldRow++;

            }
        }
    }
}
