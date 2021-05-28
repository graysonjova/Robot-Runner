package demoreee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
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
 * The Character class creates the character which the user controls in the game.
 */
public class Character {
	
	protected ImageView entity;
	protected Point2D playerVelocity;
	
	String[] curLvl = Main.getLvl();
	
	protected int levelWidth = curLvl[0].length() * 60;
    
    //A character is any entity that can move and interact with the level. It is not limited to a human controlled move set.
    //A character could be asymmetric or symmetric to the player. It may not get killed by touching hazards or may not even fall through gravity for example.
    public Character(Pane gameRoot, int x, int y, int w, int h, Image image) {
    	//Character Constructor
    	
    	//Turn the character into a member of the game.
        entity = new ImageView(image);
        entity.resize(w, h);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.getProperties().put("alive", true);

        gameRoot.getChildren().add(entity);
        
        entity.translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();

            if (offset > 640 && offset < levelWidth - 640) {
                gameRoot.setLayoutX(-(offset - 640));
            }
        });        
        
        //Instantiate properties.
        playerVelocity = new Point2D(0, 0);

    }
    
    public Character() {
    	//Empty constructor so HumanPlayer 
    }
    
    
    public Node getEntity() {
    	//Some functions such as update() need the Node class. This is contained in the member variable "entity".
    	return entity;
    }
}
