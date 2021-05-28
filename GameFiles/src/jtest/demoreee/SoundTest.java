package demoreee;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class SoundTest {

    Sound sound = new Sound();

    @Test
    void testSetFile(){
        sound.setFile("GameFiles/src/res/sound/Onward!.wav");
        Assertions.assertNotNull(sound);
    }
}