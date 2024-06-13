package stages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class WelcomeStage {
	public static final int WINDOW_HEIGHT = 800;
	public static final int WINDOW_WIDTH = 800;
	public static final int MAP_HEIGHT = 2400;
	public static final int MAP_WIDTH = 2400;
	private Scene scene;
	private Stage stage;
	private Group root;
	private Canvas canvas;
	private GraphicsContext gc;
	private Image background;

	public WelcomeStage() {
		this.root = new Group();
		this.scene = new Scene(root, WelcomeStage.WINDOW_WIDTH,WelcomeStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(WelcomeStage.WINDOW_WIDTH,WelcomeStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();

		this.background = new Image("images/menuBackground.png",WelcomeStage.WINDOW_WIDTH,WelcomeStage.WINDOW_WIDTH,false,false);
	}

	public void setStage(Stage stage) {
		this.stage = stage;
		this.stage.setResizable(false);

		gc.drawImage(this.background, 0, 0);

		Image icon = new Image("images/Icon.png");
		this.stage.getIcons().add(icon);

		// Create VBox for the series of buttons
		VBox vbox = new VBox(10);
		vbox.setAlignment(Pos.CENTER);

		// Create contents for vbox
		Button newGame = new Button("New Game");
		Button instructions = new Button("How to Play");
		Button about = new Button("About us");
//		Button showGameOver = new Button("Game Over");

		Text text = new Text("Feeding Frenzy");
		Font theFont = Font.font("Bernard MT Condensed",FontWeight.BOLD,30);
		text.setFont(theFont);
		text.setFill(Color.WHITE);

		vbox.getChildren().addAll(text, newGame, instructions, about);
		// Create StackPane to apply both canvas and the VBox
		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(canvas, vbox);


		this.root.getChildren().add(stackPane); // canvas to root
		this.stage.setTitle("Feeding Frenzy");
		this.stage.setScene(this.scene);
		this.stage.show();

		newGame.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				GameStage gameStage = new GameStage(stage);
				stage.setScene(gameStage.getScene());
			}

		});

		instructions.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				InstructionsStage instructionsStage = new InstructionsStage(stage);
				stage.setScene(instructionsStage.getScene());
			}

		});

		about.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				AboutUsStage aboutUsStage = new AboutUsStage(stage);
				stage.setScene(aboutUsStage.getScene());
			}
		});



//		showGameOver.setOnAction(new EventHandler<ActionEvent>(){
//		@Override
//		public void handle(ActionEvent e){
//			GameOverStage gameOverStage = new GameOverStage(stage, 1,1,1,1);
//			stage.setScene(gameOverStage.getScene());
//		}
//
//	});

	}

}
