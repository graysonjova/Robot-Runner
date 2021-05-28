package demoreee;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The InitContent class creates in game content such as coins entities and hazards.
 */
public class InitContent {
	
    public static ArrayList<ImageView> platforms = new ArrayList<ImageView>();
    public static ArrayList<Node> pickups = new ArrayList<Node>();
    public static ArrayList<Node> hazards = new ArrayList<Node>();
    public static ArrayList<Spider> spiderList = new ArrayList<Spider>();
    public static ArrayList<Node> guide = new ArrayList<Node>();
    private static Spider spider1;
    static ImageView levelFinish;

    static Image platformImage = new Image(InitContent.class.getResource("Tiles/metalCenter.png").toExternalForm());
    static Image platformImageSticker = new Image(InitContent.class.getResource("Tiles/metalCenterSticker.png").toExternalForm());
    static Image platformImageWireShort = new Image(InitContent.class.getResource("Tiles/metalPlatformWireCropped.png").toExternalForm());
    static Image platformImageWireLong = new Image(InitContent.class.getResource("Tiles/metalPlatformWireAltCropped.png").toExternalForm());
    static Image platformImageLeftCurve = new Image(InitContent.class.getResource("Tiles/metalLeft.png").toExternalForm());
    static Image platformImageRightCurve = new Image(InitContent.class.getResource("Tiles/metalRight.png").toExternalForm());
    static Image finishLineImage1 = new Image(InitContent.class.getResource("Tiles/laserSwitchGreenOff.png").toExternalForm());
    static Image coinImage = new Image(InitContent.class.getResource("Icons/coin.gif").toExternalForm());
    static Image hazardImage = new Image(InitContent.class.getResource("Icons/Ball2.gif").toExternalForm());

	
    static String[] curLvl = Main.getLvl();
    
	private static int levelWidth;
	
	static int counter;
	
	/**
	 * A function that creates an entity and its shape. Assigns the property alive to this entity.
	 * @param x - The x coordinate of the entity.
	 * @param y - The y coordinate of the entity.
	 * @param w - The width of the entity, necessary for creating the Rectangle shape.
	 * @param h - The height of the entity, necessary for creating the Rectangle shape.
	 * @param color - Sets the color of the entity.
	 * @param gameRoot - The gameRoot which links the entity and its shape and coordinates to the map.
	 * @return - The entity with its shape and coordinates.
	 */
    private static Node createEntity(int x, int y, int w, int h, Color color, Pane gameRoot) {
        Rectangle entity = new Rectangle(w, h);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setFill(color);
        entity.getProperties().put("alive", true);
        gameRoot.getChildren().add(entity);
        return entity;
    }

    private static Node createCoin(int x, int y, int w, int h, Color color, Pane gameRoot) {
    	ImageView entity = new ImageView(coinImage);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setFitHeight(30);
        entity.setFitWidth(30);
        entity.getProperties().put("alive", true);
        gameRoot.getChildren().add(entity);
        return entity;
    }

    private static Node createHazard(int x, int y, int w, int h, Color color, Pane gameRoot) {
    	ImageView entity = new ImageView(hazardImage);
        entity.setTranslateX(x - 10);
        entity.setTranslateY(y - 25);
        entity.setFitHeight(45);
        entity.setFitWidth(45);
        entity.getProperties().put("alive", true);
        gameRoot.getChildren().add(entity);
        return entity;
    }


    /**
	 * A function that sets the scene of the game, adds the background gif, builds the level and binds them together.
	 * @param appRoot - The application root.
	 * @param gameRoot - The actual gameplay root.
	 * @param uiRoot - The user interface root.
	 */
	public InitContent(Pane appRoot, Pane gameRoot, Pane uiRoot) {
    	Image bkgndImg = new Image(getClass().getResource("skyline.gif").toExternalForm());
    	ImageView bkgnd = new ImageView(bkgndImg);
    	bkgnd.setFitHeight(720);
    	bkgnd.setFitWidth(1280);
    	//60 is the size of the blocks, 40 is the size of the player
    	String[] curLvl = Main.getLvl();
        levelWidth = curLvl[0].length() * 60;

        
        gameRoot = levelBuilder(gameRoot);

        appRoot.getChildren().removeAll();
        appRoot.getChildren().addAll(bkgnd, gameRoot, uiRoot);


    }

