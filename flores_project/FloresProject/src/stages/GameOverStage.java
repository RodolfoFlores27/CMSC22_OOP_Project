package stages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameOverStage {
	public static final int WINDOW_HEIGHT = 800;
	public static final int WINDOW_WIDTH = 800;
	public static final int MAP_HEIGHT = 2400;
	public static final int MAP_WIDTH = 2400;
	private Scene scene;
	private Group root;
	private Canvas canvas;
	private GraphicsContext gc;
	private Image background;
	private Stage stage;

	public GameOverStage(Stage stage, int foodScore, int fishScore, double finalSize, int timeAlive) {
		this.stage = stage;
		this.root = new Group();
		this.scene = new Scene(root, WelcomeStage.WINDOW_WIDTH,WelcomeStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(WelcomeStage.WINDOW_WIDTH,WelcomeStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.background = new Image("images/menuBackground.png",WelcomeStage.WINDOW_WIDTH,WelcomeStage.WINDOW_WIDTH,false,false);
		this.gc.drawImage(this.background, 0, 0, WINDOW_HEIGHT, WINDOW_WIDTH);

		// Create VBox for the series of buttons
		Button backButton = new Button("Go Back");
		backButton.setLayoutX(10);
		backButton.setLayoutY(10);

		backButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				WelcomeStage newWelcomeStage = new WelcomeStage();
				newWelcomeStage.setStage(stage);
			}
		});

		VBox vbox = new VBox(10);
		vbox.setAlignment(Pos.CENTER);


		Font theFont = Font.font("Aharoni",FontWeight.BOLD,30);
		Text gameOverText = new Text("GAME OVER");
		Text foodScoreText = new Text("Food consumed: "+foodScore);
		Text fishScoreText = new Text("Fish consumed: "+fishScore);
		Text finalSizeText = new Text("Final Size: "+finalSize);
		Text timeAliveText = new Text("Time Alive: "+timeAlive);
		//apply lists for these texts for easier implementation when there's too many

		gameOverText.setFont(Font.font("Times New Roman",FontWeight.BOLD,50));
		foodScoreText.setFont(theFont);
		fishScoreText.setFont(theFont);
		finalSizeText.setFont(theFont);
		timeAliveText.setFont(theFont);
		vbox.getChildren().addAll(gameOverText, foodScoreText, fishScoreText, finalSizeText, timeAliveText);

		// Create StackPane to apply both canvas and the VBox
		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(canvas, vbox);
		this.root.getChildren().addAll(stackPane, backButton); // canvas to root
	}

	public Scene getScene() {
		return this.scene;
	}
}



