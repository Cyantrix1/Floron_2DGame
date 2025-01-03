package main;

// import our .png files
import entity.Entity;
import entity.Player;
import objects.SuperObject;
import tile.TileManager;

// import needed libraries
import javax.swing.JPanel;
import java.awt.*;

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
    public SuperObject obj[] = new SuperObject[10];
    public Entity npc[] = new Entity[10];

    //GAME STATE
    public int  gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;


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
        playMusic(0);
        stopMusic();
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

        if(keyH.checkDrawTime == true){
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2);
        }
        else{
            // draw the tiles
            tileM.draw(g2);

            // OBJECT
            for(int i = 0; i < obj.length; i++){
                if(obj[i] != null){
                    obj[i].draw(g2, this);
                }
            }
            // NPC
            for(int i = 0; i < npc.length; i++){
                if(npc[i] != null){
                    npc[i].draw(g2);
                }
            }

            // draw the player
            player.draw(g2);
            // UI
            ui.draw(g2);

        }

        // DEBUG
        if(keyH.checkDrawTime == true){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw Time: " + passed);
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