	/**
	 * A function that builds the level for the gameplay.
	 * @param gameRoot - The actual gameplay root.
	 * @return - This functions returns a gameRoot which has the level built within.
	 */
    public static Pane levelBuilder(Pane gameRoot){
    	clearLayout();
    	curLvl = Main.getLvl();
        for (int i = 0; i < curLvl.length; i++) {
            String line = curLvl[i];
            for (int j = 0; j < line.length(); j++) {
            	Node hazardFloor = createEntity(j*60, 900, 180, 60, Color.ORANGE, gameRoot);
            	hazards.add(hazardFloor);
                switch (line.charAt(j)) {
                    case '0':
                        break;
                    case '1':
                    	ImageView platform = new ImageView(platformImage);//create entity (location,location,width,length,color)
                    	platform.setTranslateX(j*60);
                    	platform.setTranslateY(i*60);
                    	//platform.resize(60, 60);
                    	platform.setFitHeight(60);
                    	platform.setFitWidth(60);
                    	
                        platforms.add(platform);
                        gameRoot.getChildren().add(platform);
                        break;
                    case '2':
                        counter = counter+1;
                        if(counter%2==1){
                    	Node hazardTop = createHazard((j*60) + 20, (i*60) + 40, 20, 20, Color.RED, gameRoot);
                        hazards.add(hazardTop);}
                        else{break;}
                        break;
                    case '3':
                    	Node pickup1 = createCoin((j*60) + 20, (i*60) + 20, 20, 20, Color.YELLOW, gameRoot);
                        pickups.add(pickup1);
                        break;
                    case '4':
                    	String name = "Spider" + spiderList.size();
                        Spider spiderTemp = new Spider(name, gameRoot, Main.players, platforms, hazards, (j*60) + 20, (i*60), 35, 35, levelWidth);  
                        spiderList.add(spiderTemp);
                        break;
                    case '7':
                        ImageView platformSticker = new ImageView(platformImageSticker);//create entity (location,location,width,length,color)
                        platformSticker.setTranslateX(j*60);
                        platformSticker.setTranslateY(i*60);
                        //platform.resize(60, 60);
                        platformSticker.setFitHeight(60);
                        platformSticker.setFitWidth(60);

                        platforms.add(platformSticker);
                        gameRoot.getChildren().add(platformSticker);
                        break;
                    case '8':
                        ImageView platformWireShort = new ImageView(platformImageWireShort);//create entity (location,location,width,length,color)
                        platformWireShort.setTranslateX(j*60);
                        platformWireShort.setTranslateY(i*60);
                        //platform.resize(60, 60);
                        platformWireShort.setFitHeight(27);
                        platformWireShort.setFitWidth(60);

                        platforms.add(platformWireShort);
                        gameRoot.getChildren().add(platformWireShort);
                        break;
                    case '9':
                        ImageView platformWireLong = new ImageView(platformImageWireLong);//create entity (location,location,width,length,color)
                        platformWireLong.setTranslateX(j*60);
                        platformWireLong.setTranslateY(i*60);
                        //platform.resize(60, 60);
                        platformWireLong.setFitHeight(47);
                        platformWireLong.setFitWidth(60);

                        platforms.add(platformWireLong);
                        gameRoot.getChildren().add(platformWireLong);
                        break;
                    case 'r':
                        ImageView platformedgeright = new ImageView(platformImageRightCurve);//create entity (location,location,width,length,color)
                        platformedgeright.setTranslateX(j*60);
                        platformedgeright.setTranslateY(i*60);
                        //platform.resize(60, 60);
                        platformedgeright.setFitHeight(60);
                        platformedgeright.setFitWidth(60);

                        platforms.add(platformedgeright);
                        gameRoot.getChildren().add(platformedgeright);
                        break;
                    case 'l'://this is L not one
                        ImageView platformedgeleft = new ImageView(platformImageLeftCurve);//create entity (location,location,width,length,color)
                        platformedgeleft.setTranslateX(j*60);
                        platformedgeleft.setTranslateY(i*60);
                        //platform.resize(60, 60);
                        platformedgeleft.setFitHeight(60);
                        platformedgeleft.setFitWidth(60);

                        platforms.add(platformedgeleft);
                        gameRoot.getChildren().add(platformedgeleft);
                        break;
                    case 'f':
                        ImageView finishline = new ImageView(finishLineImage1);//create entity (location,location,width,length,color)
                        finishline.setTranslateX(j*60);
                        finishline.setTranslateY(i*60);
                        //platform.resize(60, 60);
                        finishline.setFitHeight(60);
                        finishline.setFitWidth(60);

                        levelFinish = finishline;
                        platforms.add(finishline);
                        gameRoot.getChildren().add(finishline);
                        break;
                                        
                    case 'p':
                    	Node guide1 = createEntity((j*60), (i*60), 60, 60, Color.TRANSPARENT, gameRoot);
                        guide.add(guide1);
                        break;
                     
                
                };
                }
            }
        return gameRoot;}

