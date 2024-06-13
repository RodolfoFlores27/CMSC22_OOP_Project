package sprites;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import stages.GameStage;

public class Fish extends Sprite {
	private boolean shielded;
	private int foodFishEaten;
	private int enemyFishEaten;
	private int timeAlive;
	private Timer timer;

	// types
	public static final String MAIN_FISH = "MainFish";
	public static final String ENEMY_FISH = "EnemyFish";
	public static final String FOOD_FISH = "SmallFish";

	// widths
	public static final int MAIN_FISH_WIDTH = 40;
	public static final int ENEMY_FISH_WIDTH = 40;
	public static final int FOOD_FISH_WIDTH = 20;

	// heights
	public static final int MAIN_FISH_HEIGHT = 40;
	public static final int ENEMY_FISH_HEIGHT = 40;
	public static final int FOOD_FISH_HEIGHT = 20;

	public Fish(int x, int y, int width, int height, String type) {
		super(x, y, width, height, type);
		this.shielded = false;
		this.moving = false; //indicator if fish should move
		this.isVisible = true;
	}

	public void move() {
		// can only move if this is still in game Area
		if(this.isInBounds(GameStage.MAP_WIDTH, GameStage.MAP_HEIGHT)) {
			this.x += this.dx;
			this.y += this.dy;
		}
	}

	public void setMovement() {
		this.setMoving(true); // set the fish moving

		// constants for movement direction
		final int LEFT = 0;
		final int RIGHT = 1;
		final int UP = 2;
		final int DOWN = 3;

		Random r = new Random();
		int direction = r.nextInt(4);

		// set this directional movement based on direction
        if (direction == LEFT) this.setDx(this.speed);
        if (direction == RIGHT) this.setDx(-this.speed);
        if (direction == DOWN) this.setDy(this.speed);
        if (direction == UP) this.setDy(-this.speed);

		int seconds = r.nextInt(4) + 1;
        // Converting seconds to milliseconds
        long moveDuration = TimeUnit.MILLISECONDS.convert(seconds, TimeUnit.SECONDS);

        Fish fish = this; // reference to this
		// Apply timer for movement
        fish.timer = new Timer();
		TimerTask task = new TimerTask(){
			Fish fishInTask = fish;

			@Override
			public void run() {
				// fish stops after moveDuration
				fishInTask.setMoving(false);
				fishInTask.setDx(0);
				fishInTask.setDy(0);
			}
		};
		fish.timer.schedule(task, moveDuration); // do task after moveDuration
	}

	public void consume(Fish fish) {
		// sideEffects based on fish type
		if (fish.type == "SmallFish") {
			this.setFoodFishEaten(this.foodFishEaten+1);
		} else if (fish.type == "EnemyFish") {
			this.setEnemyFishEaten(this.enemyFishEaten+1);
		}
		// increase in size relative to consumed fish size
		this.setSize((this.getSize()+fish.getWidth()*.2), (this.getSize()+fish.getHeight()*.2));
		this.setSpeed(120/this.getSize());
	}

	public void checkCollision(Fish fish) {
		if(this.collidesWith(fish) && this.size<fish.size && !this.isShielded()){
			this.alive = false;
		}
	}

	// getters
	public boolean isShielded() {
		return this.shielded;
	}

	public Timer getTimer() {
		return this.timer;
	}

	public int getFoodFishEaten() {
		return foodFishEaten;
	}

	public int getEnemyFishEaten() {
		return enemyFishEaten;
	}

	public int getTimeAlive() {
		return timeAlive;
	}

	public void setShielded(boolean val){
		this.shielded = val;
	}

	public void setFoodFishEaten(int val) {
		this.foodFishEaten = val;
	}

	public void setEnemyFishEaten(int val) {
		this.enemyFishEaten = val;
	}

	public void setTimeAlive(int val) {
		this.timeAlive = val;
	}
}
