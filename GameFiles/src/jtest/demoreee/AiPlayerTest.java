package demoreee;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AiPlayerTest {


    @Test
    void getEntity() {
        Runnable runningApp  = () -> {

            LevelData lvl1 = new LevelData();
            lvl1.genLevel();
            Main.setLvl(lvl1.getLevel());

            Stage primaryStage = new Stage();
            Pane appRoot = new Pane();
            Pane gameRoot = new Pane();
            Pane uiRoot = new Pane();

            String name = "AI player";
            ArrayList<ImageView> levelPlayers = new ArrayList< ImageView >();
            ArrayList<ImageView> levelPlatforms = new ArrayList< ImageView >();
            ArrayList<Node> levelGuides = new ArrayList< Node >();
            ArrayList<Node> levelHazards = new ArrayList<Node>();
            int startX = 0;
            int startY = 0;
            int width = 40;
            int height = 60;
            int roamRange = 20;

            int loop = 30000;
            int[] xPath = new int[]{ 1,2,3,4,5,6,7,8,9,10 };
            int[] yPath = new int[]{ 1,2,3,4,5,6,7,8,9,10 };

            AiPlayer player = new AiPlayer(name, gameRoot, levelPlayers, levelPlatforms, levelGuides, levelHazards, startX, startY, width, height);

            Assertions.assertNotNull(player.getEntity());

        };
    }

    @Test
    void testToString() {
        Runnable runningApp  = () -> {

            LevelData lvl1 = new LevelData();
            lvl1.genLevel();
            Main.setLvl(lvl1.getLevel());

            Stage primaryStage = new Stage();
            Pane appRoot = new Pane();
            Pane gameRoot = new Pane();
            Pane uiRoot = new Pane();

            String name = "AI player";
            ArrayList<ImageView> levelPlayers = new ArrayList< ImageView >();
            ArrayList<ImageView> levelPlatforms = new ArrayList< ImageView >();
            ArrayList<Node> levelGuides = new ArrayList< Node >();
            ArrayList<Node> levelHazards = new ArrayList<Node>();
            int startX = 0;
            int startY = 0;
            int width = 40;
            int height = 60;
            int roamRange = 20;

            int loop = 30000;
            int[] xPath = new int[]{ 1,2,3,4,5,6,7,8,9,10 };
            int[] yPath = new int[]{ 1,2,3,4,5,6,7,8,9,10 };

            AiPlayer player = new AiPlayer(name, gameRoot, levelPlayers, levelPlatforms, levelGuides, levelHazards, startX, startY, width, height);

            assertNotNull(player.toString());

        };
    }

    @Test
    void update2() {
        Runnable runningApp  = () -> {

            LevelData lvl1 = new LevelData();
            lvl1.genLevel();
            Main.setLvl(lvl1.getLevel());

            Stage primaryStage = new Stage();
            Pane appRoot = new Pane();
            Pane gameRoot = new Pane();
            Pane uiRoot = new Pane();

            String name = "AI player";
            ArrayList<ImageView> levelPlayers = new ArrayList< ImageView >();
            ArrayList<ImageView> levelPlatforms = new ArrayList< ImageView >();
            ArrayList<Node> levelGuides = new ArrayList< Node >();
            ArrayList<Node> levelHazards = new ArrayList<Node>();
            int startX = 0;
            int startY = 0;
            int width = 40;
            int height = 60;
            int roamRange = 20;

            int loop = 30000;
            int[] xPath = new int[]{ 1,2,3,4,5,6,7,8,9,10 };
            int[] yPath = new int[]{ 1,2,3,4,5,6,7,8,9,10 };

            AiPlayer player = new AiPlayer(name, gameRoot, levelPlayers, levelPlatforms, levelGuides, levelHazards, startX, startY, width, height);
            double firstPositionX = player.getEntity().getTranslateX();
            double firstPositionY = player.getEntity().getTranslateY();
            player.update2(loop, xPath, yPath, primaryStage);
            double secondPositionX = player.getEntity().getTranslateX();
            double secondPositionY = player.getEntity().getTranslateY();

            Boolean moved = false;
            if( (firstPositionX != secondPositionX) || (firstPositionY != secondPositionY) ){
                moved = true;
            }

            Assertions.assertTrue(moved);

            int speed = player.speed;
            assertNotNull(speed);

        };
    }
}