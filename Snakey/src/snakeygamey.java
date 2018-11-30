import java.io.FileNotFoundException;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Random;
import javafx.event.EventHandler;
import javafx.scene.input.*;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.animation.*;
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

public class snakeygamey extends Application {
	private AnimationTimer timer;
	private Snake snake;
	private Chain gen;
	private ArrayList<Block> row=new ArrayList<Block>();
	private Timeline timeline;
	private Timeline blockTimeline;
	private Timeline wallTimeline;
    private Pane root;
	private static int speed=4;
	private Shield shield;
	private Magnet magnet;
	private Ball ball;
	private Destroy destroy;
	private Wall wall;
	private Coin coin;
	private int blockFlag;
	private int ballFlag,shieldFlag,magnetFlag,destroyFlag,coinFlag;
	private int wallFlag;
	private int shieldPower;
	private int collidingFlag;
	private int score,coinScore;
	private Text scoreText,coinScoreText;
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
		wallFlag=0;
		score=0;
		scoreText=new Text(Integer.toString(score));
		scoreText.setFill(Color.WHITE);
		scoreText.setFont(Font.font ("Verdana", 15));
		scoreText.setX(320);
		scoreText.setY(40);
		
		coinScore=0;
		coinScoreText=new Text(Integer.toString(coinScore));
		coinScoreText.setFill(Color.GOLD);
		coinScoreText.setFont(Font.font ("Verdana", 15));
		coinScoreText.setX(260);
		coinScoreText.setY(40);
		
		root.getChildren().addAll(scoreText,coinScoreText);
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
            	ball.updatemovementText(root,ball.getPositionX(),ball.getPositionY());
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
	    			ball.setValue(ball.generatenevalue());
	    			ball.generatenewvaluetext(root,ball.getPositionX(),ball.getPositionY());
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
			val.setFont(Font.font ("Verdana", 15));
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

	private void updateLength(int flag, int value) {
		if(flag==-1) {
			int blockValue=value;
			if(blockValue<=5 && snake.getLength()>blockValue+1)
			{
				for(int i=snake.getLength(),j=0;j<blockValue;i--,j++) {
					//remove tail circles and remove from root
					//root.getChildren().remove(snake.getTrail().getTailtrail().get(i-1));
					snake.getTrail().getTailtrail().get(i-1).setVisible(false);
					snake.setLength(snake.getLength()-1);
					score=score+1;
					scoreText.setText(Integer.toString(score));
				}
			}
			else {
			
				PauseTransition pause = new PauseTransition(Duration.millis(100));
				pause.setOnFinished(event -> {
					if(snake.getLength()>0) {
						snake.getTrail().getTailtrail().get(snake.getLength()-1).setVisible(false);
						score=score+1;
						snake.setLength(snake.getLength()-1);
						snake.updatemovement(root);
						scoreText.setText(Integer.toString(score));
					}
					else {
						gameOver();
					}
				});
	//			pause.play();
				SequentialTransition seq = new SequentialTransition(pause);
				seq.setCycleCount(blockValue);
				seq.play();
			}
		}
		else if(flag==1){
			int ballValue=value;
			int prevLen=snake.getLength();
//			for(int i=prevLen;i<prevLen+ballValue;i++) {
//				snake.getTrail().getTailtrail().get(i-1).setVisible(true);
//				snake.setLength(snake.getLength()+1);
//			}
			
			PauseTransition pause = new PauseTransition(Duration.millis(100));
			pause.setOnFinished(event -> {
				snake.getTrail().getTailtrail().get(snake.getLength()).setVisible(true);
				snake.setLength(snake.getLength()+1);
			});
//			pause.play();
			SequentialTransition seq = new SequentialTransition(pause);
			seq.setCycleCount(ballValue);
			seq.play();
		}
	}

