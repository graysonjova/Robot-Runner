package demoreee;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ClientHandlerTest {

    @Test
    void intToByteArray() throws IOException {
        byte[] byteArray = new byte[] { (byte) 0x00, (byte)0x00, (byte)0x00, (byte)0x10};
        byte[] number2 = ClientHandler.intToByteArray(16);
    	
    	assertTrue(Arrays.toString(number2).equals(Arrays.toString(byteArray)));
    }

    @Test
    void changeLvl() {
    }

    @Test
    void run() {
    }
}
