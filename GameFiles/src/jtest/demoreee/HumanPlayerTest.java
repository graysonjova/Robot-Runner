package demoreee;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class HumanPlayerTest {

    @Test
    void testToString() throws Exception {

        Runnable runningApp  = () -> {

            LevelData lvl1 = new LevelData();
            lvl1.genLevel();
            Main.setLvl(lvl1.getLevel());

            Stage primaryStage = new Stage();
            Pane appRoot = new Pane();
            Pane gameRoot = new Pane();
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

            HumanPlayer player = new HumanPlayer(name, gameRoot, levelPlayers, levelPlatforms, levelPickups, levelHazards, startX, startY, width, height, KeyCode.A, KeyCode.D, KeyCode.SPACE);
            Assertions.assertNotNull(player.toString());

        };
    }

    @Test
    void update() {
        Runnable runningApp  = () -> {

            LevelData lvl1 = new LevelData();
            lvl1.genLevel();
            Main.setLvl(lvl1.getLevel());

            Stage primaryStage = new Stage();
            Pane appRoot = new Pane();
            Pane gameRoot = new Pane();
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

            HumanPlayer player = new HumanPlayer(name, gameRoot, levelPlayers, levelPlatforms, levelPickups, levelHazards, startX, startY, width, height, KeyCode.A, KeyCode.D, KeyCode.SPACE);
            double firstPositionX = player.getEntity().getTranslateX();
            double firstPositionY = player.getEntity().getTranslateY();
            player.update(true, true, true, false, primaryStage);
            double secondPositionX = player.getEntity().getTranslateX();
            double secondPositionY = player.getEntity().getTranslateY();

            Assertions.assertNotEquals(firstPositionX, secondPositionX);
            Assertions.assertNotEquals(firstPositionY, secondPositionY);

            player.update(false, false, false, true, primaryStage);

            double thirdPositionX = player.getEntity().getTranslateX();
            double thirdPositionY = player.getEntity().getTranslateY();

            Assertions.assertEquals(firstPositionX, thirdPositionX);
            Assertions.assertEquals(firstPositionY, thirdPositionY);

        };
    }

    @Test
    void resetLevel() {
        Runnable runningApp  = () -> {

            LevelData lvl1 = new LevelData();
            lvl1.genLevel();
            Main.setLvl(lvl1.getLevel());

            Stage primaryStage = new Stage();
            Pane appRoot = new Pane();
            Pane gameRoot = new Pane();
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

            HumanPlayer player = new HumanPlayer(name, gameRoot, levelPlayers, levelPlatforms, levelPickups, levelHazards, startX, startY, width, height, KeyCode.A, KeyCode.D, KeyCode.SPACE);
            double firstPositionX = player.getEntity().getTranslateX();
            double firstPositionY = player.getEntity().getTranslateY();
            player.update(true, true, true, false, primaryStage);
            double secondPositionX = player.getEntity().getTranslateX();
            double secondPositionY = player.getEntity().getTranslateY();

            Assertions.assertNotEquals(firstPositionX, secondPositionX);
            Assertions.assertNotEquals(firstPositionY, secondPositionY);

            player.resetLevel(primaryStage);

            double thirdPositionX = player.getEntity().getTranslateX();
            double thirdPositionY = player.getEntity().getTranslateY();

            Assertions.assertEquals(firstPositionX, thirdPositionX);
            Assertions.assertEquals(firstPositionY, thirdPositionY);

        };
    }
}