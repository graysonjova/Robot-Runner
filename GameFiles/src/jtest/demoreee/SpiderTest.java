package demoreee;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SpiderTest {

    @Test
    void testToString() {

        Runnable runningApp  = () -> {

            LevelData lvl1 = new LevelData();
            lvl1.genLevel();
            Main.setLvl(lvl1.getLevel());

            Pane appRoot = new Pane();
            Pane gameRoot = new Pane();
            Pane uiRoot = new Pane();

            String name = "Spider1";
            ArrayList< ImageView > levelPlayers = new ArrayList< ImageView >();
            ArrayList<ImageView> levelPlatforms = new ArrayList< ImageView >();
            ArrayList<Node> levelHazards = new ArrayList<Node>();
            int startX = 0;
            int startY = 0;
            int width = 40;
            int height = 60;
            int roamRange = 20;

            Spider spider = new Spider(name, gameRoot, levelPlayers, levelPlatforms, levelHazards, startX, startY, width, height, roamRange);
            Assertions.assertNotNull(spider.toString());
        };
    }

    @Test
    void testUpdate() {
        Runnable runningApp  = () -> {

            LevelData lvl1 = new LevelData();
            lvl1.genLevel();
            Main.setLvl(lvl1.getLevel());

            Pane appRoot = new Pane();
            Pane gameRoot = new Pane();
            Pane uiRoot = new Pane();

            String name = "Spider1";
            ArrayList< ImageView > levelPlayers = new ArrayList< ImageView >();
            ArrayList<ImageView> levelPlatforms = new ArrayList< ImageView >();
            ArrayList<Node> levelHazards = new ArrayList<Node>();
            int startX = 0;
            int startY = 0;
            int width = 40;
            int height = 60;
            int roamRange = 20;

            Spider spider = new Spider(name, gameRoot, levelPlayers, levelPlatforms, levelHazards, startX, startY, width, height, roamRange);
            double firstPosition = spider.getEntity().getTranslateX();
            spider.update();
            double secondPosition = spider.getEntity().getTranslateX();

            Assertions.assertNotEquals(firstPosition,secondPosition);

        };
    }

}