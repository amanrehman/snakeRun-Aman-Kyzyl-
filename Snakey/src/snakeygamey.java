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
	private static final int speed=5;
	private Shield shield;
	private Magnet magnet;
	private Ball ball;
	private Destroy destroy;
	private Wall wall;
	private Coin coin;
	private int blockFlag;
	private int ballFlag,shieldFlag,magnetFlag,destroyFlag,coinFlag;
	private int shieldPower;
	private int collidingFlag;
	private static final int tokenDuration=10;
	
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
		blockFlag=0;
		collidingFlag=0;
		shieldPower=0;
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
            	snake.updatemovement(root);
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
        		 new KeyFrame(Duration.seconds(r.nextInt(tokenDuration)), e -> {
        			shieldFlag=1;
 	    			shield.generatenewtoken(root);
 	    });
        timeline.getKeyFrames().add(shieldtime);
        KeyFrame magnettime = 
       		 new KeyFrame(Duration.seconds(r.nextInt(tokenDuration)), e -> {
       			 	magnetFlag=1;
	    			magnet.generatenewtoken(root);
	    });
        timeline.getKeyFrames().add(magnettime);
        KeyFrame balltime = 
      		 new KeyFrame(Duration.seconds(r.nextInt(tokenDuration)), e -> {
      			 	ballFlag=1;
	    			ball.generatenewtoken(root);
	    });
        timeline.getKeyFrames().add(balltime);
        KeyFrame destroytime = 
     		 new KeyFrame(Duration.seconds(r.nextInt(tokenDuration)), e -> {
     			 	destroyFlag=1;
	    			destroy.generatenewtoken(root);
	    });
        timeline.getKeyFrames().add(destroytime);
        KeyFrame cointime = 
     		 new KeyFrame(Duration.seconds(r.nextInt(tokenDuration)), e -> {
     			 	coinFlag=1;
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
			wallTimeline.play();
			currentStage.setScene(previousScene);
		});
	}

	public void ChainGenerator() {
		row=gen.generateRow();
		blockFlag=1;
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

	private void updateLength(int flag, int blockValue) {
		if(flag==-1) {
			int prevSizeOfSnake=snake.getTrail().getTailtrail().size();

			for(int i=prevSizeOfSnake,j=0;j<blockValue;i--,j++) {
				//remove tail circles and remove from root
				root.getChildren().remove(snake.getTrail().getTailtrail().get(i-1));
				snake.getTrail().getTailtrail().get(i-1).setVisible(false);
				snake.setLength(snake.getLength()-1);
//				System.out.println(snake.getLength());
//				
//				System.out.println(".."+snake.getTrail().getTailtrail().size());
				//update Group snakeBody
				snake.getTrail().getSnakeBody().getChildren().remove(snake.getTrail().getTailtrail().get(i-1));
			}
		}
	}

	private void checkState() {
		Node snakeHead=null;
		if(snake.getLength()!=0) {
			snakeHead=snake.getTrail().getTailtrail().get(0);
		}
		else {
			gameOver();
		}
		
		for (Block r: row) {
			Node blk=r.getR();
			if(snakeHead!=null)
			if (blk.getBoundsInParent().intersects(snakeHead.getBoundsInParent()) && blockFlag==1) {
				collidingFlag=1;
				if(snake.getLength()>=r.getValue()) {
//					root.getChildren().remove(blk);
//					snake.setLength(snake.getLength()-r.getValue());

					if(snake.getLength()==0) {
						gameOver();
					}
					
					else {
						/*decreasing snake length(variable), updating Group snakeBody, removing tail circles of snake
						 and text on screen */

						if(r.getValue()>5) {
							timer.stop();
							timeline.pause();
							blockTimeline.pause();
							wallTimeline.pause();
							
							PauseTransition pause = new PauseTransition(Duration.millis(100*r.getValue()));
							pause.setOnFinished(event -> {
								timer.start();
								timeline.play();
								blockTimeline.play();
								wallTimeline.play();
								blk.setVisible(false);
								collidingFlag=0;
								r.getTextValue().setVisible(false);
								
		
							});
//						Duration.millis(100);
//							mouseHandler.wait(Duration.millis(100*r.getValue());
							pause.play();
						}
						
						else {
							blk.setVisible(false);
							collidingFlag=0;
							r.getTextValue().setVisible(false);
						}
						updateLength(-1,r.getValue());
					}
					
				}
				else {
					gameOver();
				}

				blockFlag=0;
			}
		}

		if(destroy.getC().getBoundsInParent().intersects(snakeHead.getBoundsInParent()) && destroyFlag==1) {
			collidingFlag=1;
//			destroy.getC().setTranslateY(destroy.getC().getTranslateY()+200);
			root.getChildren().removeAll(destroy.getC(), destroy.getImageView());
			for(int i=0;i<row.size();i++) {
				root.getChildren().remove(row.get(i).getR());
//				row.remove(i);
			}
			destroyFlag=0;
			collidingFlag=0;
		}

		if(ball.getC().getBoundsInParent().intersects(snakeHead.getBoundsInParent()) && ballFlag==1) {
			collidingFlag=1;
			root.getChildren().removeAll(ball.imageView,ball.c);
			updateLength(1,0);
			ballFlag=0;
			collidingFlag=0;
		}

		if(coin.getC().getBoundsInParent().intersects(snakeHead.getBoundsInParent()) && coinFlag==1) {
			collidingFlag=1;
			root.getChildren().removeAll(coin.getC(),coin.getImageView());
			coinFlag=0;
			collidingFlag=0;
		}

		if(magnet.getC().getBoundsInParent().intersects(snakeHead.getBoundsInParent()) && magnetFlag==1) {
			collidingFlag=1;
			root.getChildren().removeAll(magnet.getC(),magnet.getImageView());			
			magnetFlag=0;
			collidingFlag=0;
		}

		if(shield.getC().getBoundsInParent().intersects(snakeHead.getBoundsInParent()) && shieldFlag==1) {
			collidingFlag=1;
			root.getChildren().removeAll(shield.getC(), shield.getImageView());
			shieldFlag=0;
			collidingFlag=0;
		}
	}

	private void gameOver() {
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
				e1.printStackTrace();
			}
	        stage.getScene().setOnKeyPressed(event -> {
	            switch (event.getCode()) {
	                case LEFT:
	                	TranslateTransition t= new TranslateTransition();
	                	t.setDuration(Duration.millis(25));
	                	t.setToX(snake.getTranslateX()-5);
	                	t.setToY(snake.getTranslateY());
	                	t.setNode(snake.getTrail().getTailtrail().get(0));
	                	t.play();
	                	//snake.setTranslateX(snake.getTranslateX()-5);
	            		//snake.updateleftmovement();
	                	break;
	                case RIGHT:
	                	t= new TranslateTransition();
	                	t.setDuration(Duration.millis(25));
	                	t.setToX(snake.getTranslateX()+5);
	                	t.setToY(snake.getTranslateY());
	                	t.setNode(snake.getTrail().getTailtrail().get(0));
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
			 if(collidingFlag==0) {
			 TranslateTransition t= new TranslateTransition();
	        	t.setDuration(Duration.millis(1));
	        	t.setToX(mouseEvent.getX()-180);
	        	t.setToY(snake.getTranslateY());
	        	t.setNode(snake.getTrail().getTailtrail().get(0));
	        	t.play();
			 }
	        }
	};

	public static void main(String[] args) {
        launch(args);
    }
}
