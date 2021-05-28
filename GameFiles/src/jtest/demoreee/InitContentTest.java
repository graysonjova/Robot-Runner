/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demoreee;

import static demoreee.InitContent.curLvl;
import javafx.scene.layout.Pane;
import org.junit.After;

import org.junit.Before;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 *
 * @author Imperator
 */
public class InitContentTest {
    public  InitContentTest() {
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
     * Test of levelBuilder method, of class InitContent.
     */
    @Test
    public void testLevelBuilder() {
        Runnable runningApp = ()->{
        Pane gameRoot = null;
        Pane expResult = null;
        Pane result = InitContent.levelBuilder(gameRoot);
        assertTrue(result == expResult);
        //test if init content not null, most of the testing for this is ran in leveldata.
        //as long as the game root is  not null, the level is built
        
        // TODO review the generated test code and remove the default call to fail.
    };}

    /**
     * Test of aiYPath method, of class InitContent.
     */
    @Test
    public void testAiYPath() {
        Runnable runningApp = ()->{
        int[] expResult = null;
        int[] result = InitContent.aiYPath();
        int levelTop = (LevelData.levelGen()).length;
        int levelBottom =0;
        int inFrame =1;
        int isNearFinish;
        
        
        assertFalse(expResult == result);
        //make sure the path is not null, means its present
        assertTrue(result[0] == 10);//check if the path starts in 10(1 above floor)
        
        for(int i =0; i<result.length;i++){
            if(result[i]<0||result[i]>levelTop){
                inFrame =0;
            }
            
        }
        assertTrue(inFrame == 1);
        //check if all path are in frame, if clear then its probably right
        
        
        // TODO review the generated test code and remove the default call to fail.
    };}

    /**
     * Test of aiXPath method, of class InitContent.
     */
    @Test
    public void testAiXPath() {
        Runnable runningApp = ()->{
        int expResult = 80;
        int[] result = InitContent.aiXPath();
        assertTrue(result[0] == 0);//check if the path starts in the start
        
        assertTrue(result[-1] >expResult);
        //check if the patch is long enough to reach the finish.(min level generated is 80)
        
        // TODO review the generated test code and remove the default call to fail.
        }
    ;}
    @Test
    public void testIfPathFinish() {
        Runnable runningApp = ()->{
        int[] xPath = InitContent.aiXPath();
        int[] yPath = InitContent.aiYPath();
        int[] finishCoor = new int[2];
        curLvl = Main.getLvl();
        for (int i = 0; i < curLvl.length; i++) {
            String line = curLvl[i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case 'f':
                        finishCoor[0]=i;//this is Y
                        finishCoor[1]=j;//this is X
                        break;
                     
                
                };
                }
            }
        assertTrue(xPath[-1]>finishCoor[1]-3); /*the ai must finish atleast within 3 block from the finish(its a pathguide ai
        so it dont actually need to finish , just get close enough
        */
        assertTrue(yPath[-1]==finishCoor[0]||yPath[-1]==finishCoor[0]-1||yPath[-1]==finishCoor[0]+1);

        // TODO review the generated test code and remove the default call to fail.
        }
    ;}
    
}



