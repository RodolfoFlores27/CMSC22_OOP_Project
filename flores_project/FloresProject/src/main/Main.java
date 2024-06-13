package main;

import javafx.application.Application;
import javafx.stage.Stage;
import stages.WelcomeStage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage){
		WelcomeStage welcomeStage = new WelcomeStage();
		welcomeStage.setStage(stage);
	}
}
