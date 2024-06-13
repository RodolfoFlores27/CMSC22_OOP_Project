package timers;

import sprites.Fish;
import sprites.PowerUp;

public class PowerUpTimer extends Thread {
	private int timeLeft;
	private Fish mainFish;
	private PowerUp powerUp;

	private final static int UPGRADE_TIME = 5;

	public PowerUpTimer(Fish fish, PowerUp powerUp) {
		this.mainFish = fish;
		this.timeLeft = PowerUpTimer.UPGRADE_TIME;
		this.powerUp = powerUp;
	}

	public void run() {
		this.countDown();
	}

	private void countDown(){
		while(this.timeLeft!=0){
			try{
				Thread.sleep(1000);
				this.timeLeft--;
				System.out.println("Time left for powerUp: " + this.timeLeft);
			}catch(InterruptedException e){
				System.out.println(e.getMessage());
			}
		}

		// fish returns to normal
		System.out.println("Mainfish goes back to normal.");
		if (powerUp.getType() == PowerUp.SHIELD) {
			this.mainFish.setType(Fish.MAIN_FISH);
			this.mainFish.setShielded(false); // unshield fish
			System.out.println("MainFish Shielded: "+mainFish.isShielded());

		} else if (powerUp.getType() == PowerUp.SPEEDBOOST) {
			this.mainFish.setSpeed(120/this.mainFish.getSize()); //return to normal speed
		}
	}
}
