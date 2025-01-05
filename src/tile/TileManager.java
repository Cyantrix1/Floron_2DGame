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
    // 2D array to visualize the map
    public int[][] mapTileNum;

    // constructor
    public TileManager(GamePanel gp) {
        this.gp = gp;
        // tile array
        tile = new Tile[10];
        // map array
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("maps/Floron_TileMap.txt");

    }

    public void getTileImage() {

            // set up each tile
            setUp(0, "Grass", false);
            setUp(1, "water", true);
            setUp(2, "wall", true);
            setUp(3, "Tree", true);
            setUp(4, "Dirt", false);

    }

    // when called, set up the tile
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
