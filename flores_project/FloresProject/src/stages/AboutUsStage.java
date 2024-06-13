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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class AboutUsStage {
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

	public AboutUsStage(Stage stage) {

//		When clicked, the scene/window about the developer(s) of the game should appear
//		Cite your references here (cmsc22 base code, images used, and other references)

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

		Text text1 = new Text("CMSC 22 : Mini Project\n");
		Text text2 = new Text("\tFor this mini project I tried to recreate the game "
				+ "Feeding Frenzy. In Feeding Frenzy, you play as a fish. Together "
				+ "with other fishes, your aim is to survive. Eat smaller fishes and "
				+ "grow in size. Avoid bigger fishes or it's game over. Grow larger and "
				+ "larger to surpass the bigger ones. Once you do, it's now your time to "
				+ "prey upon them as they prey upon you when you are still a little one.\n\n");
		Text text3 = new Text("\tThis project is still not at its best state. However, I "
				+ "thank myself for being able, to reach this point into the project.\n\n");
		Text text4 = new Text(" * Things to note: \n\n \tThere is a bug where sprites"
				+ " get to move outside the gameArea despite being restricted by the code."
				+ "The bug doesn't happen upon pressing only one button. However with, "
				+ "simultaneous presses the bug is highly expected to occur \n\n \tThe "
				+ "collisions will look weird at times especially when the fishes"
				+ " start to get bigger. This is because in collision checking, "
				+ "a rectangle is being checked, despite the fishes obviously not"
				+ "looking like rectangles.\n\n");

		Text text5 = new Text("References: \n\n");
		Text text6 = new Text("https://www.onlinewebfonts.com/icon/ --WASD images\n");
		Text text7 = new Text("https://feedingfrenzy.fandom.com/wiki/Fish --Fish images\n");
		Text text8 = new Text("https://www.templatemonster.com/illustrations/ocean-"
				+ "underwater-landscape-illustration-125155.html --Gameplay Background\n");
		Text text9 = new Text("\n@author <Rodolfo P. Flores III>\n@created_date 2022-12-09\n\n");

		Font theFont = Font.font("Aharoni",FontWeight.NORMAL,15);
		ArrayList<Text> texts = new ArrayList<Text>();
		Collections.addAll(texts, text1, text2, text3, text4, text5, text6, text7, text8
				, text9);
		for (Text text : texts) {
			text.setFont(theFont);
		}

		TextFlow textFlow = new TextFlow();

		textFlow.getChildren().addAll(text1, text2, text3, text4, text5, text6, text7,
				text8, text9);

		textFlow.setMaxSize(WINDOW_WIDTH-80, WINDOW_HEIGHT-80);
		textFlow.setTextAlignment(TextAlignment.CENTER);



		Rectangle rect = new Rectangle(50, 50, WINDOW_WIDTH-50, WINDOW_HEIGHT-50);
		rect.setFill(Color.ANTIQUEWHITE);
		rect.setOpacity(0.5);
		rect.setArcWidth(50);
		rect.setArcHeight(50);

		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(canvas, rect, textFlow);
		this.root.getChildren().addAll(stackPane, backButton); // canvas to root
	}

	Scene getScene() {
		return this.scene;
	}
}
