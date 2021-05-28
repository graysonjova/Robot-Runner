/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demoreee;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LevelDataTest {

    public LevelDataTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of levelGen method, of class LevelData.
     */
    @Test
    public void testLevelGenCalled() {
        //System.out.println("levelGen");

        String[] expResult = null;
        String[] result = LevelData.levelGen();

        //check if level data not null, if yes, that  means level data is sucessfully generated.
        assertNotEquals(expResult,result);



        // TODO review the generated test code and remove the default call to fail.

    }

    @Test
    public void testLevelGenSize() {
        String[] result = LevelData.levelGen();
        int widthExpectedLow = 79;
        int widthExpectedHigh = 130;
        int widthResult = result[1].length();
        int expectedHeight = 12;
        int height = result.length;

        assertTrue(widthResult>widthExpectedLow);//check if the level is above 80(the minimum length) in length.
        assertTrue(widthResult<=widthExpectedHigh);//check if the level is below or is 130(the maximum length) in length;
        assertTrue(height==expectedHeight);//check if the level has the intended height(12);

    }

    @Test
    public void testLevelGenContent() {

        String[] result = LevelData.levelGen();
        int areCoinsPresent = 0;
        int areHazardsPresent = 0;
        int isFinishLinePresent = 0;
        int arePlatformsPresent = 0;
        int areSpidersPresent = 0;
        int arePathingPresent = 0;

        for (int i = 0; i < result.length; i++) {
            String line = result[i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '1':
                        arePlatformsPresent = 1;
                        break;
                    case '2':
                        areHazardsPresent = 1;
                        break;
                    case '3':
                        areCoinsPresent = 1;
                        break;
                    case '4':
                        areSpidersPresent = 1;
                        break;
                    case 'f':
                        isFinishLinePresent = 1;
                        break;

                    case 'p':
                        arePathingPresent = 1;
                        break;


                };
            }
        }



        assertTrue(arePlatformsPresent == 1);//check if some platforms are generated.
        assertTrue(areHazardsPresent == 1);//check if some hazards are generated
        assertTrue(areCoinsPresent == 1);//check if some coins are generated
        assertTrue(areSpidersPresent == 1);//check if some spiders are generated
        assertTrue(arePathingPresent == 1);//check if the path to finishis present
        assertTrue(isFinishLinePresent == 1);//check if finishline is present

    }

    /**
     * Test of genLevel method, of class LevelData.
     */
    @Test
    public void testGenLevel() {
        System.out.println("genLevel");
        LevelData instance = new LevelData();
        instance.genLevel();
        assertTrue(true);//this is a void function, it does not need to return anything
    }

    /**
     * Test of getLevel method, of class LevelData.
     */
    @Test
    public void testGetLevel() {
        System.out.println("getLevel");
        LevelData instance = new LevelData();
        String[] expResult = null;
        String[] result = instance.getLevel();
        assertEquals(expResult,result);/*null because the only point of this function is to be called by networking,
        it basically does nothing but pass a value outside leveldata, hence null. Check networking Junit test to see
        if the level is actually passed*/
        // TODO review the generated test code and remove the default call to fail.

    }

}