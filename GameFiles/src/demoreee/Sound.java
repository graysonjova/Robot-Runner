package demoreee;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;


/**
 * Class that handles everything that is related to sound and contains all functions that are sound-related.
 */
public class Sound {
    private Clip clip;

    /**
     * Sets the file path of the sound file to the variable in the parameter
     * @param soundFileName String variable with the address of the sound file
     */
    public void setFile(String soundFileName){

        try{
            File soundFile = new File(soundFileName);
            AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(sound);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Starts the sound file
     */
    public void play(){
        clip.setFramePosition(0);
        clip.start();
    }

    /**
     * Loops the sound file continuously
     */
    public void loop(){
       clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Stops the sound file
     */
    public void stop(){
       clip.stop();
    }

}