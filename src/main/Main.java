package main;

// import that allows us to create a game frame
import javax.swing.JFrame;

public class Main {

   public static  JFrame screen;
    public static void main(String[] args) {

       // create new game frame
       screen = new JFrame();
       // make sure that when we hit the X to close the game the program stops running
       screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // don't allow the program to resize
       screen.setResizable(false);
       // set title of the program
       screen.setTitle("2D Project: Floron");

       // get a new game panel
       GamePanel gamePanel = new GamePanel();
       // add to the JFrame
       screen.add(gamePanel);

       gamePanel.config.loadConfig();
       if(gamePanel.fullScreenOn == true){
          screen.setUndecorated(true);
       }

       // causes the Window to be sized to fit the preferred size and layouts of its subcomponents
       screen.pack();

        // we need to set this to null so that it will be at the center of the screen on launch
       screen.setLocationRelativeTo(null);
       // make sure we can see the screen
       screen.setVisible(true);

       gamePanel.preGame();
       // the game loop
       gamePanel.startGameThread();

    }
}