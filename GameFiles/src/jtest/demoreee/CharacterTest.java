package demoreee;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

    @Test
    void getEntity() {
        Runnable runningApp  = () -> {
            Character character = new Character();
            Assertions.assertNotNull(character.getEntity());

        };
    }
}