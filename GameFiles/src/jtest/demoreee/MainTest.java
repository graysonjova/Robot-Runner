package demoreee;

import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;

class MainTest {

    @Test
    void getStage() {
    }

    @Test
    void start() {
    	Runnable runningApp  = () -> {

            LevelData lvl1 = new LevelData();
            lvl1.genLevel();
            Main.setLvl(lvl1.getLevel());

            Pane appRoot = new Pane();
            Pane gameRoot = new Pane();
            Pane uiRoot = new Pane();

            ArrayList< ImageView > levelPlayers = new ArrayList< ImageView >();
            ArrayList<ImageView> levelPlatforms = new ArrayList< ImageView >();
            ArrayList<Node> levelHazards = new ArrayList<Node>();
            int startX = 0;
            int startY = 0;
            int width = 40;
            int height = 60;
            int roamRange = 20;
            Assertions.assertNotNull(Main.getStage());
        };
    }

    @Test
    void setLvl() {
    }

    @Test
    void getLvl() {
    }

    @Test
    void main() {
    }
}