	private void checkState() {
		Node snakeHead=null;
		if(snake.getLength()!=0) {
			snakeHead=snake.getTrail().getTailtrail().get(0);
		
		for (Block r: row) {
			Node blk=r.getR();
			if(snakeHead!=null)
			if (blk.getBoundsInParent().intersects(snakeHead.getBoundsInParent()) && blockFlag==1) {
				collidingFlag=1;
//				if(snake.getLength()>=r.getValue()) {
//					root.getChildren().remove(blk);
//					snake.setLength(snake.getLength()-r.getValue());

					if(snake.getLength()==0) {
						gameOver();
					}
					
					else {
						/*decreasing snake length(variable), updating Group snakeBody, removing tail circles of snake
						 and text on screen */

						if(r.getValue()>5 || snake.getLength()<=r.getValue()) {
							timer.stop();
							timeline.pause();
							blockTimeline.pause();
							wallTimeline.pause();

//							for(int i=r.getValue();i>0;i--) {
//								r.getTextValue().setVisible(false);
								
								PauseTransition pause = new PauseTransition(Duration.millis(100));
								pause.setOnFinished(event -> {
									if(snake.getLength()>0) {
										r.setValue(r.getValue()-1);
										r.getTextValue().setText(Integer.toString(r.getValue()));
										if(r.getValue()==0) {
											timer.start();
											timeline.play();
											blockTimeline.play();
											wallTimeline.play();
											blk.setVisible(false);
											collidingFlag=0;
											r.getTextValue().setVisible(false);
										}
									}
								});
//								pause.play();
								SequentialTransition seq = new SequentialTransition(pause);
								if(snake.getLength()>r.getValue())
									seq.setCycleCount(r.getValue());
								else
									seq.setCycleCount(snake.getLength());
								seq.play();

//							r.getTextValue().setVisible(true);
//							}
//							PauseTransition pause = new PauseTransition(Duration.millis(100*r.getValue()));
//							pause.setOnFinished(event -> {
								
								
//						
//							});
//							pause.play();
						}
						
						else {
							blk.setVisible(false);
							collidingFlag=0;
							r.getTextValue().setVisible(false);
						}
						updateLength(-1,r.getValue());
					}
					
//				}
//				else {
//					updateLength(-1, r.getValue());
////					gameOver();
//				}

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
			root.getChildren().removeAll(ball.getImageView(),ball.getC(),ball.getValueText());
			updateLength(1,ball.getValue());
			ballFlag=0;
			collidingFlag=0;
		}

		if(coin.getC().getBoundsInParent().intersects(snakeHead.getBoundsInParent()) && coinFlag==1) {
			collidingFlag=1;
			root.getChildren().removeAll(coin.getC(),coin.getImageView());
			coinScore=coinScore+1;
			coinScoreText.setText(Integer.toString(coinScore));
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
		if(wall.getRec().getBoundsInParent().intersects(snakeHead.getBoundsInParent())){
			if(wall.getRec().getX()-180>snake.getTranslateX()) {
				wallFlag=-1;
//				System.out.println("stay left");
			}
			else {
				wallFlag=1;
//				System.out.println("stay right");
			}
		}
		}
		else {
			gameOver();
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

	private static void setSpeed(int s) {
		speed=s;
	}

	private Group setDiffButtons(Stage stage){
		Button easyButton=new Button("Easy");
		easyButton.setLayoutX(140);
		easyButton.setLayoutY(300);

		Button mediumButton=new Button("Medium");
		mediumButton.setLayoutX(140);
		mediumButton.setLayoutY(340);

		Button hardButton=new Button("Hard");
		hardButton.setLayoutX(140);
		hardButton.setLayoutY(380);

		Group diffMenuButtons=new Group();
		diffMenuButtons.getChildren().addAll(easyButton, mediumButton, hardButton);

		return diffMenuButtons;
	}

	@Override
	public void start(Stage stage) throws Exception {
		MainMenu elements=new MainMenu();
		Pane mainMenuPane=new Pane();
		mainMenuPane.setStyle("-fx-background-color: black");
		mainMenuPane.setPrefSize(360, 640);
		Button startButton=elements.createStartButton();
		Button leadButton=elements.createLeadButton();

		Group diffMenuButtons=setDiffButtons(stage);
		Pane diffMenuPane=new Pane();
		diffMenuPane.setStyle("-fx-background-color: black");
		diffMenuPane.setPrefSize(360, 640);
		
		mainMenuPane.getChildren().addAll(startButton, leadButton);
		Scene main=new Scene(mainMenuPane,360,640);
		stage.setScene(main);
		stage.show();

		diffMenuPane.getChildren().add(diffMenuButtons);
		Scene diffMenuScene=new Scene(diffMenuPane,360,640);

		startButton.setOnAction(e -> {		
			stage.setScene(diffMenuScene);

			diffMenuButtons.getChildren().get(0).setOnMouseClicked(e1 -> {
				setSpeed(4);
				try {
					stage.setScene(new Scene(createContent()));
					stage.getScene().setOnMouseMoved(mouseHandler);
				} catch (FileNotFoundException e4) {
					e4.printStackTrace();
				}
//					System.out.println(speed);
			});

			diffMenuButtons.getChildren().get(1).setOnMouseClicked(e2 -> {
				setSpeed(5);
				try {
					stage.setScene(new Scene(createContent()));
					stage.getScene().setOnMouseMoved(mouseHandler);
				} catch (FileNotFoundException e4) {
					e4.printStackTrace();
				}
//					System.out.println(speed);
			});

			diffMenuButtons.getChildren().get(2).setOnMouseClicked(e3 -> {
				setSpeed(6);
				try {
					stage.setScene(new Scene(createContent()));
					stage.getScene().setOnMouseMoved(mouseHandler);
				} catch (FileNotFoundException e4) {
					e4.printStackTrace();
				}
//					System.out.println(speed);
			});
		});

//		stage.getScene().setOnKeyPressed(event -> {
//            switch (event.getCode()) {
//                case LEFT:
//                	TranslateTransition t= new TranslateTransition();
//                	t.setDuration(Duration.millis(25));
//                	t.setToX(snake.getTranslateX()-5);
//                	t.setToY(snake.getTranslateY());
//                	t.setNode(snake.getTrail().getTailtrail().get(0));
//                	t.play();
//                	//snake.setTranslateX(snake.getTranslateX()-5);
//            		//snake.updateleftmovement();
//                	break;
//                case RIGHT:
//                	t= new TranslateTransition();
//                	t.setDuration(Duration.millis(25));
//                	t.setToX(snake.getTranslateX()+5);
//                	t.setToY(snake.getTranslateY());
//                	t.setNode(snake.getTrail().getTailtrail().get(0));
//                	t.play();
//                	
//                	//snake.setTranslateX(snake.getTranslateX()+5);
//            		//snake.updaterightmovement();
//            		break;
//                default:
//                    break;
//            }
//        });
//        stage.getScene().setOnMouseMoved(mouseHandler);
// 		});

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
				 if(wallFlag==1) {
					 TranslateTransition t= new TranslateTransition();
			        	t.setDuration(Duration.millis(1));
			        	if(mouseEvent.getX()-180>wall.getRec().getX()-180)
			        		t.setToX(mouseEvent.getX()-180);
			        	else
			        		t.setToX(t.getToX());
			        	t.setToY(snake.getTranslateY());
			        	t.setNode(snake.getTrail().getTailtrail().get(0));
			        	t.play();
			        	
			        	if(wall.getRec().getTranslateY()>=320) {
			        		wallFlag=0;
			        	}
				 }
				 else if(wallFlag==-1) {
					 TranslateTransition t= new TranslateTransition();
			        	t.setDuration(Duration.millis(1));
			        	if(mouseEvent.getX()-180<wall.getRec().getX()-180)
			        		t.setToX(mouseEvent.getX()-180);
			        	else
			        		t.setToX(t.getToX());
			        	t.setToY(snake.getTranslateY());
			        	t.setNode(snake.getTrail().getTailtrail().get(0));
			        	t.play();
			        	
			        	if(wall.getRec().getTranslateY()>=320) {
			        		wallFlag=0;
			        	}
				 }
				 else {
					 TranslateTransition t= new TranslateTransition();
			        	t.setDuration(Duration.millis(1));
			        	double nextX=mouseEvent.getX()-180;
//			        	System.out.println(nextX+" "+(wall.getRec().getX()-180)+" "+snake.getTranslateX());
//			        	if(nextX>wall.getRec().getX()-180 && wall.getRec().getX()-180>snake.getTranslateX())
//			        	{
//			        		System.out.println("lolol");
//			        		t.setToX(wall.getRec().getX()-180);
//			        	}
//			        	else if(nextX<(wall.getRec().getX()-180) && (wall.getRec().getX()-180)<snake.getTranslateX())
//			        	{
//			        		t.setToX(wall.getRec().getX()-180);
//			        		System.out.println("lolol");
//			        	}
//			        	else
			        	t.setToX(nextX);
			        	t.setToY(snake.getTranslateY());
			        	t.setNode(snake.getTrail().getTailtrail().get(0));
			        	t.play();
				 }
			 }
	        }
	};

	public static void main(String[] args) {
        launch(args);
    }
}
