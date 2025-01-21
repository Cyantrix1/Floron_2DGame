package main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
// implement key listener (which requires different methods)
public class KeyHandler implements KeyListener {
    GamePanel gp;
    // up/down/left/right
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shootKeyPressed;
    //DEBUG
    boolean showDebugText = false;
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
            titleState(code);
        }

        //PLAY STATE
        else if(gp.gameState == gp.playState) {
            playState(code);

        }
        // PAUSE STATE
        else if(gp.gameState == gp.pauseState){
            pauseState(code);
        }

        // DIALOGUE STATE
        else if(gp.gameState ==  gp.dialogueState){
            dialogueState(code);

        }
        // CHARACTER STATS STATE
        else if(gp.gameState == gp.characterState){
            characterState(code);
        }
        // OPTIONS STATE
        else if(gp.gameState == gp.optionsState){
            optionState(code);
        }

        //DEBUG STATE
        if(code == KeyEvent.VK_T){
            if(showDebugText == false){
                showDebugText = true;
            }
            else if(showDebugText == true){
                showDebugText = false;
            }
        }
        if(code == KeyEvent.VK_R){
            gp.tileM.loadMap("maps/New_Map.txt");
        }
    }

    public void titleState(int code){

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
    public void playState(int code){
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
        if(code == KeyEvent.VK_G){
            gp.gameState = gp.characterState;
        }
        if(code == KeyEvent.VK_F){
            shootKeyPressed = true;
        }
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.optionsState;
        }
    }
    public void pauseState(int code){
        if(code == KeyEvent.VK_P){
            gp.gameState = gp.playState;
        }
    }
    public void dialogueState(int code){
        if(code == KeyEvent.VK_ENTER){
            gp.gameState  = gp.playState;
        }
    }
    public void characterState(int code){
        if(code == KeyEvent.VK_G){
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            if(gp.ui.slotRow !=0){
                gp.ui.slotRow--;
                gp.playSoundEffect(8);
            }
        }
        if(code == KeyEvent.VK_A|| code == KeyEvent.VK_LEFT){
            if(gp.ui.slotCol != 0){
                gp.ui.slotCol--;
                gp.playSoundEffect(8);
            }
        }
        if(code == KeyEvent.VK_S|| code == KeyEvent.VK_DOWN){
            if(gp.ui.slotRow != 3){
                gp.ui.slotRow++;
                gp.playSoundEffect(8);
            }
        }
        if(code == KeyEvent.VK_D|| code == KeyEvent.VK_RIGHT){
            if(gp.ui.slotCol != 4){
                gp.ui.slotCol++;
                gp.playSoundEffect(8);
            }
        }
        if(code == KeyEvent.VK_ENTER){
            gp.player.selectItem();
        }
    }
    public void optionState(int code){
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }

        int maxCommandNum = 0;
        switch(gp.ui.subState) {
            case 0: maxCommandNum = 5; break;
            case 3: maxCommandNum = 1; break;
        }
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            gp.ui.commandNum--;
            gp.playSoundEffect(8);
            if(gp.ui.commandNum < 0){
                gp.ui.commandNum = maxCommandNum;
            }
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            gp.playSoundEffect(8);
            if (gp.ui.commandNum > maxCommandNum) {
                gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            if(gp.ui.subState == 0){
                if(gp.ui.commandNum == 1 && gp.music.volumeScale >0){
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSoundEffect(8);
                }
                if(gp.ui.commandNum == 2 && gp.se.volumeScale >0){
                    gp.se.volumeScale--;
                    gp.playSoundEffect(8);
                }
            }
        }
        if(code == KeyEvent.VK_D|| code == KeyEvent.VK_RIGHT) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSoundEffect(8);
                }
                if (gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
                    gp.se.volumeScale++;
                    gp.playSoundEffect(8);
                }
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
        if(code == KeyEvent.VK_F){
            shootKeyPressed = false;
        }
    }
}
