package demoreee;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * The HumanPlayer class implements mechanisms such as user inputted controls or the level reset.
 */
public class HumanPlayer extends Character {

	String[] curLvl = Main.getLvl();
	
    private HashMap<KeyCode, Boolean> playerKeys = new HashMap<KeyCode, Boolean>(); //basically to check if a key is pressed
	private static double accelerationSpeed = 0.2; //The amount that the player can increase their velocity by in a single tick. 0.5
    private static double decelerationMultiplier = 2.0; //decelerationMultiplier is how many times faster the player will accelerate when they are still moving in a direction that they don't want to be mobing in. Helps the movement feel less like being on ice.
    private static double maxSpeed = 6 * accelerationSpeed; //Max speed should be a multiple of accelerationSpeed of there might be some overshooting errors (not guaranteed). 4
    private boolean canJump = true;
    private boolean dying = false;
    private boolean ontopOfBlock = true;
    private int spawnX;
    private int spawnY;
    static ImageView finish = InitContent.levelFinish;
    
    static LevelData lvlData = Server.lvl1;
    
    
    boolean pause = false;
 
   
    private KeyCode left;
    private KeyCode right;
    private KeyCode jump;
    private KeyCode Esc;
    
    private String playerName;
    private int characterWidth;
    private int characterHeight;
    private int platformBlockSize = 60;

    private Pane gameRoot;
    private int levelWidth;
    private ArrayList<ImageView> players;
    private ArrayList<ImageView> platforms;
    private ArrayList<Node> pickups;
    private ArrayList<Node> hazards;
    
    //WalkState, walkTotal, repeatFrame, repeatFrameTotal Can be used for both directions.
    private int walkState = 0;
    private int walkStateTotal = 7;
    private int repeatFrame = 0;
    private int repeatFrameTotal = 30;
    private int dyingRepeatFrame = 0;
    private int dyingState = 0;
    private int dyingRepeatFrameTotal = 2;
    private int dyingCurrentLoop = 0;
    private int dyingTotalLoops = 1;
    String characterName = GameMenu.characterName;
    Image charIdleImage = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_idle.png").toExternalForm());
    Image charFallImage = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_fall.png").toExternalForm());
    Image walkRightImage0 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_right0.png").toExternalForm());
    Image walkRightImage1 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_right1.png").toExternalForm());
    Image walkRightImage2 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_right2.png").toExternalForm());
    Image walkRightImage3 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_right3.png").toExternalForm());
    Image walkRightImage4 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_right4.png").toExternalForm());
    Image walkRightImage5 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_right5.png").toExternalForm());
    Image walkRightImage6 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_right6.png").toExternalForm());
    Image walkRightImage7 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_right7.png").toExternalForm());
    Image walkLeftImage0 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_left0.png").toExternalForm());
    Image walkLeftImage1 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_left1.png").toExternalForm());
    Image walkLeftImage2 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_left2.png").toExternalForm());
    Image walkLeftImage3 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_left3.png").toExternalForm());
    Image walkLeftImage4 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_left4.png").toExternalForm());
    Image walkLeftImage5 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_left5.png").toExternalForm());
    Image walkLeftImage6 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_left6.png").toExternalForm());
    Image walkLeftImage7 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_left7.png").toExternalForm());
    Image hitImage0 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_hit0.png").toExternalForm());
    Image hitImage1 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_hit1.png").toExternalForm());
    Image hitImage2 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_hit2.png").toExternalForm());
    Image hitImage3 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_hit3.png").toExternalForm());
    Image hitImage4 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_hit4.png").toExternalForm());
    Image hitImage5 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_hit5.png").toExternalForm());
    Image hitImage6 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_hit6.png").toExternalForm());
    Image hitImage7 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_hit7.png").toExternalForm());
    Image hitImage8 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_hit8.png").toExternalForm());
    Image finishLineImage2 = new Image(getClass().getResource("Tiles/laserSwitchGreenOn.png").toExternalForm());
    private ArrayList<Image> animRightList = new ArrayList<Image>();
    private ArrayList<Image> animLeftList = new ArrayList<Image>();
    private ArrayList<Image> animHitList = new ArrayList<Image>();

    private static String deathEffectPath = "GameFiles/src/res/sound/player_death.wav";
    private static String electricSoundPath = "GameFiles/src/res/sound/Electric.wav";
    private static String coinSoundPath = "GameFiles/src/res/sound/coin_collected.wav";

    private Sound soundEffect = new Sound();

