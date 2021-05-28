package demoreee;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameMenuTest {


    @Test
    void testSaveFile(){
        Assertions.assertNotNull(GameMenu.coins);
        Assertions.assertNotNull(GameMenu.bgMusic);
        //Assertions.assertNotNull(GameMenu.characterName);
        Assertions.assertNotNull(GameMenu.musicMute);
        Assertions.assertNotNull(GameMenu.soundEffectMute);
        Assertions.assertNotNull(GameMenu.boolMusicItem2);
        Assertions.assertNotNull(GameMenu.boolMusicItem3);
        Assertions.assertNotNull(GameMenu.characterOwnedFemaleAdventurer);
        Assertions.assertNotNull(GameMenu.characterOwnedMaleAdventurer);
    }

    @Test
    void startTest()  throws Exception {
        Runnable runningApp = () -> {

            //Stage primaryStage = new Stage();
            GameMenu gamemenu = new GameMenu();
            Stage primaryStage = GameMenu.getPrimaryStage();
            Assertions.assertNotNull(primaryStage);

            try {
                gamemenu.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Assertions.assertNotNull(GameMenu.coins);
            Assertions.assertNotNull(GameMenu.demo);
            Assertions.assertNotNull(GameMenu.scoreText);
            Assertions.assertNotNull(GameMenu.getPrimaryStage());


        };
    }

    @Test
    void closeGame() {
        Runnable runningApp = () -> {

            //Stage primaryStage = new Stage();
            GameMenu gamemenu = new GameMenu();
            Stage primaryStage = GameMenu.getPrimaryStage();
            Assertions.assertNotNull(primaryStage);

            try {
                gamemenu.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }

            gamemenu.closeGame();
            primaryStage = GameMenu.getPrimaryStage();
            Assertions.assertNotNull(primaryStage);


        };
    }

    @Test
    void pauseGame() {
        Runnable runningApp = () -> {

            //Stage primaryStage = new Stage();
            GameMenu gamemenu = new GameMenu();
            Stage primaryStage = GameMenu.getPrimaryStage();
            Assertions.assertNotNull(primaryStage);

            try {
                gamemenu.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }

            LevelData lvl1 = new LevelData();
            lvl1.genLevel();
            Main.setLvl(lvl1.getLevel());

            Pane appRoot = new Pane();
            Pane p_gameRoot = new Pane();
            Pane uiRoot = new Pane();

            String name = "Human Player1";
            ArrayList<ImageView> levelPlayers = new ArrayList< ImageView >();
            ArrayList<ImageView> levelPlatforms = new ArrayList< ImageView >();
            ArrayList<Node> levelPickups = new ArrayList< Node >();
            ArrayList<Node> levelHazards = new ArrayList<Node>();
            int startX = 0;
            int startY = 0;
            int width = 40;
            int height = 60;
            int roamRange = 20;

            HumanPlayer player = new HumanPlayer(name, p_gameRoot, levelPlayers, levelPlatforms, levelPickups, levelHazards, startX, startY, width, height, KeyCode.A, KeyCode.D, KeyCode.SPACE);

            Scene s_gameRoot = new Scene(p_gameRoot);

            Assertions.assertFalse(gamemenu.pause);
            gamemenu.PauseGame(primaryStage, s_gameRoot, player);
            Assertions.assertTrue(gamemenu.pause);



        };
    }
}