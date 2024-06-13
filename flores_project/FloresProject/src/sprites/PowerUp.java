package sprites;

import timers.PowerUpTimer;

public class PowerUp extends Sprite {
	public static final String SHIELD = "shield";
	public static final String SPEEDBOOST = "speedBoost";

	public static final int WIDTH = 40;
	public static final int HEIGHT = 40;

	public PowerUp(int x, int y, int width, int height, String type) {
		super(x, y, width, height, type);
		this.moving = false;
		this.isVisible = true;
	}

	public void affect(Fish fish) {
		// update values of fish
		if (this.type == "shield") {
			System.out.println("Fish is shielded.");
			//makes fish shielded
			fish.setType("ShieldedMainFish");
			fish.setShielded(true);
		}

		else if (this.type == "speedBoost") {
			System.out.println("Speedboost On!");
			//double speed
			fish.setSpeed(fish.getSpeed()*2);
		}

		//start timer for PowerUp duration
		PowerUpTimer timer = new PowerUpTimer(fish, this);
		timer.start();
	}

	public void checkCollision(Fish fish){
		if(this.collidesWith(fish)){
			this.alive = false;
		}
	}
}
