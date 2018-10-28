import javafx.event.EventHandler;
import javafx.scene.input.*;
import javafx.application.Application;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.*;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.layout.*;

public class snakeygamey extends Application {
	private AnimationTimer timer;
	private Snake snake;
	private Chain gen;
	private ArrayList<Block> row=new ArrayList<Block>();
	private Timeline timeline;

    private Pane root;

	private Parent createContent() {
        gen=new Chain();
		root=new Pane();
		root.setStyle("-fx-background-color: black;");
		snake= new Snake(root);
		root.setPrefSize(360, 640);							//size of window.
//        ChainGenerator();

        timer = new AnimationTimer() {						//AnimationTimer is called in each frame.
            @Override
            public void handle(long now) {
            	snake.updatemovement();
                onUpdate();
            }
        };
        timer.start();

        timeline = new Timeline(
        	    new KeyFrame(Duration.seconds(2.5
        	    		), e -> {
        	        ChainGenerator();
        	    })
        	);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        return root;
    }

	public void ChainGenerator() {
		row=gen.generateRow();
//		Rectangle[] constituentRectangles = new Rectangle[5];
		ArrayList<Rectangle> constituentRectanglesList = new ArrayList<Rectangle>();
	
//		for(int i=0;i<5;i++) constituentRectangles[i]=row[i].getR();
		
		for(int i=0;i<5;i++) constituentRectanglesList.add(row.get(i).getR());

		Random ran=new Random();
		double probability= ran.nextDouble();
		int howManyToDel=ran.nextInt(2)+2;

		if(probability<1) {
			while(howManyToDel>0) {
				int index=ran.nextInt(constituentRectanglesList.size()-1)+1;
				constituentRectanglesList.remove(index);
				row.remove(index);
				howManyToDel--;
			}
		}

		
		Group stack = new Group();		
		for (Rectangle rect: constituentRectanglesList)
		{	
			Text val = new Text ("");
			val.setX(rect.getX()+36);

			System.out.println(constituentRectanglesList.size());
			val.setFill(Color.WHITE);
			stack.getChildren().addAll(rect);
		}
		System.out.println();
		onUpdate();
		
		
		//root.getChildren().addAll(constituentRectanglesList);
		
		root.getChildren().addAll(stack);
	}
	
	private void onUpdate() {
		int speed=5;
		Group stack = new Group();	
		for(Block rect: row) {			
			rect.getR().setTranslateY(rect.getR().getTranslateY()+speed);
			
			System.out.println(rect.getR().getTranslateY()+speed);
			Text val = new Text ("");
			val.setX(rect.getX()+36);
			val.setTranslateY(rect.getR().getTranslateY()+speed);

			System.out.println(row.size());
			val.setFill(Color.WHITE);
			stack.getChildren().addAll(rect, val);
			if(rect.getR().getTranslateY()+speed>500) {
				stack.getChildren().remove(rect);
			}
		}
		root.getChildren().addAll(stack);
		checkState();
    }
	private void checkState() {
		Node snakeHead=snake.trail.tailtrail.get(0);
		for (Block r: row) {
			Node blk=r.getR();
			if (blk.getBoundsInParent().intersects(snakeHead.getBoundsInParent())) {
//				root.getChildren().remove(snakeHead);
	            timer.stop();
	            timeline.stop();
	            String win = "reKt";
	            HBox hBox = new HBox();
	            hBox.setTranslateY(0);
	            hBox.setTranslateX(0);
	            root.getChildren().add(hBox);
	            for (int i = 0; i < win.toCharArray().length; i++) {
	            	char letter = win.charAt(i);
	            	Text text = new Text(String.valueOf(letter));
	                text.setFont(Font.font(48));
	                text.setOpacity(0);
	                text.setFill(Color.RED);
	                hBox.getChildren().add(text);

	                FadeTransition ft = new FadeTransition(Duration.seconds(0.66), text);
	                ft.setToValue(1);
	                ft.setDelay(Duration.seconds(i * 0.15));
	                ft.play();
	                }
	        }
		}
		if(row.size()!=0)
			if(row.get(0).getTranslateX()>=640) {
				for(Block r:row)
					root.getChildren().remove(r.getR());
			}
	}
	@Override
	public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent()));
        stage.getScene().setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                	TranslateTransition t= new TranslateTransition();
                	t.setDuration(Duration.millis(25));
                	t.setToX(snake.getTranslateX()-5);
                	t.setToY(snake.getTranslateY());
                	t.setNode(snake.trail.tailtrail.get(0));
                	t.play();
                	//snake.setTranslateX(snake.getTranslateX()-5);
            		//snake.updateleftmovement();
                	break;
                case RIGHT:
                	t= new TranslateTransition();
                	t.setDuration(Duration.millis(25));
                	t.setToX(snake.getTranslateX()+5);
                	t.setToY(snake.getTranslateY());
                	t.setNode(snake.trail.tailtrail.get(0));
                	t.play();
                	
                	//snake.setTranslateX(snake.getTranslateX()+5);
            		//snake.updaterightmovement();
            		break;
                default:
                    break;
            }
        });
        stage.getScene().setOnMouseMoved(mouseHandler);
        stage.show();
	}
	EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
		 @Override
	        public void handle(MouseEvent mouseEvent) {
			 TranslateTransition t= new TranslateTransition();
	        	t.setDuration(Duration.millis(1));
	        	t.setToX(mouseEvent.getX()-180);
	        	t.setToY(snake.getTranslateY());
	        	t.setNode(snake.trail.tailtrail.get(0));
	        	t.play();
	        }
	};
	public static void main(String[] args) {
        launch(args);
    }
}
