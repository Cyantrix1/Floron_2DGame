package main;

// import our .png files
import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile_iteractive.InteractiveTile;

// import needed libraries
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// GamePanel extends JPanel so it can use JPanel's implementation
// GamePanel implements Runnable
public class GamePanel extends JPanel implements Runnable{

    // variables
    final int orgTileSize = 16;                                           // set the tile size to 16 (16x12)
    final int scale = 3;                                                  //

    // public so other classes can retrieve it
    public final int tileSize = orgTileSize * scale;                      // tile size will equal 16 * 3 = 48 pixels
    public final int maxScreenCol = 20;                                   // max 16 col tiles
    public final int maxScreenRow = 12;                                   // max 12 row tiles
    public final int screenWidth = tileSize * maxScreenCol;               // screen width = 48 x 16 = 768 pixels
    public final int screenHeight = tileSize * maxScreenRow;              // screen height = 48 * 12 = 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FULL SCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;


    // set FPS to 60
    int FPS = 60;

    // create a new TileManager
    TileManager tileM = new TileManager(this);

    // create a new key handler to get in input
    public KeyHandler keyH = new KeyHandler(this);

    // SOUND
    Sound sound = new Sound();
    Sound se = new Sound();
    public Sound music = new Sound();

    // COLLISION CHECKER
    public CollisionChecker cChecker = new CollisionChecker(this);

    // ASSET SETTER
    public AssetSetter aSetter = new AssetSetter(this);

    // UI
    public UI ui = new UI(this);

    //EVENT HANDELER
    public EventHandler eHandler = new EventHandler(this);
    // GAME THREAD (RUNNER)
    Thread gameThread;

    // CREATE NEW PLAYER
    public Player player = new Player(this, keyH);


    //ENTITY AND OBJECT
    public Entity obj[] = new Entity[30];
    public Entity npc[] = new Entity[30];
    public Entity monster[] = new Entity[30];
    ArrayList<Entity> entityList = new ArrayList<>();
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public InteractiveTile iTile[] = new InteractiveTile[30];
    public ArrayList<Entity> particleList = new ArrayList<>();

    //GAME STATE
    public int  gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;


    // constructor for our game panel
    public GamePanel(){
        // set this game panel to the size we set previously
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        // set background to black
        this.setBackground(Color.black);
        //
        this.setDoubleBuffered(true);
        // set the keylistener to keyH
        this.addKeyListener(keyH);
        // focused to recieve key input
        this.setFocusable(true);
    }

    public void preGame(){
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setiTile();
       // playMusic(0);
       // stopMusic();
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth,screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();

        setFullScreen();
    }
    public void setFullScreen(){
        // GET LOCAL SCREEN DEVICE
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.screen);

        // GET FULL SCREEN WIDTH AND HEIGHT
        screenWidth2 = Main.screen.getWidth();
        screenHeight2 = Main.screen.getHeight();
    }

    // method that starts the game loop
    public void startGameThread(){
        gameThread = new Thread(this);             // automatically calls run()
        gameThread.start();
    }


    // the game loop
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;



        while(gameThread != null){
            currentTime = System.nanoTime();

            delta+= (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >=1){
                update();
                drawToTempScreen();
                drawToScreen();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000){
                drawCount = 0;
                timer = 0;
            }
        }

    }
    public void update(){
        if(gameState == playState){
            player.update();
            for(int i = 0; i < npc.length; i++){
                if(npc[i] != null){
                    npc[i].update();
                }
            }
            for(int i = 0; i < monster.length;i++){
                if(monster[i] != null){
                    if(monster[i].alive == true && monster[i].dying == false){
                        monster[i].update();
                    }
                    if (monster[i].alive == false) {
                        monster[i].checkDrop();
                        monster[i] = null;
                    }
                }
            }
            for(int i = 0; i < projectileList.size();i++){
                if(projectileList.get(i) != null){
                    if(projectileList.get(i).alive == true){
                        projectileList.get(i).update();
                    }
                    if (projectileList.get(i).alive == false) {
                        projectileList.remove(i);
                    }
                }
            }
            for(int i = 0; i < particleList.size();i++){
                if(particleList.get(i) != null){
                    if(particleList.get(i).alive == true){
                        particleList.get(i).update();
                    }
                    if (particleList.get(i).alive == false) {
                        particleList.remove(i);
                    }
                }
            }
            for(int i = 0; i < iTile.length; i++){
                if(iTile[i] != null){
                    iTile[i].update();
                }
            }
        }
        if(gameState == pauseState){
            //nothing
        }
    }
    public void drawToTempScreen(){
        // DEBUG
        long drawStart = 0;

        if(keyH.showDebugText == true){
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2);
        }
        else{
            // draw the tiles
            tileM.draw(g2);
            // INTERACTIVE TILES
            for(int i = 0; i < iTile.length; i++){
                if(iTile[i] != null){
                    iTile[i].draw(g2);
                }
            }
            // ADD ENTITIES: PLAYER
            entityList.add(player);

            // OBJ NPCs
            for(int i = 0; i < npc.length; i++){
                if(npc[i] != null){
                    entityList.add(npc[i]);
                }
            }
            // OBJ OBJECT
            for(int i = 0; i < obj.length; i++){
                if(obj[i] != null){
                    entityList.add(obj[i]);
                }
            }
            // OBJ MONSTER
            for(int i = 0; i < monster.length; i++){
                if(monster[i] != null){
                    entityList.add(monster[i]);
                }
            }
            // OBJ PROJECTILES
            for(int i = 0; i < projectileList.size(); i++){
                if(projectileList.get(i) != null){
                    entityList.add(projectileList.get(i));
                }
            }
            // OBJ PARTICLES
            for(int i = 0; i < particleList.size(); i++){
                if(particleList.get(i) != null){
                    entityList.add(particleList.get(i));
                }
            }

            // sort
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return 0;
                }

                @Override
                public boolean equals(Object obj) {
                    return false;
                }
            });

            // DRAW ENTITIES
            for(int i = 0; i< entityList.size(); i++){
                entityList.get(i).draw(g2);
            }
            //EMPTY ENTITY LIST
            entityList.clear();

            // UI
            ui.draw(g2);

        }

        // DEBUG
        if(keyH.showDebugText == true){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.white);
            int x = 10;
            int y = 400;
            int lineHeight = 20;
            g2.setColor(Color.white);

            g2.drawString("WorldX" + player.worldX, x, y); y += lineHeight;
            g2.drawString("WorldY" + player.worldY, x, y); y += lineHeight;
            g2.drawString("Col" + (player.worldX + player.solidArea.x)/tileSize, x, y); y += lineHeight;
            g2.drawString("Row" + (player.worldY + player.solidArea.y)/tileSize, x, y); y += lineHeight;
            g2.drawString("Draw Time: " + passed, x, y);
        }
    }
    public void drawToScreen(){
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    public void playMusic(int i){
        sound.setFile(i);
        sound.play();
        sound.loop();
    }
    public void stopMusic(){
        sound.stop();
    }
    public void playSoundEffect(int i){
        se.setFile(i);
        se.play();
    }
}