    /**
     * A function that creates the humanPlayer. It follows specific movement rules. It can only move left, right or jump. It is affected by gravity.
     * @param name - The name of the player.
     * @param p_gameRoot - The gameplay root.
     * @param levelPlayers - the players who are playing the game.
     * @param levelPlatforms - The array containing level architecture.
     * @param levelPickups - The array containing level pickups.
     * @param levelHazards - The array containing level hazards, that are fatal for the player if encountered.
     * @param startX - The start x coordinate of the entity.
     * @param startY - The start y coordinate of the entity.
     * @param width - The widith of the entity.
     * @param height - The height of the entity.
     * @param controlsLeft - The control which moves the player to the left.
     * @param controlsRight - The control which moves the player to the right.
     * @param controlsJump - The control which makes the player perform a jump action.
     */
    public HumanPlayer(String name, Pane p_gameRoot, ArrayList<ImageView> levelPlayers, ArrayList<ImageView> levelPlatforms, ArrayList<Node> levelPickups, ArrayList<Node> levelHazards, int startX, int startY, int width, int height, KeyCode controlsLeft, KeyCode controlsRight, KeyCode controlsJump) {
    	//HumanPlayer Constructor.
    	
    	gameRoot = p_gameRoot;
    	
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
        animHitList.add(hitImage0);
        animHitList.add(hitImage1);
        animHitList.add(hitImage2);
        animHitList.add(hitImage3);
        animHitList.add(hitImage4);
        animHitList.add(hitImage5);
        animHitList.add(hitImage6);
        animHitList.add(hitImage7);
        animHitList.add(hitImage8);

        entity.setFitHeight(60);
        entity.setFitWidth(60);
        entity.setX(-10);
        entity.setY(-20);
    	
        entity.setTranslateX(startX);
        entity.setTranslateY(startY);
        entity.getProperties().put("alive", true);

        gameRoot.getChildren().add(entity);
        levelPlayers.add(entity);
        
        
        entity.translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();

            if (offset > 640 && offset < levelWidth - 640) {
                gameRoot.setLayoutX(-(offset - 640));
            }
        });        
        
        //Instantiate properties.
        playerName = name;
        //platforms may need a function to update it if the level layout changes and the character is not remade.
        players = levelPlayers;
        platforms = levelPlatforms;
        pickups = levelPickups;
        hazards = levelHazards;
        
        playerVelocity = new Point2D(0, 0);
        levelWidth = curLvl[0].length() * 60; //I don't like hard-coding this. This should be taken from somewhere more central.
        
        left = controlsLeft;
        right = controlsRight;
        jump = controlsJump;
        spawnX = startX;
        spawnY = startY;
        characterWidth = width;
        characterHeight = height;

    	
    	accelerationSpeed = 2;
    	decelerationMultiplier = 4;
    	maxSpeed = 3 * accelerationSpeed;
    	canJump = true;
    	dying = false;
    	playerVelocity = new Point2D(0, 0);
    	
    }

    /**
     * A function that returns the player name.
     * @return - Returns the player name.
     */
    public String toString() {
    	return playerName;
    }

    public void updateImages(){
        characterName = GameMenu.characterName;

        charIdleImage = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_idle.png").toExternalForm());
        charFallImage = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_fall.png").toExternalForm());
        walkRightImage0 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_right0.png").toExternalForm());
        walkRightImage1 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_right1.png").toExternalForm());
        walkRightImage2 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_right2.png").toExternalForm());
        walkRightImage3 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_right3.png").toExternalForm());
        walkRightImage4 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_right4.png").toExternalForm());
        walkRightImage5 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_right5.png").toExternalForm());
        walkRightImage6 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_right6.png").toExternalForm());
        walkRightImage7 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_right7.png").toExternalForm());
        walkLeftImage0 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_left0.png").toExternalForm());
        walkLeftImage1 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_left1.png").toExternalForm());
        walkLeftImage2 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_left2.png").toExternalForm());
        walkLeftImage3 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_left3.png").toExternalForm());
        walkLeftImage4 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_left4.png").toExternalForm());
        walkLeftImage5 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_left5.png").toExternalForm());
        walkLeftImage6 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_left6.png").toExternalForm());
        walkLeftImage7 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_walk_left7.png").toExternalForm());
        Image hitImage0 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_hit0.png").toExternalForm());
        Image hitImage1 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_hit1.png").toExternalForm());
        Image hitImage2 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_hit2.png").toExternalForm());
        Image hitImage3 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_hit3.png").toExternalForm());
        Image hitImage4 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_hit4.png").toExternalForm());
        Image hitImage5 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_hit5.png").toExternalForm());
        Image hitImage6 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_hit6.png").toExternalForm());
        Image hitImage7 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_hit7.png").toExternalForm());
        Image hitImage8 = new Image(getClass().getResource(characterName + "/PNG/Poses HD/character_hit8.png").toExternalForm());

        animLeftList.clear();
        animRightList.clear();
        animHitList.clear();

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
        animHitList.add(hitImage0);
        animHitList.add(hitImage1);
        animHitList.add(hitImage2);
        animHitList.add(hitImage3);
        animHitList.add(hitImage4);
        animHitList.add(hitImage5);
        animHitList.add(hitImage6);
        animHitList.add(hitImage7);
        animHitList.add(hitImage8);
    }
    
    
    // The class does know what keys it should look for. I would like to replace rightPressed with isPressed(right) . 
    /**
     * A functions that reads the keys that are pressed as inputs and performs actions in the game based on that.
     * @param leftPressed - The player moves to the left.
     * @param rightPressed - The player moves to the right.
     * @param jumpPressed - The player performs a jump action.
     * @param resetPressed - The player gets a reset at the start of the level.
     * @param primaryStage - The stage in which the game occurs.
     */
    public void update(boolean leftPressed, boolean rightPressed, boolean jumpPressed, boolean resetPressed, Stage primaryStage) {

        if(dying){
            dyingAnim();
        }
        else {
            pickupCollision();
            hazardCollision();
            finishDetection(primaryStage);

            if (resetPressed) {
                this.reset(primaryStage);
            }

            if (jumpPressed && entity.getTranslateY() >= 4) {
                jumpPlayer();

            }

            //When the player presses the A or D key, they will begin to accelerate until a maximum speed.
            //The velocity is updated on the key press and the movement is handled after.
            //The player decelerates to a X velocity of 0 of they are not pressing either A or D.
            if (leftPressed && entity.getTranslateX() >= 5 && (int)playerVelocity.getX() >= -maxSpeed) {

                double multiply = 1.0;
                //If the player is trying to swap directions, do it a decelerationMultiplier times as fast.
                if ((int)playerVelocity.getX() > 0) {
                    multiply = decelerationMultiplier;
                }
                playerVelocity = playerVelocity.add(-(accelerationSpeed * multiply), 0);
            }
            else if (rightPressed && entity.getTranslateX() + characterWidth <= levelWidth - 5 && (int)playerVelocity.getX() <= maxSpeed) {

                double multiply = 1.0;
                //If the player is trying to swap directions, do it decelerationMultiplier times as fast.
                if ((int)playerVelocity.getX() < 0) {
                    multiply = decelerationMultiplier;
                }
                playerVelocity = playerVelocity.add((accelerationSpeed * multiply), 0);
            }
            else {
                //The player is not pressing A or D so they must decelerate to a velocity of 0.
                if ((int)playerVelocity.getX() < 0) {
                    //Velocity is between 0 and -maxSpeed so we must add to bring to 0.
                    playerVelocity = playerVelocity.add(accelerationSpeed, 0);
                }
                else if ((int)playerVelocity.getX() > 0) {
                    //Velocity is between 0 and maxSpeed so we must subtract to bring to 0.
                    playerVelocity = playerVelocity.add(-accelerationSpeed, 0);
                }
            }

            if (playerVelocity.getY() < 10) {
                playerVelocity = playerVelocity.add(0, 1);
            }

            movePlayerY(entity, playerVelocity);
            movePlayerX(entity, playerVelocity);
        }

    }
    
    /**
     * A function that translates the pick up collisions that occur during the game such as coins.
     */
    private void pickupCollision() {
    	//Pickup collision detection
        //You cannot remove Nodes during the iteration. You must collect a list of the one's you want to remove and then remove them after.
        ArrayList<Node> toRemove = new ArrayList<Node>();
        for (Node pickup : pickups) {
        	if (entity.getTranslateY() + characterHeight > pickup.getTranslateY() && entity.getTranslateY() < pickup.getTranslateY() + pickup.getLayoutBounds().getHeight()) {
        		if (entity.getTranslateX() + characterWidth > pickup.getTranslateX() && entity.getTranslateX() < pickup.getTranslateX() + pickup.getLayoutBounds().getWidth()) {

	        		//Collision detected!
                   
                    if(GameMenu.soundEffectMute == false) {
                        soundEffect.setFile(coinSoundPath);
                        soundEffect.play();
                    }
	        		getCoin();
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
     * A function that detects the hazard collision and performs actions based on that.
     */
    private void hazardCollision() {
        int isded = 1;
    	//Hazard collision detection
        //You cannot remove Nodes during the iteration. You must collect a list of the one's you want to remove and then remove them after.
        for (Node hazard : hazards) {
        	if (entity.getTranslateY() + characterHeight > hazard.getTranslateY() && entity.getTranslateY() < hazard.getTranslateY() + hazard.getLayoutBounds().getHeight()) {
        		if (entity.getTranslateX() + characterWidth > hazard.getTranslateX() && entity.getTranslateX() < hazard.getTranslateX() + hazard .getLayoutBounds().getWidth()) {
	        		//Collision detected!

                    if(entity.getTranslateY() > 700){
                        //The hazard that has been touched is one at the bottom of the map.
                        death();
                    }
                    else{
                        //The hazard is in the player's view
                        dyingRepeatFrame = 0;
                        dying = true;
                        if(GameMenu.soundEffectMute == false) {
                            soundEffect.setFile(electricSoundPath);
                            soundEffect.play();
                        }

                    }


        		}
        	}
        }

    }
    
    /**
     * A function that implements the coin collection mechanism in the game, as well as the sound effects made when this collection occurs.
     */
    private void getCoin() {
        GameMenu.coins++;
        soundEffect.setFile(coinSoundPath);
        if(GameMenu.soundEffectMute == false) {
            soundEffect.play();
        }

    }

    /**
     * A function that resets the player to the start of the level.
     */
    private void reset(Stage primaryStage) {
    	System.out.println(this.toString() + ": Reset themself.");
    	canJump = false;
    	
		canJump = true;
		gameRoot.setLayoutX(0);
		entity.setTranslateX(spawnX);
		entity.setTranslateY(spawnY);

		resetLevel(primaryStage);
    }


    /**
     * A function that implements the mechanism of the robot's death.
     */
    private void death() {
    	//Events on dying. Could be losing points, playing a sound/animation etc.

    	canJump = false;


        if(GameMenu.soundEffectMute == false) {
            soundEffect.setFile(deathEffectPath);
            soundEffect.play();
        }
    	
        gameRoot.setLayoutX(0);
    	entity.setTranslateX(spawnX);
		entity.setTranslateY(spawnY);

    }
    
    
    /**
     * A function that sends information regarding which input key is pressed.
     */
    private boolean isPressed(KeyCode key) {

        return playerKeys.getOrDefault(key, false);
    }

    /**
     * A function that updates the player's location according to the X coordinate.
     * @param player - The x coordinate of the location.
     * @param playervelocity - The velocity of the player.
     */
    private void movePlayerX(Node player, Point2D playervelocity) {
        int value = (int)playerVelocity.getX();
        boolean movingRight = value > 0;
        
        if(ontopOfBlock && value == 0) {
        	entity.setImage(charIdleImage);
        }

        //Loops once for each unit of velocity. This is the reason that we cannot have floating speeds.
        for (int i = 0; i < Math.abs(value); i++) {
        	//Checks collision for every platform that is registered to the level. Keep in mind that getting the position of a rectangular object returns the bottom left corner of it.
            for (Node platform : platforms) {
            	if (player.getTranslateY() + characterHeight > platform.getTranslateY() && player.getTranslateY() < platform.getTranslateY() + platform.getLayoutBounds().getHeight()) {
                    if (movingRight) {
                    	//If we're moving to the right then we need to find the x positions of the right side of the character (origin plus width) and the left size of the block (just the origin).
                        if (player.getTranslateX() + characterWidth == platform.getTranslateX()) {                      	
                            return;
                        }
                    }
                    else {
                    	//If we're moving left then we need to find the x positions of the left side of the character (just the origin) and the right side of the platform (origin plus platform block width).
                        if (player.getTranslateX() == platform.getTranslateX() + platform.getLayoutBounds().getWidth()) {
                            return;
                        }
                    }
                }
            }
            //We change the position of the character here. The return statements above will cause us to skip this if we have detected a collision.   
            if (canJump) {
            	walkAnim(movingRight);
            }
            player.setTranslateX(player.getTranslateX() + (movingRight ? 1 : -1));
        }
        
        
    }
    
    /**
     * A function that updates the player's location according to the Y coordinate.
     * @param player - The Y coordinate of the location.
     * @param playervelocity - The velocity of the player.
     */
    private void movePlayerY(Node player, Point2D playervelocity) {
       int value = (int)playerVelocity.getY();
        boolean movingDown = value > 0;

        //Loops once for each unit of velocity. This is the reason that we cannot have floating speeds.
        for (int i = 0; i < Math.abs(value); i++) {
        	//Checks collision for every platform that is registered to the level. Keep in mind that getting the position of a rectangular object returns the bottom left corner of it.
            for (Node platform : platforms) {
            	if (player.getTranslateX() + characterWidth > platform.getTranslateX() && player.getTranslateX() < platform.getTranslateX() + platform.getLayoutBounds().getWidth()) {
                    if (movingDown) {
                    	//If we're moving down then we need to find the y positions of the bottom side of the character (just the origin) and the top side of the platform (origin plus platform block height).
                        if (player.getTranslateY() + characterHeight == platform.getTranslateY()) {
                            player.setTranslateY(player.getTranslateY() - 1);
                            //We have touched down onto the top of a platform to we reset our jump ability.                        
                            if (playerVelocity.getX() == 0) {
                                //entity.setImage(charIdleImage);
                            }
                            canJump = true;  
                            ontopOfBlock = true;
                            return;
                        }
                        else {
                        	//entity.setImage(charFallImage);
                        }
                    }
                    else {
                    	//If we're moving up then we need to find the y positions of the top side of the character (the origin plus character height) and the bottom side of the platform (just the origin).
                        if (player.getTranslateY() == platform.getTranslateY() + platform.getLayoutBounds().getHeight()) {
                        	//We have bumped our head onto the bottom of a block.
                            playerVelocity = playerVelocity.add(0,-value);
                            return;
                        }
                    }
                }
            }
            
            //We change the position of the character here. The return statements above will cause us to skip this if we have detected a collision.
            if (!ontopOfBlock) {
                entity.setImage(charFallImage);
            }
            ontopOfBlock = false;
            player.setTranslateY(player.getTranslateY() + (movingDown ? 1 : -1));
        }
    }
    

    /**
     * A function that implements the player's jump mechanism in the game.
     */
    private void jumpPlayer() {
        if (canJump) {
            playerVelocity = playerVelocity.add(0, -30);
            canJump = false;
        }
    }
    
    /**
     * A function that implements the player's moving animations.
     * @param movingRight - A boolean variable that determines whether the animation is going to be moving to the right or else to the left.
     */
    private void walkAnim(boolean movingRight) {
    	if (repeatFrame == repeatFrameTotal) {

    		if (movingRight) {
    			entity.setImage(animRightList.get(walkState));
    		}
    		else {
    			entity.setImage(animLeftList.get(walkState));
    		}
        	
        	repeatFrame = 0;
        	walkState++;
        	
        	if(walkState > walkStateTotal) {
        		walkState = 0;
        	}
    	}
    	else {
    		repeatFrame ++;
    	}
    }

    private void dyingAnim() {
        if (dyingRepeatFrame == dyingRepeatFrameTotal) {
            entity.setImage(animHitList.get(dyingState));
            dyingRepeatFrame = 0;
            dyingState++;

            if(dyingState > animHitList.size() - 1){
                dyingCurrentLoop ++;
                dyingState = 0;
            }

            if(dyingCurrentLoop > dyingTotalLoops){
                dying = false;
                dyingRepeatFrame = 0;
                dyingState = 0;
                dyingCurrentLoop = 0;
                death();
            }


        }
        else {
            dyingRepeatFrame ++;
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
                Main.gameMenu.gameOver(primaryStage,Main.gameScene, Main.player1, "Player 1");
            }
        }
    }

    public void resetLevel(Stage primaryStage){
        ImageView finish = InitContent.levelFinish;
        finish.setImage(finishLineImage2);
        gameRoot.getChildren().clear();
        Main.appRoot.getChildren().remove(gameRoot);
        String[] newLevel = Server.lvl1.levelGen();
        Main.setLvl(newLevel);

        gameRoot.setLayoutX(0);
        InitContent.levelBuilder(gameRoot);
        updateLayout();

        entity.setTranslateX(spawnX);
        entity.setTranslateY(spawnY);
        gameRoot.getChildren().add(entity);

        Main.oppPlayer.entity.setTranslateX(spawnX);
        Main.oppPlayer.entity.setTranslateY(spawnY);
        gameRoot.getChildren().add(Main.oppPlayer.entity);
        Main.appRoot.getChildren().add(gameRoot);

        Main.xPath = InitContent.aiXPath();
        Main.yPath = InitContent.aiYPath();
        Main.loop = 0;
        gameRoot.getChildren().add(Main.aiPlayer.getEntity());

        levelWidth = LevelData.currentLevelWidth;
    }

    /**
     * Updates the level layout for the character.
     */
    private void updateLayout() {
         ImageView finish = InitContent.levelFinish;
    	 players = Main.players;
         platforms = InitContent.platforms;
         pickups = InitContent.pickups;
         hazards = InitContent.hazards;
         finish = InitContent.levelFinish;
    }


}
