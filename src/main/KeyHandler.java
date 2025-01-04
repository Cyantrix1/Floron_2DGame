package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// implement key listener (which requires different methods)
public class KeyHandler implements KeyListener {

    GamePanel gp;
    // up/down/left/right
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;

    //DEBUG
    boolean checkDrawTime = false;


    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //TITLE STATE
        if(gp.gameState == gp.titleState){

            if(gp.ui.titleScreenState == 0){
                // MOVE UP AND DOWN
                if ((code == KeyEvent.VK_W || code == KeyEvent.VK_UP)) {
                    if(gp.ui.commandNum > 0){
                        gp.ui.commandNum--;
                    }else{
                        gp.ui.commandNum = 2;
                    }
                }
                if ((code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN)) {
                    if(gp.ui.commandNum < 2){
                        gp.ui.commandNum++;

                    }else{
                        gp.ui.commandNum = 0;
                    }

                }
                if((code == KeyEvent.VK_ENTER )){
                    // NEW GAME
                    if(gp.ui.commandNum == 0){
                        gp.ui.titleScreenState = 1;
                    }
                    // LOAD GAME
                    if(gp.ui.commandNum == 1){

                    }
                    // EXIT GAME
                    if(gp.ui.commandNum == 2){
                        System.exit(0);
                    }
                }
            }
            // CHARACTER CLASS SELECTION
            else if(gp.ui.titleScreenState == 1){
                // MOVE UP AND DOWN
                if ((code == KeyEvent.VK_W || code == KeyEvent.VK_UP)) {
                    if(gp.ui.commandNum > 0){
                        gp.ui.commandNum--;
                    }else{
                        gp.ui.commandNum = 2;
                    }
                }
                if ((code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN)) {
                    if(gp.ui.commandNum < 3){
                        gp.ui.commandNum++;

                    }else{
                        gp.ui.commandNum = 0;
                    }

                }
                if((code == KeyEvent.VK_ENTER )){
                    // MAGE
                    if(gp.ui.commandNum == 0){
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    }
                    //KNIGHT
                    if(gp.ui.commandNum == 1){
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    }
                    // ARCHER
                    if(gp.ui.commandNum == 2){
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    }
                    //BACK
                    if(gp.ui.commandNum == 3){
                        gp.ui.titleScreenState = 0;
                        gp.stopMusic();
                    }
                }
            }

        }

        if(gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                leftPressed = true;

            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                downPressed = true;

            }
            if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                rightPressed = true;

            }
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.pauseState;
            }
            if(code == KeyEvent.VK_ENTER){
                enterPressed = true;
            }
        }
        else if(gp.gameState == gp.pauseState){
            if(code == KeyEvent.VK_P){
                gp.gameState = gp.playState;
            }
        }
        //DEBUG STATE
        if(code == KeyEvent.VK_T){
            if(checkDrawTime == false){
                checkDrawTime = true;
            }
            else if(checkDrawTime == true){
                checkDrawTime = false;
            }
        }
        // DIALOGUE STATE
        else if(gp.gameState ==  gp.dialogueState){
            if(code == KeyEvent.VK_ENTER){
                gp.gameState  = gp.playState;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            upPressed = false;
        }
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            downPressed = false;
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            rightPressed = false;
        }
    }
}
