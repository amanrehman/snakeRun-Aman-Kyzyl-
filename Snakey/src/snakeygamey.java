import javafx.event.EventHandler;
import javafx.scene.input.*;
import javafx.application.Application;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.plaf.basic.BasicTabbedPaneUI.MouseHandler;

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
	private static final int speed=3;
	Shield shield;
	Magnet magnet;
	Ball ball;
	Destroy destroy;
	Wall wall;
	Coin coin;

	private Parent createContent() throws FileNotFoundException {
		Random r= new Random();
        gen=new Chain();
		root=new Pane();
		root.setStyle("-fx-background-color: black;");
		snake= new Snake(root);
		root.setPrefSize(360, 640);							//size of window.
		
		shield =new Shield();
		magnet=new Magnet();
		ball= new Ball();
		destroy=new Destroy();
		wall=new Wall();
		coin= new Coin();
		Button pauseButton=new Button("||");
		root.getChildren().add(pauseButton);
		
		pauseButton.setOnAction(e ->{
			pause();
		});
//        ChainGenerator();
		shield =new Shield();
		magnet=new Magnet();
		ball= new Ball();
		destroy=new Destroy();
		wall=new Wall();
		coin= new Coin();
		
        timer = new AnimationTimer() {						
            @Override
            public void handle(long now) {
            	snake.updatemovement();
            	shield.updatemovement(root,speed);
            	magnet.updatemovement(root,speed);
            	ball.updatemovement(root,speed);
            	destroy.updatemovement(root,speed);
            	coin.updatemovement(root,speed);
            	wall.updatemovent(root,speed);
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
		ArrayList<Text> constituentValList = new ArrayList<Text>();
	
//		for(int i=0;i<5;i++) constituentRectangles[i]=row[i].getR();
		
		for(int i=0;i<5;i++) {
			constituentRectanglesList.add(row.get(i).getR());
			constituentValList.add(row.get(i).getTextValue());			
		}

		Random ran=new Random();
		double probability= ran.nextDouble();
		int howManyToDel=ran.nextInt(2)+2;

		if(probability<1) {
			while(howManyToDel>0) {
				int index=ran.nextInt(constituentRectanglesList.size()-1)+1;
				constituentRectanglesList.remove(index);
				constituentValList.remove(index);
				row.remove(index);
				howManyToDel--;
			}
		}

		
		Group rectStack = new Group();
		Group textStack=new Group();

		for (Rectangle rect: constituentRectanglesList)
		{	
			int index=constituentRectanglesList.indexOf(rect);

			Text val = constituentValList.get(index);
			val.setX(rect.getX()+36);
			val.setY(36);
			val.setFill(Color.BLACK);

			rectStack.getChildren().addAll(rect);
			textStack.getChildren().addAll(val);
		}
		root.getChildren().addAll(rectStack, textStack);
	}

	private void onUpdate() {
		for(Block block: row) {			
			block.getR().setTranslateY(block.getR().getTranslateY()+speed);
			block.getTextValue().setTranslateY(block.getR().getTranslateY()+speed);
		}
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
		
		if(wall.getRec().getBoundsInParent().intersects(snakeHead.getBoundsInParent())) {
			System.out.println("...");
			System.out.println(snakeHead.getBoundsInParent());
//			snakeHead.setLayoutX(snakeHead.getLayoutX());
//			snakeHead.setTranslateX(snake.trail.tailtrail.get(1).getTranslateX());
//			snake.updatemovement();
			mouseHandler.
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