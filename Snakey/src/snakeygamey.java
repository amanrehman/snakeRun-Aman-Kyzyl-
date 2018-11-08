import javafx.event.EventHandler;
import javafx.scene.input.*;
import javafx.application.Application;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.control.*;

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
	private Timeline blockTimeline;
	private Timeline wallTimeline;

    private Pane root;

	private Parent createContent() throws FileNotFoundException {
		Random r= new Random();
        gen=new Chain();
		root=new Pane();
		root.setStyle("-fx-background-color: black;");
		snake= new Snake(root);
		root.setPrefSize(360, 640);							//size of window.
		
		Button pauseButton=new Button("||");
		root.getChildren().add(pauseButton);
		
		pauseButton.setOnAction(e ->{
			pause();
		});
//        ChainGenerator();
		Shield shield =new Shield();
		Magnet magnet=new Magnet();
		Ball ball= new Ball();
		Destroy destroy=new Destroy();
		Wall wall=new Wall();
		Coin coin= new Coin();
		
        timer = new AnimationTimer() {						
            @Override
            public void handle(long now) {
            	snake.updatemovement();
            	shield.updatemovement(root);
            	magnet.updatemovement(root);
            	ball.updatemovement(root);
            	destroy.updatemovement(root);
            	coin.updatemovement(root);
            	wall.updatemovent(root);
            	onUpdate();
            }
        };
        timer.start();
        
        timeline = new Timeline();
        timeline.setCycleCount(timeline.INDEFINITE);
        KeyFrame shieldtime = 
        		 new KeyFrame(Duration.seconds(r.nextInt(30)), e -> {
 	    			shield.generatenewtoken(root);
 	    });
        timeline.getKeyFrames().add(shieldtime);
        KeyFrame magnettime = 
       		 new KeyFrame(Duration.seconds(r.nextInt(30)), e -> {
	    			magnet.generatenewtoken(root);
	    });
        timeline.getKeyFrames().add(magnettime);
        KeyFrame balltime = 
      		 new KeyFrame(Duration.seconds(r.nextInt(30)), e -> {
	    			ball.generatenewtoken(root);
	    });
        timeline.getKeyFrames().add(balltime);
        KeyFrame destroytime = 
     		 new KeyFrame(Duration.seconds(r.nextInt(30)), e -> {
	    			destroy.generatenewtoken(root);
	    });
        timeline.getKeyFrames().add(destroytime);
        KeyFrame cointime = 
     		 new KeyFrame(Duration.seconds(r.nextInt(30)), e -> {
	    			coin.generatenewtoken(root);
	    });
        timeline.getKeyFrames().add(cointime);
        timeline.play();
        
        
        blockTimeline = new Timeline(
        	    new KeyFrame(Duration.seconds(2.5
        	    		), e -> {
        	        ChainGenerator();
        	    })
        	);
        blockTimeline.setCycleCount(Animation.INDEFINITE);
        blockTimeline.play();

        wallTimeline = new Timeline(
        	    new KeyFrame(Duration.seconds(2.5
        	    		), e -> {
        	        wall.create(root);
        	    })
        	);
        wallTimeline.setCycleCount(Animation.INDEFINITE);
        wallTimeline.play();
        
        return root;
    }
	
	private void pause() {
		timer.stop();
		timeline.pause();
		blockTimeline.pause();
		wallTimeline.pause();
		Pane pausePane=new Pane();
		Button restartButton=new Button("Restart");
		pausePane.getChildren().add(restartButton);
		
		Scene pause=new Scene(pausePane, 360, 640);
		
		Stage currentStage=(Stage) root.getScene().getWindow();
		Scene previousScene=currentStage.getScene();
		currentStage.setScene(pause);
		restartButton.setOnAction(e ->{
			timer.start();
			timeline.play();
			blockTimeline.play();
			currentStage.setScene(previousScene);
		});
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
			val.setFill(Color.WHITE);
			stack.getChildren().addAll(rect);
		}
		onUpdate();
		
		
		//root.getChildren().addAll(constituentRectanglesList);
		
		root.getChildren().addAll(stack);
	}
	
	private void onUpdate() {
		int speed=5;
		Group stack = new Group();	
		for(Block rect: row) {			
			rect.getR().setTranslateY(rect.getR().getTranslateY()+speed);
			
//			System.out.println(rect.getR().getTranslateY()+speed);
			Text val = new Text ("");
			val.setX(rect.getX()+36);
			val.setTranslateY(rect.getR().getTranslateY()+speed);

//			System.out.println(row.size());
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
	            blockTimeline.stop();
	            wallTimeline.stop();
	            String gameover = "G4m3 0v3R";
	            HBox hBox = new HBox();
	            hBox.setTranslateY(300);
	            hBox.setTranslateX(50);
	            root.getChildren().add(hBox);
	            for (int i = 0; i < gameover.toCharArray().length; i++) {
	            	char letter = gameover.charAt(i);
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
		MainMenu elements=new MainMenu();
		Pane mainMenuPane=new Pane();
		mainMenuPane.setStyle("-fx-background-color: black");
		mainMenuPane.setPrefSize(360, 640);
		Button startButton=elements.createStartButton();
		Button leadButton=elements.createLeadButton();

		mainMenuPane.getChildren().addAll(startButton, leadButton);
		Scene main=new Scene(mainMenuPane,360,640);

		stage.setScene(main);
		stage.show();
		
		startButton.setOnAction(e -> {
			try {
				stage.setScene(new Scene(createContent()));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
 		});
		
		leadButton.setOnAction(e ->{
			Pane leadPane=new Pane();
			
			Button goBack=new Button("Go Back");
			leadPane.getChildren().add(goBack);
			
			Scene leadScene=new Scene(leadPane, 360,640);
			Scene previousScreen=stage.getScene();
		    
			Leaderboard leaderTable=new Leaderboard();
			TableView table=leaderTable.createTable();

			leadPane.getChildren().add(table);
	        stage.setScene(leadScene);

			goBack.setOnAction(e2 ->{
				stage.setScene(previousScreen);
			});
		});    
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
