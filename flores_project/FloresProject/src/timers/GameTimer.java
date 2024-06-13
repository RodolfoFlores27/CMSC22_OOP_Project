package timers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sprites.Fish;
import sprites.PowerUp;
import stages.GameOverStage;
import stages.GameStage;

/*
 * The GameTimer is a subclass of the AnimationTimer class. It must override the handle method.
 */
public class GameTimer extends AnimationTimer{
	private Stage theStage;
	private Scene theScene;
	private GraphicsContext gc;
	private ImageView imgView;
	private StackPane statusBar;

	private Fish mainFish;
	private ArrayList<Fish> otherFish; // otherFishes besides mainFish
	private PowerUp powerUp;
	private Thread stopWatch; //used for counting gameDuration

	public static final int ENEMY_FISH_NUM = 10;
	public static final int FOOD_FISH_NUM = 50;
	public static final int POWERUP_NUM = 1;

	public GameTimer(Stage theStage, Scene theScene, ImageView background, GraphicsContext gc, StackPane statusBar){
		this.theStage = theStage;
		this.theScene = theScene;
		this.gc = gc;
		this.imgView = background;
		this.statusBar = statusBar;
		this.spawnSprites();
		this.setStopWatch();
		this.handleKeyPressEvent();
	}

	@Override
	public void handle(long currentNanoTime) {
		this.gc.clearRect(0, 0, GameStage.MAP_WIDTH,GameStage.MAP_HEIGHT);
		this.renderSprites(); // render sprites on canvas
		this.updateStatusBar();
		this.moveOtherFishes(); //makes otherFishes move randomly
		this.checkCollisions(); //checks collisions in between sprites

		// if mainFish dies, gameOver
		if(!this.mainFish.isAlive()) {
        	this.stop(); //stop GameTimer
        	// show gameOverStage
			GameOverStage gameOverStage = new GameOverStage(
					theStage,mainFish.getFoodFishEaten() ,mainFish.getEnemyFishEaten(),
					mainFish.getSize(),mainFish.getTimeAlive());
			theStage.setScene(gameOverStage.getScene());
		}
	}

	private void spawnSprites() {
		spawnMainFish(); //spawns in the middle of the map
		this.otherFish = new ArrayList<Fish>();
		spawnOtherFishes(otherFish); //spawns anywhere the map
		spawnPowerUp(); //spawns anywhere the map
	}

	private void spawnMainFish() {
		this.mainFish = new Fish(GameStage.MAP_WIDTH/2, GameStage.MAP_HEIGHT/2,
			Fish.MAIN_FISH_WIDTH, Fish.MAIN_FISH_HEIGHT , Fish.MAIN_FISH);
	}

	private void spawnOtherFishes(ArrayList<Fish> fishes) {
		spawnEnemyFish(fishes, ENEMY_FISH_NUM);
		spawnFoodFish(fishes, FOOD_FISH_NUM);
	}

	private void spawnEnemyFish(ArrayList<Fish> fishes, int numOfInstances) {
		Random r = new Random();
		for (int i = 0; i < numOfInstances; i++) {
			int x = r.nextInt(GameStage.MAP_WIDTH);
			int y = r.nextInt(GameStage.MAP_HEIGHT);
			fishes.add(new Fish(x, y, Fish.ENEMY_FISH_WIDTH, Fish.ENEMY_FISH_HEIGHT,
					Fish.ENEMY_FISH));
			System.out.println("An new enemy fish has spawned at x: "+x+" ; y: "+y);
		}
	}

	private void spawnFoodFish(ArrayList<Fish> fishes, int numOfInstances) {
		Random r = new Random();
		for (int i = 0; i < numOfInstances; i++) {
			int x = r.nextInt(GameStage.MAP_WIDTH);
			int y = r.nextInt(GameStage.MAP_HEIGHT);
			fishes.add(new Fish(x, y, Fish.FOOD_FISH_WIDTH, Fish.FOOD_FISH_HEIGHT,
					Fish.FOOD_FISH));
			System.out.println("A new food fish has spawned at x: "+x+"; y: "+y);
		}
	}

	protected void spawnPowerUp() {
		Random r = new Random();

		// makes sure that PowerUp spawns in visible playArea
		int x = r.nextInt(GameStage.WINDOW_WIDTH) + GameStage.MAP_WIDTH/2-GameStage.WINDOW_WIDTH/2;
		int y = r.nextInt(GameStage.WINDOW_HEIGHT) + GameStage.MAP_HEIGHT/2-GameStage.WINDOW_HEIGHT/2;

		// randomizer for type of PowerUp that will spawn
		int powerUpType = r.nextInt(2);

		// spawn PowerUp
		if (powerUpType == 0) this.powerUp = new PowerUp(x, y,
				PowerUp.WIDTH, PowerUp.HEIGHT, PowerUp.SPEEDBOOST);
		if (powerUpType == 1) this.powerUp = new PowerUp(x, y,
				PowerUp.WIDTH, PowerUp.HEIGHT, PowerUp.SHIELD);
		System.out.println("A new powerup has spawned at x: "+x+"; y: "+y);

		// timer for PowerUp duration in its place
		Timer timer = new Timer();
		TimerTask task = new TimerTask(){
			PowerUp powerUpInTask = powerUp;

			@Override
			public void run() {
				// if PowerUp is still not consumed, spawn another one
				if(powerUpInTask.isAlive())
					spawnPowerUp();
			}
		};
		// do task every after 10 seconds
		timer.schedule(task, 10000);
	}

