package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound(){
        soundURL[0] = getClass().getClassLoader().getResource("sound/shimmy.wav");
        soundURL[1] = getClass().getClassLoader().getResource("sound/collecting_power_item.wav");
        soundURL[2] = getClass().getClassLoader().getResource("sound/open_sound.wav");
        soundURL[3] = getClass().getClassLoader().getResource("sound/treasure_collect.wav");
        soundURL[4] = getClass().getClassLoader().getResource("sound/bodyHitLight1.wav");
        soundURL[5] = getClass().getClassLoader().getResource("sound/bodyHitHard1.wav");
        soundURL[6] = getClass().getClassLoader().getResource("sound/dullSwoosh1.wav");
        soundURL[7] = getClass().getClassLoader().getResource("sound/levelUp.wav");
        soundURL[8] = getClass().getClassLoader().getResource("sound/cursor.wav");

    }

    public void setFile(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            System.out.println("Clip Loaded alright");

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}