    /**
	 * A function that aims to create an array of the Y coordinate of the pathfinding, which is useful for the Ai.
	 * @return - This function returns the Y coordinate of the pathfinding.
	 */
        public static int[] aiYPath(){
        int ind =0;
        int max = 0;
        int maxloc =0;
        for (int i = 0; i < curLvl.length; i++) {
            String line = curLvl[i];
            if(line.length()>=max){
                
                max = line.length();
                maxloc = i;
            }
        }
        int[] yPath = new int[200];
        
        for(int i =0;i<max;i++){//then we find every f, store the Ycoordinate in the array.
            for(int j=curLvl.length-1; j>=0;j--){
                String line = curLvl[j];
                switch (line.charAt(i)) {
                    case 'p':
                        yPath[ind] = j;
                        ind = ind+1;
                        break;
                    }
                
            }
        }
        for(int i=0; i<ind-1; i++){
        	int switchind =0;
            if(yPath[i+1]==yPath[i]+1){
            	int temp = yPath[i+2];
                yPath[i+2] = yPath[i+1];
                yPath[i+1] = temp;
            }

        }
    
    return yPath;
}


   /**
    * A function that aims to create an array of the X coordinate of the pathfinding, which is useful for the Ai.
    * @return - This function returns the X coordinate of the pathfinding.
   */
    public static int[] aiXPath(){//the point of this function is to create an array of the Xcoordinate of the pothfinding.
        int ind =0;
        int max = 0;
        int maxloc =0;
        for (int i = 0; i < curLvl.length; i++) {//here is where we find the maximum length of each string
            String line = curLvl[i];
            if(line.length()>=max){
                max = line.length();
                maxloc = i;
            }
        }
        int[] xPath = new int[200];
        for(int i =0;i<max;i++){
            for(int j=0; j<curLvl.length;j++){//then we find every f, store the Xcoordinate in the array.
                String line = curLvl[j];
                switch (line.charAt(i)) {
                    case 'p':
                        xPath[ind] = i;
                        ind = ind+1;
                        break;
                    }
                
            }
        }      
    
    return xPath;
}

   /**
     * A function that removes all elements from lists (used for generating a new level),
     */
    private static void clearLayout() {
    	platforms.removeAll(platforms);
    	pickups.removeAll(pickups);
    	hazards.removeAll(hazards);
    	spiderList.removeAll(spiderList);
    }
}