	// method for setting stopWatch of gameTimer
	private void setStopWatch() {
		Thread stopWatch = new Thread() {
			Fish mainFishHere = mainFish;
			@Override
			public void run() {
				System.out.println("Timer started.");
				while(mainFishHere.isAlive()){
					try{
						// add 1 per second
						Thread.sleep(1000);
						mainFishHere.setTimeAlive(mainFishHere.getTimeAlive()+1);
						System.out.println("MainFish speed: "+mainFish.getSpeed());
					} catch(InterruptedException e){
						System.out.println(e.getMessage());
					}
				}
			}
		};
		this.stopWatch = stopWatch;
		this.stopWatch.start();
	}

	private void setFishVisibility() {
		for (Fish fish : otherFish){
			// if inside playArea
			if (fish.getX() >= mainFish.getX()-GameStage.WINDOW_WIDTH
					&& fish.getX() <= mainFish.getX()+GameStage.WINDOW_WIDTH
					&& fish.getY() >= mainFish.getY()-GameStage.WINDOW_HEIGHT
					&& fish.getY() <= mainFish.getY()+GameStage.WINDOW_HEIGHT) {
				fish.setVisible(true);
			} else {
				fish.setVisible(false);
			}
		}
	}

	//method that will render/draw the fishes to the canvas
	private void renderSprites() {
		// sets which fishes get to be rendered
		setFishVisibility();

		mainFish.render(gc); // draw mainFish
		for (Fish fish : otherFish){ // draw otherFish
			if (fish.getVisible())
				fish.render(gc);

		}
		try { // draw PowerUp
			powerUp.render(gc);
		} catch(Exception e) {}
	}


	private void updateStatusBar() {
		this.statusBar.getChildren().remove(0); // removes gridPane child of statusBar

		GridPane grid = new GridPane(); // makes a new gridPane

		Text foodEaten = new Text();
		Text enemyEaten = new Text();
		Text mainFishSize = new Text();
		Text timeAlive = new Text();

		// set Text content
		foodEaten.setText("Food Eaten: "+mainFish.getFoodFishEaten());
		enemyEaten.setText("Enemies Eaten: "+mainFish.getEnemyFishEaten());
		mainFishSize.setText("Size: "+mainFish.getSize());
		timeAlive.setText("Time Alive: "+mainFish.getTimeAlive());

		// set Font
		Font theFont = Font.font("Aharoni",FontWeight.NORMAL,15);
		ArrayList<Text> texts = new ArrayList<Text>();
		Collections.addAll(texts, foodEaten, enemyEaten, mainFishSize, timeAlive);
		for (Text text : texts) {
			text.setFont(theFont);
		}

		// add Texts on GridPane
		grid.add(foodEaten, 0, 0);
		grid.add(mainFishSize, 0, 1);
		grid.add(enemyEaten, 1, 0);
		grid.add(timeAlive, 1, 1);

		// adjust gridPane values
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(35);
		grid.setVgap(5);
		grid.setPadding(new Insets(10,0,0,0));
		grid.setPrefHeight(40);

		statusBar.getChildren().add(grid); // add grid to statusBar
	}

