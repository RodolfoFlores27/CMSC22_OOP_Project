package stages;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import timers.GameTimer;

public class GameStage {
	public static final int WINDOW_HEIGHT = 800;
	public static final int WINDOW_WIDTH = 800;
	public static final int MAP_HEIGHT = 2400;
	public static final int MAP_WIDTH = 2400;
	private Scene scene;
	private Stage stage;
	private StackPane root;
	private GraphicsContext gc;
	private GameTimer gametimer;
	private ImageView background;
	private Canvas canvas;
	private StackPane statusBar;

	//the class constructor
	public GameStage(Stage stage) {
		this.root = new StackPane();
		this.scene = new Scene(root, WINDOW_WIDTH,WINDOW_HEIGHT);
		this.canvas = new Canvas(MAP_WIDTH,MAP_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.statusBar = new StackPane();

		//Image img = new Image("images/MainFish.png",50,50,true,false);
		//this.gc.drawImage(img, WINDOW_WIDTH/2-img.getWidth()/2, WINDOW_WIDTH/2-img.getHeight()/2);
		//instantiate an animation timer
		this.setBackground();
		this.setStatusBarBackground();
		this.gametimer = new GameTimer(stage, scene, background, gc, statusBar);
		this.setStage(stage);
	}

	public void setStatusBarBackground() {
//		Rectangle rect = new Rectangle(0, 0, 300, 50);
//		rect.setFill(Color.ANTIQUEWHITE);
//		rect.setOpacity(0.5);
//		rect.setArcWidth(50);
//		rect.setArcHeight(50);
//		rect.setX(0);
//		rect.setTranslateY(-(WINDOW_HEIGHT-455));

		GridPane gridPane = new GridPane();

		this.statusBar.getChildren().addAll(gridPane);
	}

	//method to add the stage elements
	public void setStage(Stage stage) {
		this.stage = stage;
		this.stage.setResizable(false);
		this.stage.setX(400);
		this.stage.setY(100);

		//set stage elements here
		//invoke the start method of the animation timer
		//set stage elements here
		this.root.getChildren().addAll(background, canvas, statusBar); // canvas to root
		this.stage.setScene(this.scene);
		this.stage.show();
		this.gametimer.start();
	}

	Scene getScene() {
		return this.scene;
	}

	void setBackground() {
		Image bgImage = new Image("images/gameplayBackground.png",MAP_WIDTH,MAP_HEIGHT,false,false);
		this.background = new ImageView(bgImage);
	}
}

