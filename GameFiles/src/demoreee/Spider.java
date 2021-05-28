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

import java.util.Random;

/**
 * Moving obstacle that drops from the ceiling onto the player after a short delay.
 */
public class Spider extends Character  {

	private static double maxSpeed = 3; //The positive or negative velocity that the Spider moves with.
	private boolean aggro = false; //aggro is the boolean to tell the Spider if it has ever detected a player beneath it.
	private int freezeTime = 20; //The amount of ticks that the Spider waits for between detecting a player and falling.
    private int spawnX;
    private int spawnY;
    private boolean movingRight = true; //The Spider is either moving left or right.
    private int roamRange; //roamRange is the distance that the Spider will move before turning around.
    private int roamedDistance; //roamedDistance is the distance that the Spider has roamed within it's roaming range. It should be between 0 and roamRange.
    
    private String spiderName;
    private int characterWidth;
    private int characterHeight;
    private int platformBlockSize = 60;
    
    private int levelWidth;
    private ArrayList<ImageView> players;
    private ArrayList<ImageView> platforms;
    private ArrayList<Node> hazards;
    
    static Random rand = new Random();

    private Image idle = new Image(getClass().getResource("Robot/PNG/Parts HD/spiderIdleBig.png").toExternalForm());
    private Image alerted = new Image(getClass().getResource("Robot/PNG/Parts HD/spiderAggroBig.png").toExternalForm());
    
    private static String attackSoundPath = "GameFiles/src/res/sound/spider_attack.wav";


    private Sound soundEffect = new Sound();
    //A Spider is an AI controlled enemy to the player. It follows specific movement rules. It will hang on the underside of blocks and move left and right between certain bounds. If the player walks underneath the Spider, it will indicate a detection and then fall towards the player, killing them if they are hit.
    //A Spider is a special type of hazard.
    public Spider(String name, Pane gameRoot, ArrayList<ImageView> levelPlayers, ArrayList<ImageView> levelPlatforms, ArrayList<Node> levelHazards, int startX, int startY, int width, int height, int p_roamRange) {
    	//HumanPlayer Constructor.

    	
    	//Turn the character into a member of the game.
        entity = new ImageView(idle);
    	
        entity.setFitHeight(30);
        entity.setFitWidth(30);
        entity.setX(0);
        entity.setY(0);
    	
        entity.setTranslateX(startX);
        entity.setTranslateY(startY);
        entity.getProperties().put("alive", true);

        gameRoot.getChildren().add(entity);
        levelHazards.add(entity);
        
        
        
        //Instantiate properties.
        spiderName = name;
        //platforms may need a function to update it if the level layout changes and the character is not remade.
        players = levelPlayers;
        platforms = levelPlatforms;
        hazards = levelHazards;
        
        playerVelocity = new Point2D(0, 0);   
        
        spawnX = startX;
        spawnY = startY;
        characterWidth = width;
        characterHeight = height;
        roamRange = p_roamRange;
        
        int randomDistance = rand.nextInt(roamRange - 1);
        roamedDistance = randomDistance;   	
    	
    	playerVelocity = new Point2D(maxSpeed, 0);
    	
    }
    
