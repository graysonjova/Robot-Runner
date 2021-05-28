package demoreee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Most important class; contains the game logic and calls functions from other classes.
 */
public class Main extends Application {

    public static int loop;
    public static int[] xPath;
    public static int[] yPath;



	
    private static Stage demoreStage;
	
	public static Stage getStage() {
        return demoreStage;
    }

    public static Pane appRoot = new Pane();
    public static Pane gameRoot = new Pane();
    public static Pane uiRoot = new Pane();
    public static Scene gameScene;

	
	private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();//basically to check if a key is pressed
	
	public static ArrayList<ImageView> players = new ArrayList<ImageView>();
	
    public static HumanPlayer player1;
    public static AiPlayer aiPlayer;
    public static GameMenu gameMenu;
    
    private static Client client;
    
    //Create an object of type OppPlayer called oppPlayer
    public static OppPlayer oppPlayer;

    private static String[] curLvl;
    
    /**
     * Check if a key has been pressed
     * 
     * @param key The key to check for press
     * @return True if the key is pressed, otherwise false
     */
    private boolean isPressed(KeyCode key) {
    	
        return keys.getOrDefault(key, false);
    }
    int ind =0;
    

    private boolean dialogEvent = false, running = true;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setMinHeight(759);
        primaryStage.setMinWidth(1296);

        primaryStage.setMaxHeight(759);
        primaryStage.setMaxWidth(1296);

        appRoot.getChildren().removeAll();

        InitContent initContent = new InitContent(appRoot, gameRoot, uiRoot); 
        //Creating pickup. Y = 0 is at near the top and an increasing coordinate takes you lower
        player1 = new HumanPlayer("Player1", gameRoot, players, initContent.platforms, initContent.pickups, initContent.hazards, 0, 580, 40, 40, KeyCode.A, KeyCode.D, KeyCode.SPACE);
        aiPlayer = new AiPlayer("AIPlayer", gameRoot, players, initContent.platforms, initContent.guide, initContent.hazards, 0, 620, 40, 40);

        oppPlayer = new OppPlayer("Player2", gameRoot, players, initContent.pickups, 0, 580, 40, 40);
        gameMenu = new GameMenu();
        
        demoreStage = primaryStage;
        gameScene = new Scene(appRoot);
        gameScene.setOnKeyPressed(event -> {
           	if (event.getCode() == KeyCode.ESCAPE) {
            	try {
            	    GameMenu.scoreText.setText("Coins: " + GameMenu.coins);
                    gameMenu.writeSaveFile();
            		gameMenu.PauseGame(primaryStage,gameScene, player1);
    			} catch (Exception e1) {

    				e1.printStackTrace();
    			}
           	} else keys.put(event.getCode(), true);
            });
        gameScene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        
           	
        primaryStage.setTitle("Robot Runner");
        primaryStage.setScene(gameScene);
        primaryStage.show();

        xPath = InitContent.aiXPath();
        yPath = InitContent.aiYPath();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                    int ind =0;
                    if (GameMenu.pause == false){
                        ind = ind+1;
                        player1.update(isPressed(KeyCode.A), isPressed(KeyCode.D), isPressed(KeyCode.SPACE), isPressed(KeyCode.O), primaryStage);
                        oppPlayer.update();
                        if(loop%1000<xPath.length-2){
                        
                            loop = aiPlayer.update2(loop,xPath,yPath, primaryStage);
                        }                 
                    };    
                 
                    
                    for (Spider spider : initContent.spiderList) {
                    	spider.update();
                    }
                
            }
        };
        timer.start();
    }
    
    public static void setLvl(String[] lvl) {
    	curLvl = lvl;
    }
    
    public static String[] getLvl() {
    	return curLvl;
    }

    
    public static void main(String[] args) {

    	
        launch(args);
    }
    
}