	//method that will listen and handle the key press events
	private void handleKeyPressEvent() {
		theScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
            	KeyCode code = e.getCode();
                moveMainFish(code);
			}
		});

		theScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
            public void handle(KeyEvent e){
            	KeyCode code = e.getCode();
                stopMainFish(code);
            }
        });
    }

	//method that will move mainFish depending on the key pressed
	private void moveMainFish(KeyCode ke) {
		if(ke==KeyCode.W) {
			//checks if next translate can still fit the map on the window.
			if(!(imgView.getTranslateY()+mainFish.getSpeed() >=
					GameStage.MAP_HEIGHT/2-GameStage.WINDOW_HEIGHT/2)) {
				// set mainFish move speed at y axis
				mainFish.setDy(mainFish.getSpeed());

				// update positions of sprite to give illusion that fish is moving
				updateSpritesPosition(0, mainFish.getSpeed());

				// moving map background gives illusion that fish is moving
				moveBackground(imgView, mainFish);
			}
		}

		if(ke==KeyCode.A) {
			if(!(imgView.getTranslateX()+mainFish.getSpeed() >=
					GameStage.MAP_HEIGHT/2-GameStage.WINDOW_HEIGHT/2)) {
				mainFish.setDx(mainFish.getSpeed());
				updateSpritesPosition(mainFish.getSpeed(), 0);
				moveBackground(imgView, mainFish);
			}
		}

		if(ke==KeyCode.S) {
			if(!(imgView.getTranslateY()-mainFish.getSpeed() <=
					-(GameStage.MAP_HEIGHT/2-GameStage.WINDOW_HEIGHT/2))) {
				mainFish.setDy(-mainFish.getSpeed());
				updateSpritesPosition(0, -mainFish.getSpeed());
				moveBackground(imgView, mainFish);
			}
		}

		if(ke==KeyCode.D) {
			if(!(imgView.getTranslateX()-mainFish.getSpeed() <=
					-(GameStage.MAP_HEIGHT/2-GameStage.WINDOW_HEIGHT/2))) {
				mainFish.setDx(-mainFish.getSpeed());
				updateSpritesPosition(-mainFish.getSpeed(), 0);
				moveBackground(imgView, mainFish);
			}
		}
			System.out.println(ke+" key pressed.");
			System.out.println("Background TranslateX: "+this.imgView.getTranslateX());
			System.out.println("Background TranslateY: "+this.imgView.getTranslateY());
	}

	// Method that stops the mainFish from moving
	private void stopMainFish(KeyCode ke){
		if(ke==KeyCode.A || ke==KeyCode.D) {
			this.mainFish.setDx(0); // reset moveSpeed for x axis
		}

		if(ke==KeyCode.W || ke==KeyCode.S) {
			this.mainFish.setDy(0); // reset moveSpeed for y axis
		}
	}
	// Background movement that simulates the movement of the fish
	private void moveBackground(ImageView background, Fish fish) {
		// move background based on fish speed
		this.imgView.setTranslateY(this.imgView.getTranslateY()+fish.getDy());
		this.imgView.setTranslateX(this.imgView.getTranslateX()+fish.getDx());
	}

	// Updates sprites positions to give illusion that fish is moving
	private void updateSpritesPosition(double dx, double dy) {
		for (int i=0; i < otherFish.size(); i++) {
			if (otherFish.get(i).isInBounds(GameStage.MAP_WIDTH, GameStage.MAP_HEIGHT)) {
				otherFish.get(i).setX(otherFish.get(i).getX() + dx);
				otherFish.get(i).setY(otherFish.get(i).getY() + dy);
			}
		}

		try {
			if (powerUp.isInBounds(GameStage.MAP_WIDTH, GameStage.MAP_HEIGHT)) {
				powerUp.setX(powerUp.getX() + dx);
				powerUp.setY(powerUp.getY() + dy);
			}
		} catch (Exception e) {}
	}

	// sets otherFishes to move at a random direction for a random amount of time
	private void moveOtherFishes() {
		for (int i=0; i < otherFish.size(); i++) {
			// if fish is not moving, make it move
			if (!otherFish.get(i).isMoving()) {
				otherFish.get(i).setMovement();
			// if mapBounds is reached, fish stops moving
			} else if (!(otherFish.get(i).isInBounds(GameStage.MAP_WIDTH, GameStage.MAP_HEIGHT))) {
				otherFish.get(i).setMoving(false); // cancel move instruction if border is reached.
				otherFish.get(i).getTimer().cancel();
				otherFish.get(i).setMovement(); // issue new movement instruction
			} else {
				// move fish (not mainFish)
				otherFish.get(i).move();
			}
		}
	}

	// Method that checks collisions in between sprites
	private void checkCollisions() {

		//check collision of fishes
		otherFish.add(mainFish); // temporarily add mainFish to list of fishes
		for(int i = 0; i < otherFish.size(); i++){
			for (int j = 0; j < otherFish.size(); j++) {
				// if will be compared to self
				if (otherFish.get(i) == otherFish.get(j)) {
					continue;
				}

				otherFish.get(i).checkCollision(otherFish.get(j));

				// if fish died from collision
				if (!otherFish.get(i).isAlive()) {
					otherFish.get(j).consume(otherFish.get(i));
					if (otherFish.get(i).getType() == Fish.MAIN_FISH) {
						System.out.println("MainFish new speed: "+mainFish.getSpeed());
					}

					if (otherFish.get(i).getType() == Fish.FOOD_FISH) {
						// respawn a new food fish on the map.
						spawnFoodFish(otherFish, 1);
					}
					otherFish.remove(otherFish.get(i));
					break; // check next fish
				}
			}
		}
		otherFish.remove(mainFish); //return otherFish list back to normal

		//check collision for powerUp
		try {
			powerUp.checkCollision(mainFish);
			// powerUp got consumed
			if (!powerUp.isAlive()) {
				powerUp.affect(mainFish);
				powerUp = null; // removes powerUp sprite in the game

				//start timer for powerUp respawn
				Timer timer = new Timer();
				TimerTask task = new TimerTask(){
					@Override
					public void run() {
						System.out.println("Ups will Respawn.");
						spawnPowerUp();
					}
				};
				// wait 10 seconds before another PowerUp respawns
				timer.schedule(task, 10000);
			}
		} catch (Exception e) {}
	}

}
