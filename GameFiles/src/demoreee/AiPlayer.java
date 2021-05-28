package demoreee;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * The AIPlayer class implements AI mechanisms into the game.
 */
public class AiPlayer extends Character {

    String[] curLvl = Main.getLvl();

    private HashMap<KeyCode, Boolean> playerKeys = new HashMap<KeyCode, Boolean>(); //basically to check if a key is pressed
    private static double accelerationSpeed = 0.5; //The amount that the player can increase their velocity by in a single tick.
    private static double decelerationMultiplier = 2.0; //decelerationMultiplier is how many times faster the player will accelerate when they are still moving in a direction that they don't want to be mobing in. Helps the movement feel less like being on ice.
    private static double maxSpeed = 4 * accelerationSpeed; //Max speed should be a multiple of accelerationSpeed of there might be some overshooting errors (not guaranteed).
    private boolean canJump = true;
    private boolean ontopOfBlock = true;
    private int spawnX;
    private int spawnY;
    //Lowering the value of speed will increase the actual speed of the AI
    public int speed = 15000;

    boolean pause = false;


    private KeyCode left;
    private KeyCode right;
    private KeyCode jump;
    private KeyCode Esc;

    private String playerName;
    private int characterWidth;
    private int characterHeight;
    private int platformBlockSize = 60;

    public int coinsCollected;


    private Pane gameRoot;
    private int levelWidth;
    private ArrayList<ImageView> players;
    private ArrayList<ImageView> platforms;
    private ArrayList<Node> guides;
    private ArrayList<Node> hazards;

    Image starImage = new Image(getClass().getResource("Icons/laserYellowBurst.png").toExternalForm());


    private Sound soundEffect = new Sound();

    /**
     * A function that constructs the human player which the user controls. It follows specific movement rules. It can only move left, right or jump. It is affected by gravity.
     *
     * @param name         - The name of the player.
     * @param p_gameRoot   - The gameRoot which links the entity and its shape and coordinates to the map.
     * @param levelPlayers - The level players.
     * @param startX       - The start position given by the X coordinate.
     * @param startY       - The start position given by the Y coordinate.
     * @param width        - the width of the robot entity.
     * @param height       - the height of the robot entity.
     * @param levelHazards
     * @param levelPlatforms
     * @param levelGuides
     */
    //A HumanPlayer is a character controlled by a human player. It follows specific movement rules. It can only move left, right or jump. It is affected by gravity.
    public AiPlayer(String name, Pane p_gameRoot, ArrayList<ImageView> levelPlayers, ArrayList<ImageView> levelPlatforms, ArrayList<Node> levelGuides, ArrayList<Node> levelHazards, int startX, int startY, int width, int height) {
        //HumanPlayer Constructor.

        System.out.println("aiPlayer Created!");

        gameRoot = p_gameRoot;

        //Turn the character into a member of the game.
        entity = new ImageView(starImage);

        entity.setFitHeight(60);
        entity.setFitWidth(60);
        entity.setX(-10);
        entity.setY(-20);

        entity.setTranslateX(startX);
        entity.setTranslateY(startY);
        entity.getProperties().put("alive", true);

        gameRoot.getChildren().add(entity);


        //Instantiate properties.
        playerName = name;



        playerVelocity = new Point2D(0, 0);
        levelWidth = curLvl[0].length() * 60; //I don't like hard-coding this. This should be taken from somewhere more central.

        spawnX = startX;
        spawnY = startY;
        characterWidth = width;
        characterHeight = height;


        accelerationSpeed = 2;
        decelerationMultiplier = 4;
        maxSpeed = 3 * accelerationSpeed;
        canJump = true;
        playerVelocity = new Point2D(0, 0);

    }

    public ImageView getEntity() {
        return entity;
    }

    public String toString() {
        return playerName;
    }


    /**
     * A function that updates the location of the player.
     * For an easier explanation regarding what the loop numbers mean, we provide the following example. In xpath[112], ypath[112], the numbers would be 0112, 1112, 2112, until 30112 etc. The last three digits (112) are where it is located in the array. The first two digits (00,01,02,until 30) determine how far along is the animation. By 30 it has reached where xPath[112] and yPath[112] is supposed to be 30 because we assume we are working with 60 fps and we decided it will move every 2 block every second. The reason i made it into one function with one value outputed for effeciency reason since this is located in update, which has beneficial effects to our game performance
     *
     * @param loop  - The loop which is the core of this method.
     * @param xPath - The X coordinate of the path.
     * @param yPath - The Y coordinate of the path.
     * @param primaryStage
     * @return - The robot entity which the user controls.
     */
    public int update2(int loop, int[] xPath, int[] yPath, Stage primaryStage) {//the way this function work is by using loop

        if (GameMenu.pause == false) {
            finishDetection(primaryStage);


        /*loop itself is divided to 2 numbers, the last three digit
            represent the index for the x and Y coordinates, and the first three represent its location relative to said coordinate
            for smoother animation.*/
            if (xPath[loop % 1000] < (xPath[(loop % 1000) + 1])) {
                if (yPath[loop % 1000] == (yPath[(loop % 1000) + 1])) {//check if next is straight path, if yes go right
                    //do the animation here.
                    entity.setTranslateX((xPath[loop % 1000] * 60) + loop / (speed / 60));
                    entity.setTranslateY((yPath[loop % 1000] * 60) + 20);
                }
            } else if (xPath[loop % 1000] == (xPath[(loop % 1000) + 1])) {
                //check if next is going up or down, then animate it
                if (yPath[loop % 1000] < (yPath[(loop % 1000) + 1])) {
                    //animation for going up
                    entity.setTranslateX((xPath[loop % 1000] * 60));
                    entity.setTranslateY((yPath[loop % 1000] * 60) + 20 + loop / (speed / 60));
                } else if (yPath[loop % 1000] > (yPath[(loop % 1000) + 1])) {
                    //animation for going down
                    entity.setTranslateX((xPath[loop % 1000] * 60));
                    entity.setTranslateY((yPath[loop % 1000] * 60) + 20 - loop / (speed / 60));
                }
            }


            //once loop have reached speed, it means it has finished its movement in the current block, moving to next block
            if (loop >= speed) {
                //loop is added one so it moves the array index
                loop = loop + 1;
                //decrease the loop by speed so the animation resets
                loop = loop - speed;
            }
            //everytime the update is ran, increase 1000 to loop so the next animation picks up where the last animation starts
            loop = loop + 1000;

        }

        return loop;
    }

    /**
     * Check if the finish line has been reached
     *
     * @param primaryStage the Stage for the game
     */
    private void finishDetection(Stage primaryStage) {
        ImageView finish = InitContent.levelFinish;
        if (entity.getTranslateX() <= (finish.getTranslateX() + finish.getFitWidth() + 100) && entity.getTranslateX() >= (finish.getTranslateX() - 100)) {
            if (entity.getTranslateY() >= (finish.getTranslateY() - 100) && entity.getTranslateY() <= (finish.getTranslateY() + finish.getFitHeight() + 100)) {
                GameMenu.scoreText.setText("Coins: " + GameMenu.coins);
                Main.gameMenu.gameOver(primaryStage, Main.gameScene, Main.player1, "AI Opponent");
            }
        }
    }
    
    /**
     * Set the AI speed
     * @param inputSpeed
     */
    public void setSpeed(int inputSpeed) {
    	speed = inputSpeed;
    	
    }

}