    /**
	 * A function that makes the spider stop upon detecting the player and then drop after a short delay, therefore attacking the player in the initially detected position.
	 */
    public void update() {
    	
    	//Try to detect a player
    	if (aggro == false) {
        	for (Node player : players) {
        		//Player's X is within range of the Spider AND Player's Y is below the Spider's Y.		
                //If we're moving to the right then we need to find the x positions of the right side of the character (origin plus width) and the left size of the block (just the origin).
        		//If we're moving left then we need to find the x positions of the left side of the character (just the origin) and the right side of the platform (origin plus platform block width).   
        		//So the if statement below is: If they player's right side is more right than the Spider's left side AND the player's left side is more left than the Spider's right side.
        		
        		//Theoretically perfect intersection detects a bit too early.
    
        		//Thin window of intersection
        		if ( (player.getTranslateX() + 10 >= entity.getTranslateX() ) && ( player.getTranslateX() <= entity.getTranslateX() + 15 ) ) {	
        			//X are intersected
        			if ( player.getTranslateY() >= entity.getTranslateY() ) {
        				//Player's Y is greater than the Spider's Y. Higher coordinates are lower on the screen. The Spider should drop.
                       
        				//Stops the Spider from moving left/right.
        				playerVelocity = playerVelocity.subtract(playerVelocity.getX(), 0);
        				entity.setImage(alerted);
        				aggro = true;
                        if(GameMenu.soundEffectMute == false) {
                            soundEffect.setFile(attackSoundPath);
                            soundEffect.play();
                        }
        			}
                }
        	}

    	}
    	
    	
    	//If player detected
    	if (aggro == true) {
    		//A Player has been detected. Wait for the freeze time, then fall.
    		if (freezeTime > 0) {
    			freezeTime -= 1;
    		} 
    		else {
    			//The Spider has already waited. Fall.
    			
    			if (playerVelocity.getY() < 10) {
                    playerVelocity = playerVelocity.add(0, 2);
                }
                movePlayerY(entity, playerVelocity);
    		}
    		
    	}
    	
    	else {
    		//A player has not been detected. Roam.
    		if (roamedDistance > roamRange) {
    			movingRight = false;
    		}
    		if (roamedDistance < 0) {
    			movingRight = true;
    		}
    		
    		
    		if (movingRight) {
    			if ((int)playerVelocity.getX() <= 0) {
    				//Switch direction.
    				playerVelocity = playerVelocity.add(maxSpeed * 2, 0);    				
    			}
    			playerVelocity = playerVelocity.subtract(playerVelocity.getX(), 0);  
    			playerVelocity = playerVelocity.add(maxSpeed, 0); 
    			roamedDistance += maxSpeed;	
    		}
    		else {
    			if ((int)playerVelocity.getX() >= 0) {
    				//Switch direction.
    				playerVelocity.add(-(maxSpeed * 2), 0);
    			}
    			playerVelocity = playerVelocity.subtract(playerVelocity.getX(), 0);  
    			playerVelocity = playerVelocity.add(-maxSpeed, 0); 
    			roamedDistance -= maxSpeed;	
    		}
    		movePlayerX(entity, playerVelocity);
    		
    	}
    	

    }
    
    /**
	 * A function that updates the location of the player considering the X coordinate.
	 * @param player - Current position of the player
	 * @param playervelocity - The speed of the player 
	 */
    private void movePlayerX(Node player, Point2D playervelocity) {
        int value = (int)playerVelocity.getX();
        
        //Loops once for each unit of velocity. This is the reason that we cannot have floating speeds.
        for (int i = 0; i < Math.abs(value); i++) {
        	//Checks collision for every platform that is registered to the level. Keep in mind that getting the position of a rectangular object returns the bottom left corner of it.
            for (Node platform : platforms) {
            	if (player.getTranslateY() + characterHeight > platform.getTranslateY() && player.getTranslateY() < platform.getTranslateY() + platform.getLayoutBounds().getHeight()) {
                    if (movingRight) {
                    	//If we're moving to the right then we need to find the x positions of the right side of the character (origin plus width) and the left size of the block (just the origin).
                        if (player.getTranslateX() + characterWidth == platform.getTranslateX()) {
                        	roamedDistance = roamRange + 1;
                            return;
                        }
                    }
                    else {
                    	//If we're moving left then we need to find the x positions of the left side of the character (just the origin) and the right side of the platform (origin plus platform block width).
                        if (player.getTranslateX() == platform.getTranslateX() + platform.getLayoutBounds().getWidth()) {
                        	roamedDistance = -1;
                            return;
                        }
                    }
                }
            }
            //We change the position of the character here. The return statements above will cause us to skip this if we have detected a collision.
            player.setTranslateX(player.getTranslateX() + (movingRight ? 1 : -1));
        }
        
        
    }
    
    /**
	 * A function that updates the location of the player considering the Y coordinate.
	 * @param player - The current position of the player
	 * @param playervelocity - The player speed
	 */
    private void movePlayerY(Node player, Point2D playervelocity) {
        int value = (int)playerVelocity.getY();
         boolean movingDown = value > 0;

       //Loops once for each unit of velocity. This is the reason that we cannot have floating speeds.
         for (int i = 0; i < Math.abs(value); i++) {             
             
             //We change the position of the character here.
             player.setTranslateY(player.getTranslateY() + (movingDown ? 1 : -1));
         }
     }
    
	
}
