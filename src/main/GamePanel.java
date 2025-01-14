package main;

// import our .png files
import entity.Entity;
import entity.Player;
import tile.TileManager;

// import needed libraries
import javax.swing.JPanel;
import java.awt.*;
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
    public final int maxScreenCol = 16;                                   // max 16 col tiles
    public final int maxScreenRow = 12;                                   // max 12 row tiles
    public final int screenWidth = tileSize * maxScreenCol;               // screen width = 48 x 16 = 768 pixels
    public final int screenHeight = tileSize * maxScreenRow;              // screen height = 48 * 12 = 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;


    // set FPS to 60
    int FPS = 60;

    // create a new TileManager
    TileManager tileM = new TileManager(this);

    // create a new key handler to get in input
    public KeyHandler keyH = new KeyHandler(this);

    // SOUND
    Sound sound = new Sound();
    Sound se = new Sound();

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
    public Entity obj[] = new Entity[10];
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();

    //GAME STATE
    public int  gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;


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
       // playMusic(0);
       // stopMusic();
        gameState = titleState;

    }

    // method that starts the game loop
    public void startGameThread(){
        gameThread = new Thread(this);             // automatically calls run()
        gameThread.start();
    }


    // the game loop
    @Override
    public void run() {

        // get FPS
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null){

            // 1,000,000 = 1 second (more precise than currentTimeMillis()
            long currentTime = System.nanoTime();
            // long currentTime2 = System.currentTimeMillis();

            // call the update method
            update();

            // call the paintComponent method
            repaint();


            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0 ){
                    remainingTime = 0;
                }

                // sometimes the .sleep method is off by a few milli seconds so you'll need to create a new run time/gameloop to combat that if you so choose
                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
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
                        monster[i] = null;
                    }
                }
            }
        }
        if(gameState == pauseState){
            //nothing
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

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

            // ADD ENTITIES: PLAYER
            entityList.add(player);

            // NPCs
            for(int i = 0; i < npc.length; i++){
                if(npc[i] != null){
                    entityList.add(npc[i]);
                }
            }

            // OBJ
            for(int i = 0; i < obj.length; i++){
                if(obj[i] != null){
                    entityList.add(obj[i]);
                }
            }
            // OBJ
            for(int i = 0; i < monster.length; i++){
                if(monster[i] != null){
                    entityList.add(monster[i]);
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

        g2.dispose();


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
