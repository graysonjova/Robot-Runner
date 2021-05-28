package demoreee;

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
 * The OppPlayer class draws the in game character animations and implements some game mechanisms.
 */
public class OppPlayer extends Character {

    private String playerName;
    private int characterWidth;
    private int characterHeight;
    
    private int coinsCollected;
    
    private int levelWidth;
    private ArrayList<ImageView> players;
    private ArrayList<ImageView> platforms;
    private ArrayList<Node> pickups;
    private ArrayList<Node> hazards;
    
    //WalkState,walkTotal, repeatFrame, repeatFrameTotal Can be used for both directions.
    private int walkState = 0;
    private int walkStateTotal = 7;
    private int repeatFrame = 0;
    private int repeatFrameTotal = 30;
    Image charIdleImage = new Image(getClass().getResource("Robot/PNG/Poses HD/character_idle.png").toExternalForm());
    Image charFallImage = new Image(getClass().getResource("Robot/PNG/Poses HD/character_fall.png").toExternalForm());
    Image walkRightImage0 = new Image(getClass().getResource("Robot/PNG/Poses HD/character_walk_right0.png").toExternalForm());
    Image walkRightImage1 = new Image(getClass().getResource("Robot/PNG/Poses HD/character_walk_right1.png").toExternalForm());
    Image walkRightImage2 = new Image(getClass().getResource("Robot/PNG/Poses HD/character_walk_right2.png").toExternalForm());
    Image walkRightImage3 = new Image(getClass().getResource("Robot/PNG/Poses HD/character_walk_right3.png").toExternalForm());
    Image walkRightImage4 = new Image(getClass().getResource("Robot/PNG/Poses HD/character_walk_right4.png").toExternalForm());
    Image walkRightImage5 = new Image(getClass().getResource("Robot/PNG/Poses HD/character_walk_right5.png").toExternalForm());
    Image walkRightImage6 = new Image(getClass().getResource("Robot/PNG/Poses HD/character_walk_right6.png").toExternalForm());
    Image walkRightImage7 = new Image(getClass().getResource("Robot/PNG/Poses HD/character_walk_right7.png").toExternalForm());
    Image walkLeftImage0 = new Image(getClass().getResource("Robot/PNG/Poses HD/character_walk_left0.png").toExternalForm());
    Image walkLeftImage1 = new Image(getClass().getResource("Robot/PNG/Poses HD/character_walk_left1.png").toExternalForm());
    Image walkLeftImage2 = new Image(getClass().getResource("Robot/PNG/Poses HD/character_walk_left2.png").toExternalForm());
    Image walkLeftImage3 = new Image(getClass().getResource("Robot/PNG/Poses HD/character_walk_left3.png").toExternalForm());
    Image walkLeftImage4 = new Image(getClass().getResource("Robot/PNG/Poses HD/character_walk_left4.png").toExternalForm());
    Image walkLeftImage5 = new Image(getClass().getResource("Robot/PNG/Poses HD/character_walk_left5.png").toExternalForm());
    Image walkLeftImage6 = new Image(getClass().getResource("Robot/PNG/Poses HD/character_walk_left6.png").toExternalForm());
    Image walkLeftImage7 = new Image(getClass().getResource("Robot/PNG/Poses HD/character_walk_left7.png").toExternalForm());
    private ArrayList<Image> animRightList = new ArrayList<Image>();
    private ArrayList<Image> animLeftList = new ArrayList<Image>();

    
    /**
     * A function that constructs the human player which the user controls. It follows specific movement rules. It can only move left, right or jump. It is affected by gravity.
     * @param name - The name of the player.
     * @param gameRoot - The gameRoot which links the entity and its shape and coordinates to the map.
     * @param startX - The start position given by the X coordinate.
     * @param startY - The start position given by the Y coordinate.
     * @param width - the width of the robot entity.
     * @param height - the height of the robot entity.
     */

    //A HumanPlayer is a character controlled by a human player. It follows specific movement rules. It can only move left, right or jump. It is affected by gravity.
    public OppPlayer(String name, Pane gameRoot, ArrayList<ImageView> levelPlayers, ArrayList<Node> levelPickups, int startX, int startY, int width, int height) {
    	//HumanPlayer Constructor.
    	
    	System.out.println("OppPlayer Created!");
    	
    	//Turn the character into a member of the game.
        entity = new ImageView(charIdleImage);
        animRightList.add(walkRightImage0);
        animRightList.add(walkRightImage1);
        animRightList.add(walkRightImage2);
        animRightList.add(walkRightImage3);
        animRightList.add(walkRightImage4);
        animRightList.add(walkRightImage5);
        animRightList.add(walkRightImage6);
        animRightList.add(walkRightImage7);
        animLeftList.add(walkLeftImage0);
        animLeftList.add(walkLeftImage1);
        animLeftList.add(walkLeftImage2);
        animLeftList.add(walkLeftImage3);
        animLeftList.add(walkLeftImage4);
        animLeftList.add(walkLeftImage5);
        animLeftList.add(walkLeftImage6);
        animLeftList.add(walkLeftImage7);

        levelPlayers.add(entity);
        pickups = levelPickups;

        entity.setFitHeight(60);
        entity.setFitWidth(60);
        entity.setX(-10);
        entity.setY(-20);
    	
        entity.setTranslateX(startX);
        entity.setTranslateY(startY);
        entity.getProperties().put("alive", true);

        gameRoot.getChildren().add(entity);
        
        
        playerName = name;
        
        playerVelocity = new Point2D(0, 0);

        characterWidth = width;
        characterHeight = height;
        coinsCollected = 0;

    	playerVelocity = new Point2D(0, 0);
    	
    }
    
    /**
     * A function that returns the player name.
     * @return - Returns the player name.
     */
    public String toString() {
    	return playerName;
    }

    public void update(){
        pickupCollision();
    }

    private void pickupCollision() {
        //Pickup collision detection
        //You cannot remove Nodes during the iteration. You must collect a list of the one's you want to remove and then remove them after.
        ArrayList<Node> toRemove = new ArrayList<Node>();
        for (Node pickup : pickups) {
            if (entity.getTranslateY() + characterHeight > pickup.getTranslateY() && entity.getTranslateY() < pickup.getTranslateY() + pickup.getLayoutBounds().getHeight()) {
                if (entity.getTranslateX() + characterWidth > pickup.getTranslateX() && entity.getTranslateX() < pickup.getTranslateX() + pickup.getLayoutBounds().getWidth()) {

                    //Collision detected!
                    pickup.setVisible(false);
                    toRemove.add(pickup);
                }
            }
        }
        for (Node remove : toRemove) {
            pickups.remove(remove);
        }
    }


    /**
     * Check if the finish line has been reached
     *
     * @param primaryStage the Stage for the game
     */
    private void finishDetection(Stage primaryStage) {
        ImageView finish = InitContent.levelFinish;
        if (entity.getTranslateX()<= (finish.getTranslateX()+finish.getFitWidth()) && entity.getTranslateX() >= (finish.getTranslateX() - 40)) {
            if (entity.getTranslateY()>= (finish.getTranslateY()-70) && entity.getTranslateY()<= (finish.getTranslateY() + finish.getFitHeight()+10)) {
                GameMenu.scoreText.setText("Coins: " + GameMenu.coins);
                Main.gameMenu.gameOver(primaryStage,Main.gameScene, Main.player1, "Online Opponent");
            }
        }
    }
    
    

}
