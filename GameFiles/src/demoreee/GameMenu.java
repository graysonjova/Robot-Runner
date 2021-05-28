package demoreee;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.util.Duration;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;


/**
 * Main starting class, contains the main UI.
 */
public class GameMenu extends Application {

	private static Stage menuStage;
	private static Stage pStage;

	private static GameMenu gameMenuSingleton;



	Stage window;


	Scene scene1, scene2, scene3, scene4, scene5, sceneOver;//declaring menu scenes

	static Main demo = new Main();

	static boolean pause  = false;

	public static boolean musicMute = false;

	private static String hoverSound = "GameFiles/src/res/sound/click.wav";
	private static String selectSound = "GameFiles/src/res/sound/select.wav";
	public static String backgroundMusic;
	public static Sound bgMusic = new Sound();
	public static boolean soundEffectMute = false;
	private Client client;
	private String soundEffectsTextOn = "Turn sound effects on";
	private String soundEffectsTextOff = "Turn sound effects off";
	private String bgMusicTextOn = "Turn background music on";
	private String bgMusicTextOff = "Turn background music off";


	private final String fileName = "save.txt";
	private final int costMusicItem2 = 10;
	public static boolean boolMusicItem2;
	private final int costMusicItem3 = 20;
	public static boolean boolMusicItem3;
	public static int coins;
	public static Text scoreText;

	public static boolean characterOwnedFemaleAdventurer;
	private final int femaleAdventurerCost = 100;
	public static boolean characterOwnedMaleAdventurer;
	private final int maleAdventurerCost = 100;
	public static String characterName;
	public static String characterNameRobot = "Robot";
	public static String characterNameMaleAdventurer = "Male Adventurer";
	public static String characterNameFemaleAdventurer = "Female Adventurer";

	public static Scene mainMenuScene;
	public static Scene shopScene;
	public static Scene settingsScene;

	private Sound soundEffect = new Sound();

	public static HumanPlayer singlePlayerCharacter;

	static Random rand = new Random();

	public static void main(String[] args) {
		launch(args) ;

	}

	public static Stage getPrimaryStage(){
		return pStage;
	}

	/**
	 * Generates 'save.txt' if the file does not exist
	 * Reads the information saved in the 'save.txt' file
	 * Checks to see that the song loaded has been unlocked
	 */

	private void readSaveFile(){
		try {
			File myObj = new File(fileName);
			if (myObj.createNewFile()) {
				FileWriter myWriter = new FileWriter(fileName);
				myWriter.write(String.valueOf(0));
				myWriter.write(System.getProperty("line.separator"));
				myWriter.write("res/sound/Onward!.wav");
				myWriter.write(System.getProperty("line.separator"));
				myWriter.write(characterNameRobot);
				myWriter.write(System.getProperty("line.separator"));
				myWriter.write(String.valueOf(false));
				myWriter.write(System.getProperty("line.separator"));
				myWriter.write(String.valueOf(false));
				myWriter.write(System.getProperty("line.separator"));
				myWriter.write(String.valueOf(false));
				myWriter.write(System.getProperty("line.separator"));
				myWriter.write(String.valueOf(false));
				myWriter.write(System.getProperty("line.separator"));
				myWriter.write(String.valueOf(false));
				myWriter.write(System.getProperty("line.separator"));
				myWriter.write(String.valueOf(false));
				myWriter.close();
			}
		} catch (IOException e) {
			System.out.println("An error occurred while generating file");
			e.printStackTrace();
		}


		//try to read from save file
		try {
			File myObj = new File(fileName);
			Scanner myReader = new Scanner(myObj);
			if(myReader.hasNextInt())
				coins = Integer.parseInt(myReader.nextLine());
			else
				coins = 0;
			if(myReader.hasNext())
				backgroundMusic = myReader.nextLine();
			else
				backgroundMusic = "res/sound/Onward!.wav";
			if(myReader.hasNext())
				characterName = myReader.nextLine();
			else
				characterName = characterNameRobot;
			if(myReader.hasNextBoolean())
				musicMute = Boolean.parseBoolean(myReader.nextLine());
			else
				musicMute = false;
			if(myReader.hasNextBoolean())
				soundEffectMute = Boolean.parseBoolean(myReader.nextLine());
			else
				soundEffectMute = false;
			if(myReader.hasNextBoolean())
				boolMusicItem2 = Boolean.parseBoolean(myReader.nextLine());
			else
				boolMusicItem2 = false;
			if(myReader.hasNextBoolean())
				boolMusicItem3 = Boolean.parseBoolean(myReader.nextLine());
			else
				boolMusicItem3 = false;
			if(myReader.hasNextBoolean())
				characterOwnedFemaleAdventurer = Boolean.parseBoolean(myReader.nextLine());
			else
				characterOwnedFemaleAdventurer = false;
			if(myReader.hasNextBoolean())
				characterOwnedMaleAdventurer = Boolean.parseBoolean(myReader.nextLine());
			else
				characterOwnedMaleAdventurer = false;
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred while loading save file.");
			e.printStackTrace();
		}

		//check to see if the backgroundMusic loaded from the save file has been acquired
		if(!backgroundMusic.equals("GameFiles/src/res/sound/Onward!.wav")&&
		!backgroundMusic.equals("GameFiles/src/res/sound/A Trillion Stars.wav")&&
		!backgroundMusic.equals("GameFiles/src/res/sound/Cosmic Love.wav"))
			backgroundMusic = "GameFiles/src/res/sound/Onward!.wav";
		if(backgroundMusic.equals("GameFiles/src/res/sound/A Trillion Stars.wav") && !boolMusicItem2)
			backgroundMusic = "GameFiles/src/res/sound/Onward!.wav";
		if(backgroundMusic.equals("GameFiles/src/res/sound/Cosmic Love.wav") && !boolMusicItem3)
			backgroundMusic = "GameFiles/src/res/sound/Onward!.wav";

		if(!characterName.equals(characterNameRobot)&&
		!characterName.equals(characterNameFemaleAdventurer)&&
		!characterName.equals(characterNameMaleAdventurer))
			characterName = characterNameRobot;
		if(characterName.equals(characterNameFemaleAdventurer) &&
		!characterOwnedFemaleAdventurer)
			characterName = characterNameRobot;
		if(characterName.equals(characterNameMaleAdventurer)&&
		!characterOwnedMaleAdventurer)
			characterName = characterNameRobot;

	}

