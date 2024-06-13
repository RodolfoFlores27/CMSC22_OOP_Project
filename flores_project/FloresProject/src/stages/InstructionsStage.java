package stages;

import java.util.ArrayList;
import java.util.Collections;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class InstructionsStage {
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

	public InstructionsStage(Stage stage) {
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
		//vbox.setLayoutX(100);

		//Font theFont = Font.font("Bernard MT Condensed",FontWeight.BOLD,30);
		Text text1 = new Text("CONTROLS:");

		//vbox contents
		HBox hboxW = new HBox(40);
		//hboxW.setAlignment(Pos.CENTER);
		hboxW.setTranslateX(WINDOW_WIDTH/2-WINDOW_WIDTH/4);
		//Insets top right bottom left
		//hboxW.setPadding(new Insets(0,0,0,20));
		Image imgW = new Image("images/W.png", 40, 40, false, false);
		ImageView imgViewW = new ImageView(imgW);
		Text textW = new Text("Move Up");
		hboxW.getChildren().addAll(imgViewW, textW);



		HBox hboxA = new HBox(40);
		//hboxA.setAlignment(Pos.CENTER);
		hboxA.setTranslateX(WINDOW_WIDTH/2-WINDOW_WIDTH/4);
		//hboxA.setTranslateX(WINDOW_WIDTH/2);
		//hboxA.setAlignment(Pos.CENTER);
		//hboxA.setPadding(new Insets(0,0,0,20));
		Image imgA = new Image("images/A.png", 40, 40, false, false);
		ImageView imgViewA = new ImageView(imgA);
		Text textA = new Text("Move Left");
		hboxA.getChildren().addAll(imgViewA, textA);

		HBox hboxS = new HBox(40);
		hboxS.setTranslateX(WINDOW_WIDTH/2-WINDOW_WIDTH/4);
		//hboxS.setAlignment(Pos.CENTER);
		//hboxS.setPadding(new Insets(0,0,0,20));
		Image imgS = new Image("images/S.png", 40, 40, false, false);
		ImageView imgViewS = new ImageView(imgS);
		Text textS = new Text("Move Down");
		hboxS.getChildren().addAll(imgViewS, textS);

		HBox hboxD = new HBox(40);
		hboxD.setTranslateX(WINDOW_WIDTH/2-WINDOW_WIDTH/4);
		//hboxD.setAlignment(Pos.CENTER);
		//hboxD.setPadding(new Insets(0,0,0,20));
		Image imgD = new Image("images/D.png", 40, 40, false, false);
		ImageView imgViewD = new ImageView(imgD);
		Text textD = new Text("Move Right");
		hboxD.getChildren().addAll(imgViewD, textD);


		Text text2 = new Text("Eat smaller fishes to survive.");
		Text text3 = new Text("Grow in size as you eat more.");
		Text text4 = new Text("Collect powerups for temporary buffs.");
		Text text5 = new Text("Don't get eaten by bigger fish or it's game over.");

		Font theFont = Font.font("Aharoni",FontWeight.NORMAL,15);
		ArrayList<Text> texts = new ArrayList<Text>();
		Collections.addAll(texts, text1, text2, text3, text4, text5);
		for (Text text : texts) {
			text.setFont(theFont);
		}

		vbox.getChildren().addAll(text1,hboxW,hboxA,hboxS,hboxD, text2, text3, text4, text5);
		//apply lists for these texts for easier implementation when there's too many
		// Create StackPane to apply both canvas and the VBox

		Rectangle rect = new Rectangle(50, 50, WINDOW_WIDTH-50, WINDOW_HEIGHT-50);
		rect.setFill(Color.ANTIQUEWHITE);
		rect.setOpacity(0.5);
		rect.setArcWidth(50);
		rect.setArcHeight(50);

		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(canvas, rect, vbox);
		this.root.getChildren().addAll(stackPane, backButton); // canvas to root
	}

	Scene getScene() {
		return this.scene;
	}

}