	/**
	 * Saves the settings, unlocked items and the amount of coins collected so far
	 */
	public void writeSaveFile(){
		try {
			FileWriter myWriter = new FileWriter(fileName);
			myWriter.write(String.valueOf(coins));
			myWriter.write(System.getProperty("line.separator"));
			myWriter.write(String.valueOf(backgroundMusic));
			myWriter.write(System.getProperty("line.separator"));
			myWriter.write(String.valueOf(characterName));
			myWriter.write(System.getProperty("line.separator"));
			myWriter.write(String.valueOf(musicMute));
			myWriter.write(System.getProperty("line.separator"));
			myWriter.write(String.valueOf(soundEffectMute));
			myWriter.write(System.getProperty("line.separator"));
			myWriter.write(String.valueOf(boolMusicItem2));
			myWriter.write(System.getProperty("line.separator"));
			myWriter.write(String.valueOf(boolMusicItem3));
			myWriter.write(System.getProperty("line.separator"));
			myWriter.write(String.valueOf(characterOwnedFemaleAdventurer));
			myWriter.write(System.getProperty("line.separator"));
			myWriter.write(String.valueOf(characterOwnedMaleAdventurer));
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	private void reloadGame(Stage primaryStage){
		pStage = primaryStage;

		try {
			singlePlayerCharacter.resetLevel(primaryStage);
			Main.oppPlayer.entity.setVisible(false);
			primaryStage.setScene(Main.gameScene);
			primaryStage.show();

		} catch (Exception e1) {

			try {
				LevelData lvl1 = new LevelData();
				lvl1.genLevel();
				Main.setLvl(lvl1.getLevel());

				demo.start(primaryStage);
				Main.oppPlayer.entity.setVisible(false);
				pause = false;
			} catch (Exception e2) {

				e1.printStackTrace();
				e2.printStackTrace();
			}
		}
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setMinHeight(759);
		primaryStage.setMinWidth(1296);

		primaryStage.setMaxHeight(759);
		primaryStage.setMaxWidth(1296);


		menuStage = primaryStage;

		ImageView bgImage = new ImageView(new Image(getClass().getResource("./bgNightComp.gif").toExternalForm()));
		bgImage.setFitHeight(720);
		bgImage.setFitWidth(1280);
		BackgroundImage backgroundImage = new BackgroundImage(bgImage.getImage(), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER ,null);
		Pane bgPane = new Pane();
		bgPane.setBackground(new Background(backgroundImage));

		readSaveFile();

		//music is set to the "backgroundMusic" file
		bgMusic.setFile(backgroundMusic);
		//check to see if player has previously turned music off or not
		if(!musicMute){
			//music starts playing
			bgMusic.play();
			bgMusic.loop();
		}

		window = primaryStage;
		window.setTitle("GameMenu");
		window.setOnCloseRequest(e -> closeGame());
		
		//text for the amount of coins collected
		scoreText = new Text();
		scoreText.setFill(Color.WHITE);
		scoreText.setText("Coins: " + coins);
		scoreText.setTranslateX(1070);
		scoreText.setTranslateY(-40);
		

	    Text tJump = new Text();
		tJump.setTranslateY(30);
	    tJump.setText("Jump: SPACE");
	    tJump.setFill(Color.WHITE);
	    tJump.setFont(Font.font(null, 23));
	    tJump.setTextAlignment(TextAlignment.CENTER);


	    Text tForward = new Text();
	    tForward.setTranslateY(30);
	    tForward.setText("Move to the left: D");
	    tForward.setFill(Color.WHITE);
	    tForward.setFont(Font.font(null, 23));
	    tForward.setTextAlignment(TextAlignment.CENTER);


	    Text tBackwards = new Text();
	    tBackwards.setTranslateY(30);
	    tBackwards.setText("Move to the right: A");
	    tBackwards.setFill(Color.WHITE);
	    tBackwards.setFont(Font.font(null, 23));
	    tBackwards.setTextAlignment(TextAlignment.CENTER);

		
		Button button1 = new Button();
		button1.setText("      Single Player      ");
		button1.setOnAction(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(selectSound);
				soundEffect.play();
			}

			try{
				singlePlayerCharacter.updateImages();
			}catch(Exception e1){

			}

			pause = false;
			reloadGame(primaryStage);

		});
		button1.setOnMouseEntered(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(hoverSound);
				soundEffect.play();
			}
		});
		button1.setTranslateX(10);
		button1.setTranslateY(20);
		
		Button buttonMultiplayer = new Button();
		buttonMultiplayer.setText("      Multiplayer      ");
		buttonMultiplayer.setOnAction(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(selectSound);
				soundEffect.play();
			}
			//window.setScene(scene5);
			try {
				window.setScene(scene5);
		    	client = new Client("multiplayer");
		    	demo.start(primaryStage);
		    	demo.aiPlayer.setSpeed(1047483647);
		    	demo.aiPlayer.entity.setVisible(false);
			 	 
			} catch (Exception e1) {

				e1.printStackTrace();
			}
		});
		buttonMultiplayer.setOnMouseEntered(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(hoverSound);
				soundEffect.play();
			}
		});
		buttonMultiplayer.setTranslateX(10);
		buttonMultiplayer.setTranslateY(30);


		Button buttonShopBack = new Button();
		buttonShopBack.setText("      Back      ");// shop back button
		buttonShopBack.setOnAction(e -> {
		if(GameMenu.soundEffectMute == false) {
			soundEffect.setFile(selectSound);
			soundEffect.play();
		}
		window.setScene(scene1);
		});
		buttonShopBack.setOnMouseEntered(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(hoverSound);
				soundEffect.play();
			}
		});
		buttonShopBack.setTranslateX(600);
		buttonShopBack.setTranslateY(20);
		
		
		Button buttonLobbyBack = new Button();
		buttonLobbyBack.setText("      Back      ");// lobby back button
		buttonLobbyBack.setOnAction(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(selectSound);
				soundEffect.play();
			}
			window.setScene(scene1);
		});
		buttonLobbyBack.setOnMouseEntered(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(hoverSound);
				soundEffect.play();
			}
		});
		buttonLobbyBack.setTranslateX(10);
		buttonLobbyBack.setTranslateY(20);
		buttonLobbyBack.setAlignment(Pos.CENTER);


		Button button3 = new Button();
		button3.setText("      Shop      ");
		button3.setOnAction(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(selectSound);
				soundEffect.play();
			}


			scoreText.setText("Coins: " + coins);
			window.setScene(scene2);
		});
		button3.setOnMouseEntered(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(hoverSound);
				soundEffect.play();
			}
		});
		button3.setTranslateX(10);
		button3.setTranslateY(40);
		

		Button button4 = new Button();
		button4.setText("Controls and sound");
		button4.setOnAction(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(selectSound);
				soundEffect.play();
			}
			scoreText.setText("Coins: " + coins);
			window.setScene(scene3);
		});
		button4.setOnMouseEntered(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(hoverSound);
				soundEffect.play();
			}
		});
		button4.setTranslateX(10);
		button4.setTranslateY(50);
		
		
		Button button5 = new Button();
		button5.setText("      Exit      ");//exits the game
		button5.setOnAction(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(selectSound);
				soundEffect.play();
			}
			closeGame();
		});
		button5.setOnMouseEntered(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(hoverSound);
				soundEffect.play();
			}
		});
		button5.setTranslateX(10);
		button5.setTranslateY(60);
		
		
		Button buttonSettingsBack = new Button();
		buttonSettingsBack.setText("        Back        ");//settings back button
		buttonSettingsBack.setOnAction(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(selectSound);
				soundEffect.play();
			}
			window.setScene(scene1);
		});
		buttonSettingsBack.setOnMouseEntered(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(hoverSound);
				soundEffect.play();
			}
		});
		buttonSettingsBack.setTranslateX(10);
		buttonSettingsBack.setTranslateY(20);
		buttonSettingsBack.setAlignment(Pos.CENTER);
		
		
		Button buttonSettingsMusic = new Button();
		if(!musicMute){
			buttonSettingsMusic.setText(bgMusicTextOff);
		}else{
			buttonSettingsMusic.setText(bgMusicTextOn);
		}
		buttonSettingsMusic.setOnAction(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(selectSound);
				soundEffect.play();
			}
			if(musicMute == false){
				musicMute = true;
				bgMusic.stop();
				buttonSettingsMusic.setText(bgMusicTextOn);
			}else{
				musicMute = false;
				buttonSettingsMusic.setText(bgMusicTextOff);
				bgMusic.setFile(backgroundMusic);
				bgMusic.play();
				bgMusic.loop();
			}
		}); 
		buttonSettingsMusic.setOnMouseEntered(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(hoverSound);
				soundEffect.play();
			}
		});
		buttonSettingsMusic.setTranslateX(10);
		buttonSettingsMusic.setTranslateY(20);
		buttonSettingsMusic.setAlignment(Pos.CENTER);
		
		
		Button button9 = new Button();
		button9.setText("      Main menu      ");
		button9.setOnAction(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(selectSound);
				soundEffect.play();
			}
			window.setScene(scene1);
		});
		button9.setOnMouseEntered(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(hoverSound);
				soundEffect.play();
			}
		});
		button9.setTranslateX(10);
		button9.setTranslateY(30);		

		
		Button buttonSettingsSounds = new Button();
		if(!soundEffectMute){
			buttonSettingsSounds.setText(soundEffectsTextOff);
		}else{
			buttonSettingsSounds.setText(soundEffectsTextOn);
		}
		buttonSettingsSounds.setOnAction(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(selectSound);
				soundEffect.play();
			}
			if(soundEffectMute == false){
				soundEffectMute = true;
				buttonSettingsSounds.setText(soundEffectsTextOn);
			}else{
				soundEffectMute = false;
				buttonSettingsSounds.setText(soundEffectsTextOff);
			}
		}); 
		buttonSettingsSounds.setOnMouseEntered(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(hoverSound);
				soundEffect.play();
			}
		});
		buttonSettingsSounds.setTranslateX(10);
		buttonSettingsSounds.setTranslateY(20);
		buttonSettingsSounds.setAlignment(Pos.CENTER);

		//buttons for changing the background music
		Button buttonMusic1 = new Button();
		Button buttonMusic2 = new Button();
		Button buttonMusic3 = new Button();


		buttonMusic1.setText("Robot Splash");
		buttonMusic1.setOnAction(e ->{
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(selectSound);
				soundEffect.play();
			}
			bgMusic.stop();
			backgroundMusic = "GameFiles/src/res/sound/Onward!.wav";
			bgMusic.setFile(backgroundMusic);
			buttonSettingsMusic.setText(bgMusicTextOff);
			musicMute = false;
			bgMusic.play();
			bgMusic.loop();
		});
		buttonMusic1.setOnMouseEntered(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(hoverSound);
				soundEffect.play();
			}
		});
		buttonMusic1.setTranslateX(450);
		buttonMusic1.setTranslateY(20);

		

		buttonMusic2.setText("Arcade Loop");
		buttonMusic2.setOnMouseEntered(e ->{
			if(!boolMusicItem2){
				buttonMusic2.setText("This song costs " + costMusicItem2 + " coins");
				buttonMusic2.setTranslateX(550);
				buttonMusic1.setTranslateX(400);
				buttonMusic3.setTranslateX(780);
			}

			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(hoverSound);
				soundEffect.play();
			}
		});
		buttonMusic2.setOnMouseExited(e ->{
			buttonMusic2.setText("Arcade Loop");
			buttonMusic2.setTranslateX(600);
			buttonMusic1.setTranslateX(450);
			buttonMusic3.setTranslateX(750);
		});
		buttonMusic2.setOnAction(e ->{
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(selectSound);
				soundEffect.play();
			}
			if(boolMusicItem2) {
				bgMusic.stop();
				backgroundMusic = "GameFiles/src/res/sound/A Trillion Stars.wav";
				bgMusic.setFile(backgroundMusic);
				buttonSettingsMusic.setText(bgMusicTextOff);
				musicMute = false;
				bgMusic.play();
				bgMusic.loop();
			}else if(coins >= costMusicItem2){
				coins -= costMusicItem2;
				scoreText.setText("Coins: " + coins);
				boolMusicItem2 = true;
				buttonMusic2.setText("Arcade Loop");
				bgMusic.stop();
				backgroundMusic = "GameFiles/src/res/sound/A Trillion Stars.wav";
				bgMusic.setFile(backgroundMusic);
				buttonSettingsMusic.setText(bgMusicTextOff);
				musicMute = false;
				bgMusic.play();
				bgMusic.loop();
				buttonMusic1.setTranslateX(450);
				buttonMusic2.setTranslateX(600);
				buttonMusic3.setTranslateX(750);
			}else if(coins < costMusicItem2){
				buttonMusic2.setText("You don't have enough coins");
			}
		});
		buttonMusic2.setTranslateX(600);
		buttonMusic2.setTranslateY(-37);

		

		buttonMusic3.setText("Medieval Arcade");
		buttonMusic3.setOnMouseEntered(e ->{
			if(!boolMusicItem3){
				buttonMusic3.setText("This song costs " + costMusicItem3 + " coins");
			}

			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(hoverSound);
				soundEffect.play();
			}
		});
		buttonMusic3.setOnMouseExited(e ->{
			buttonMusic3.setText("Medieval Arcade");
		});
		buttonMusic3.setOnAction(e ->{
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(selectSound);
				soundEffect.play();
			}
			if(boolMusicItem3){
				bgMusic.stop();
				backgroundMusic = "GameFiles/src/res/sound/Cosmic Love.wav";
				bgMusic.setFile(backgroundMusic);
				buttonSettingsMusic.setText(bgMusicTextOff);
				musicMute = false;
				bgMusic.play();
				bgMusic.loop();
			}else if(coins > costMusicItem3){
				coins -= costMusicItem3;
				scoreText.setText("Coins: " + coins);
				boolMusicItem3 = true;
				buttonMusic3.setText("Medieval Arcade");
				bgMusic.stop();
				backgroundMusic = "GameFiles/src/res/sound/Cosmic Love.wav";
				bgMusic.setFile(backgroundMusic);
				buttonSettingsMusic.setText(bgMusicTextOff);
				musicMute = false;
				bgMusic.play();
				bgMusic.loop();

			}else if(coins < costMusicItem3){
				buttonMusic3.setText("You don't have enough coins");
			}
		});
		buttonMusic3.setTranslateX(750);
		buttonMusic3.setTranslateY(-94);


		Button buttonChangeCharacterRobot = new Button();
		Button buttonChangeCharacterFemale = new Button();
		Button buttonChangeCharacterMale = new Button();



		if(characterName.equals(characterNameRobot)){
			buttonChangeCharacterRobot.setText("Robot Skin EQUIPPED");
			buttonChangeCharacterRobot.setTranslateX(365);
			buttonChangeCharacterMale.setTranslateX(765);
		}
		else{
			buttonChangeCharacterRobot.setText("Robot Skin");
			buttonChangeCharacterRobot.setTranslateX(430);
		}

		buttonChangeCharacterRobot.setOnAction(e ->{
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(selectSound);
				soundEffect.play();
			}
			characterName = characterNameRobot;
			buttonChangeCharacterRobot.setText("Robot Skin EQUIPPED");
			buttonChangeCharacterFemale.setText("Female Adventurer Skin");
			buttonChangeCharacterMale.setText("Male Adventurer Skin");
			buttonChangeCharacterRobot.setTranslateX(365);
			buttonChangeCharacterFemale.setTranslateX(555);
			buttonChangeCharacterMale.setTranslateX(758);
		});
		buttonChangeCharacterRobot.setOnMouseEntered(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(hoverSound);
				soundEffect.play();
			}
		});
		buttonChangeCharacterRobot.setTranslateY(-101);




		//Button created above the Robot's skin button so they can de-equip each other
		if(characterName.equals("Female Adventurer")){
			buttonChangeCharacterFemale.setText("Female Adventurer Skin EQUIPPED");
			buttonChangeCharacterRobot.setTranslateX(410);
			buttonChangeCharacterFemale.setTranslateX(515);
			buttonChangeCharacterMale.setTranslateX(800);
		}
		else{
			buttonChangeCharacterFemale.setText("Female Adventurer Skin");
			buttonChangeCharacterFemale.setTranslateX(555);
			if(characterName.equals(characterNameRobot))
				buttonChangeCharacterRobot.setTranslateX(370);
			else
				buttonChangeCharacterRobot.setTranslateX(434);
		}
		buttonChangeCharacterFemale.setOnMouseEntered(e ->{
			if(!characterOwnedFemaleAdventurer){
				buttonChangeCharacterFemale.setText("This skin costs " + femaleAdventurerCost + " coins");
				if(characterName.equals("Robot")){
					buttonChangeCharacterRobot.setTranslateX(365);
				}else{
					buttonChangeCharacterRobot.setTranslateX(430);
				}

				buttonChangeCharacterFemale.setTranslateX(552);
				buttonChangeCharacterMale.setTranslateX(760);
			}
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(hoverSound);
				soundEffect.play();
			}
		});
		buttonChangeCharacterFemale.setOnMouseExited(e ->{
			if(characterName.equals("Female Adventurer")){
				buttonChangeCharacterFemale.setText("Female Adventurer Skin EQUIPPED");
			}
			else{
				buttonChangeCharacterFemale.setText("Female Adventurer Skin");
				if(characterName.equals(characterNameRobot)){
					buttonChangeCharacterRobot.setTranslateX(370);
				}else{
					buttonChangeCharacterRobot.setTranslateX(434);
				}
				buttonChangeCharacterFemale.setTranslateX(555);
				buttonChangeCharacterMale.setTranslateX(758);


			}
		});
		buttonChangeCharacterFemale.setOnAction(e ->{
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(selectSound);
				soundEffect.play();
			}
			if(characterOwnedFemaleAdventurer){
				characterName = "Female Adventurer";
				buttonChangeCharacterFemale.setText("Female Adventurer Skin EQUIPPED");
				buttonChangeCharacterRobot.setText("Robot Skin");
				buttonChangeCharacterMale.setText("Male Adventurer Skin");
				buttonChangeCharacterRobot.setTranslateX(410);
				buttonChangeCharacterFemale.setTranslateX(515);
				buttonChangeCharacterMale.setTranslateX(800);
			}else if(coins > femaleAdventurerCost){
				coins -= femaleAdventurerCost;
				scoreText.setText("Coins: " + coins);
				characterOwnedFemaleAdventurer = true;
				buttonChangeCharacterFemale.setText("Female Adventurer Skin EQUIPPED");
				buttonChangeCharacterRobot.setText("Robot Skin");
				buttonChangeCharacterMale.setText("Male Adventurer Skin");
				characterName = "Female Adventurer";
				buttonChangeCharacterRobot.setTranslateX(410);
				buttonChangeCharacterFemale.setTranslateX(515);
				buttonChangeCharacterMale.setTranslateX(800);
			}else if(coins < femaleAdventurerCost){
				buttonChangeCharacterFemale.setText("You don't have enough coins");
				if(characterName.equals(characterNameRobot)){
					buttonChangeCharacterRobot.setTranslateX(350);
				}else{
					buttonChangeCharacterRobot.setTranslateX(420);
				}
				buttonChangeCharacterFemale.setTranslateX(540);
				buttonChangeCharacterMale.setTranslateX(780);
			}
		});
		buttonChangeCharacterFemale.setTranslateY(-46);




		//Button created above the Robot's skin button so they can de-equip each other
		if(characterName.equals(characterNameMaleAdventurer)){
			buttonChangeCharacterMale.setText("Male Adventurer Skin EQUIPPED");
			buttonChangeCharacterMale.setTranslateX(758);
		}
		else{
			buttonChangeCharacterMale.setText("Male Adventurer Skin");
			if(characterName.equals(characterNameRobot))
				buttonChangeCharacterMale.setTranslateX(758);
		}
		buttonChangeCharacterMale.setOnMouseEntered(e ->{
			if(!characterOwnedMaleAdventurer){		
				buttonChangeCharacterMale.setText("This skin costs " + maleAdventurerCost + " coins");
			}
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(hoverSound);
				soundEffect.play();
			}
		});
		buttonChangeCharacterMale.setOnMouseExited(e ->{
			if(characterName.equals(characterNameMaleAdventurer)){
				buttonChangeCharacterMale.setText("Male Adventurer Skin EQUIPPED");
			}
			else{
				buttonChangeCharacterMale.setText("Male Adventurer Skin");
			}
		});
		buttonChangeCharacterMale.setOnAction(e ->{
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(selectSound);
				soundEffect.play();
			}
			if(characterOwnedMaleAdventurer){
				characterName = characterNameMaleAdventurer;
				buttonChangeCharacterMale.setText("Male Adventurer Skin EQUIPPED");
				buttonChangeCharacterRobot.setText("Robot Skin");
				buttonChangeCharacterFemale.setText("Female Adventurer Skin");
				buttonChangeCharacterMale.setTranslateX(765);
				buttonChangeCharacterRobot.setTranslateX(434);
				buttonChangeCharacterFemale.setTranslateX(555);
			}else if(coins > maleAdventurerCost){
				coins -= maleAdventurerCost;
				scoreText.setText("Coins: " + coins);
				characterOwnedMaleAdventurer = true;
				buttonChangeCharacterMale.setText("Male Adventurer Skin EQUIPPED");
				buttonChangeCharacterRobot.setText("Robot Skin");
				buttonChangeCharacterFemale.setText("Female Adventurer Skin");
				characterName = characterNameMaleAdventurer;
				buttonChangeCharacterMale.setTranslateX(765);
				buttonChangeCharacterRobot.setTranslateX(434);
				buttonChangeCharacterFemale.setTranslateX(555);
			}else if(coins < maleAdventurerCost){
				buttonChangeCharacterMale.setText("You don't have enough coins");
			
			}
		});
		buttonChangeCharacterMale.setTranslateY(10);


	    Text mainName = new Text("Robot Runner");
		mainName.setId("maintitle");
		mainName.applyCss();
		mainName.setFont(Font.font ("Courier New", 24));
		

		VBox layout1 = new VBox(20);
		layout1.getChildren().addAll(mainName, button1, buttonMultiplayer, button3, button4, button5);
		layout1.setVisible(true);
		scene1 = new Scene(layout1, 1280, 720);
		scene1.getStylesheets().add(getClass().getResource("DemoStyle.css").toExternalForm());
		mainMenuScene = scene1;
		layout1.setAlignment(Pos.CENTER);


		//Shop Layout
		Label labelShop = new Label("The Shop");
		Label labelMusicItems = new Label("Soundtracks:");
		Label labelSkins = new Label("Character Skins:");
		labelShop.setMaxWidth(Double.MAX_VALUE);
		labelShop.setAlignment(Pos.TOP_CENTER);
		labelMusicItems.setMaxWidth(Double.MAX_VALUE);
		labelMusicItems.setAlignment(Pos.TOP_CENTER);
		labelSkins.setMaxWidth(Double.MAX_VALUE);
		labelSkins.setAlignment(Pos.TOP_CENTER);
		VBox layout2 = new VBox(20);
		VBox skinsLayout = new VBox(20);
		VBox musicLayout = new VBox(20);
		skinsLayout.getChildren().addAll(labelSkins, buttonChangeCharacterMale, buttonChangeCharacterFemale, buttonChangeCharacterRobot);
		musicLayout.getChildren().addAll(labelMusicItems ,buttonMusic1, buttonMusic2, buttonMusic3);
		layout2.getChildren().addAll(labelShop , scoreText, musicLayout, skinsLayout, buttonShopBack);
		scene2 = new Scene(layout2,1280, 720);
		scene2.getStylesheets().add(getClass().getResource("DemoStyle.css").toExternalForm());
		
		
		// Settings Layout
		Label labelSoundSettings = new Label("Sound Settings");
		Label labelControls = new Label("  Controls  ");
		labelSoundSettings.setMaxWidth(Double.MAX_VALUE);
		labelSoundSettings.setAlignment(Pos.TOP_CENTER);
	    labelControls.setMaxWidth(Double.MAX_VALUE);
	    labelControls.setTranslateY(45);
	    labelControls.setAlignment(Pos.CENTER);
	    labelControls.setFont(Font.font(null,20));
		VBox layout3 = new VBox(20);
		layout3.getChildren().addAll(labelSoundSettings, buttonSettingsMusic, buttonSettingsSounds, labelControls, tForward, tBackwards, tJump, buttonSettingsBack);
		//layout3.getChildren().addAll(label3,buttonSettingsBack);
		layout3.setAlignment(Pos.CENTER);
		scene3 = new Scene(layout3, 1280, 720);
		scene3.getStylesheets().add(getClass().getResource("DemoStyle.css").toExternalForm());

		
		//Lobby Layout
		VBox layout5 = new VBox(20);
		Label lobbyLabel =  new Label("Waiting for players ... ");
		lobbyLabel.setFont(Font.font(null,30));
		lobbyLabel.setMaxWidth(Double.MAX_VALUE);
	    lobbyLabel.setAlignment(Pos.CENTER);
	    layout5.getChildren().addAll(lobbyLabel, buttonLobbyBack);
		scene5 = new Scene(layout5,1280, 720);
		scene5.getStylesheets().add(getClass().getResource("DemoStyle.css").toExternalForm());
		
		window.setScene(scene1);
		window.show();

               	
	}


	//method called when Exit Button is pressed
	public void closeGame() {
		writeSaveFile();
		bgMusic.stop();
		window.close();
	}
		
	public void PauseGame(Stage primaryStage, Scene GameRoot, HumanPlayer player) {
		pause = true;

		String newText = scoreText.getText();

		Text scoreTextPause = new Text();
		scoreTextPause.setFill(Color.WHITE);
		scoreTextPause.setText(newText);
		scoreTextPause.setTranslateX(970);
		scoreTextPause.setTranslateY(-40);



		Button buttonPauseResume = new Button();
		buttonPauseResume.setText("      Resume      ");
		buttonPauseResume.setOnAction(event -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(selectSound);
				soundEffect.play();
			}
			primaryStage.setMinHeight(759);
			primaryStage.setMinWidth(1296);

			primaryStage.setMaxHeight(759);
			primaryStage.setMaxWidth(1296);
			demo.getStage().setScene(GameRoot);
			demo.getStage().show();
			pause = false;

		});
		buttonPauseResume.setOnMouseEntered(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(hoverSound);
				soundEffect.play();
			}
		});


		Button buttonPauseBack = new Button();
		buttonPauseBack.setText("   Back To Main Menu  ");// lobby back button
		buttonPauseBack.setOnAction(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(selectSound);
				soundEffect.play();
			}
			try {
				writeSaveFile();
				pause = true;
				singlePlayerCharacter = player;
				primaryStage.setScene(mainMenuScene);

			} catch (Exception e1) {

				e1.printStackTrace();
			}
		});
		buttonPauseBack.setOnMouseEntered(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(hoverSound);
				soundEffect.play();
			}
		});


  
		Button buttonPauseExit = new Button();
		buttonPauseExit.setText("     Exit Game     ");
		buttonPauseExit.setOnAction(e ->{
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(selectSound);
				soundEffect.play();
			}
			bgMusic.stop();
			writeSaveFile();
			demo.getStage().close();
		});
		buttonPauseExit.setOnMouseEntered(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(hoverSound);
				soundEffect.play();
			}
		});

    		
		Label label4 = new Label("Paused");//
		label4.setMaxWidth(Double.MAX_VALUE);
		label4.setAlignment(Pos.TOP_CENTER);
		VBox layout4 = new VBox(20);
		layout4.getChildren().addAll(label4, buttonPauseResume, buttonPauseBack, buttonPauseExit, scoreTextPause);
		layout4.setAlignment(Pos.CENTER);
		scene4 = new Scene(layout4, 1280, 720);
		scene4.getStylesheets().add(getClass().getResource("DemoStyle.css").toExternalForm());
		
		demo.getStage().setScene(scene4);
	}

	/**
	 * A function that sets the scene after completing a level.
	 * @param primaryStage - The stage.
	 * @param GameRoot - The root of the game.
	 * @param player - A player of type Human.
	 * @param winner - A string with the name of the winning player.
	 */

	public void gameOver(Stage primaryStage, Scene GameRoot, HumanPlayer player, String winner) {
		pause = true;

		primaryStage.setMinHeight(759);
		primaryStage.setMinWidth(1296);

		primaryStage.setMaxHeight(759);
		primaryStage.setMaxWidth(1296);

		String newText = scoreText.getText();

		Text scoreTextPause = new Text();
		scoreTextPause.setFill(Color.WHITE);
		scoreTextPause.setText(newText);
		scoreTextPause.setTranslateX(970);
		scoreTextPause.setTranslateY(-40);


		Button buttonNext = new Button();
		buttonNext.setText("      Next Game      ");
		buttonNext.setOnAction(event -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(selectSound);
				soundEffect.play();
			}

			pause = false;
			singlePlayerCharacter = player;
			reloadGame(primaryStage);

		});
		buttonNext.setOnMouseEntered(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(hoverSound);
				soundEffect.play();
			}
		});



		Button buttonPauseBack = new Button();
		buttonPauseBack.setText("   Back To Main Menu  ");// lobby back button
		buttonPauseBack.setOnAction(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(selectSound);
				soundEffect.play();
			}
			try {

				scoreText.setText("Coins: " + coins);
				writeSaveFile();
				singlePlayerCharacter = player;
				reloadGame(primaryStage);
				pause = true;
				primaryStage.setScene(mainMenuScene);

			} catch (Exception e1) {

				e1.printStackTrace();
			}
		});
		buttonPauseBack.setOnMouseEntered(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(hoverSound);
				soundEffect.play();
			}
		});



		Button buttonExit = new Button();
		buttonExit.setText("     Exit Game     ");
		buttonExit.setOnAction(e ->{
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(selectSound);
				soundEffect.play();
			}
			bgMusic.stop();
			writeSaveFile();
			demo.getStage().close();
		});
		buttonExit.setOnMouseEntered(e -> {
			if(GameMenu.soundEffectMute == false) {
				soundEffect.setFile(hoverSound);
				soundEffect.play();
			}
		});


		Label labelWinner = new Label(winner + " Has won the round!");//
		labelWinner.setMaxWidth(Double.MAX_VALUE);
		labelWinner.setAlignment(Pos.TOP_CENTER);
		VBox layoutOver = new VBox(20);
		layoutOver.getChildren().addAll(labelWinner, buttonNext, buttonPauseBack, buttonExit, scoreTextPause);
		layoutOver.setAlignment(Pos.CENTER);
		sceneOver = new Scene(layoutOver, 1280, 720);
		sceneOver.getStylesheets().add(getClass().getResource("DemoStyle.css").toExternalForm());

		demo.getStage().setScene(sceneOver);
	}

	
}