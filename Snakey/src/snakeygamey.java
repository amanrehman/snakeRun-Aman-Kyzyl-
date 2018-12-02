import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.util.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import javafx.event.EventHandler;
import javafx.scene.input.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.Group;

public class snakeygamey extends Application implements Serializable{
	private AnimationTimer timer;
	private static Snake snake;
	private Chain gen;
	private ArrayList<Block> row=new ArrayList<Block>();
	private Timeline timeline;
	private Timeline coinTimeline;
	private Timeline blockTimeline;
	private Timeline shieldTimeline;
	private Timeline magnetTimeline;
	private Timeline ballTimeline;
	private Timeline destroyTimeline;
	private static Timeline magnetPowerTimeline;
	private Timeline shieldPowerTimeline;
	private Timeline wallTimeline;
    private Pane root;
	private static double speed=4;
	private Shield shield;
	private Magnet magnet;
	private Ball ball;
	private Destroy destroy;
	private Wall wall;
	private Coin coin;
	private int blockFlag;
	private int ballFlag,shieldFlag,magnetFlag,destroyFlag,coinFlag;
	private int wallFlag;
	private static int shieldPower;
	private int collidingFlag;
	private static int score;
	private static int coinScore;
	private static Text scoreText;
	private static Text magnetTimeText;
	private static Text shieldTimeText;
	private static int magnetSeconds;
	private static int shieldSeconds;
	private static Text coinScoreText;
	private static final int tokenDuration=10;
	private static int gameOverFlag=0;
	Group rectStack = new Group();
	Group textStack=new Group();
	
	private Parent createContent(Stage stage) throws FileNotFoundException {
		Random r= new Random();
        gen=new Chain();
		root=new Pane();
		root.setStyle("-fx-background-color: black;");
		root.setPrefSize(360, 640);							//size of window.
		serialise(root);
		
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
		
		scoreText=new Text(Integer.toString(score));
		scoreText.setFill(Color.WHITE);
		scoreText.setFont(Font.font ("Verdana", 15));
		scoreText.setX(320);
		scoreText.setY(40);
		
		coinScoreText=new Text(Integer.toString(coinScore));
		coinScoreText.setFill(Color.GOLD);
		coinScoreText.setFont(Font.font ("Verdana", 15));
		coinScoreText.setX(260);
		coinScoreText.setY(40);
		
		magnetTimeText=new Text(Integer.toString(magnetSeconds));
		magnetTimeText.setFill(Color.WHITE);
		magnetTimeText.setFont(Font.font ("Verdana", 15));
		magnetTimeText.setX(200);
		magnetTimeText.setY(40);
		if((!root.getChildren().contains(magnetTimeText)) && magnetSeconds>0) {
			root.getChildren().add(magnetTimeText);
		}
		
		shieldTimeText=new Text(Integer.toString(shieldSeconds));
		shieldTimeText.setFill(Color.WHITE);
		shieldTimeText.setFont(Font.font ("Verdana", 15));
		shieldTimeText.setX(140);
		shieldTimeText.setY(40);
		if((!root.getChildren().contains(shieldTimeText)) && shieldSeconds>0) {
			root.getChildren().add(shieldTimeText);
		}
		
		root.getChildren().addAll(scoreText,coinScoreText);
		Button pauseButton=new Button("||");
		root.getChildren().add(pauseButton);

		pauseButton.setOnAction(e ->{
			pause();
		});

		MenuButton menuButton = new MenuButton("Options");
		menuButton.getItems().addAll(new MenuItem("Pause"), new MenuItem("Home"));
		root.getChildren().add(menuButton);

		menuButton.getItems().get(0).setOnAction(e ->{
			pause();
		});

		menuButton.getItems().get(1).setOnAction(e ->{
				root.getChildren().remove(snake);
				snake.setLength(4);
				snake=new Snake(root);
				score=0;
				scoreText=new Text(Integer.toString(score));
				coinScore=0;
				coinScoreText=new Text(Integer.toString(coinScore));
		});

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

        shieldTimeline = new Timeline();
        shieldTimeline.setCycleCount(Animation.INDEFINITE);
        KeyFrame shieldtime = 
        		 new KeyFrame(Duration.seconds(19.5), e -> {
        			shieldFlag=1;
 	    			shield.generatenewtoken(root);
 	    });
        shieldTimeline.getKeyFrames().add(shieldtime);
        shieldTimeline.play();
        
        magnetTimeline = new Timeline();
        magnetTimeline.setCycleCount(Animation.INDEFINITE);
        KeyFrame magnettime = 
       		 new KeyFrame(Duration.seconds(15.5), e -> {
       			 	magnetFlag=1;
	    			magnet.generatenewtoken(root);
	    });
        magnetTimeline.getKeyFrames().add(magnettime);
        magnetTimeline.play();
        
        ballTimeline = new Timeline();
        ballTimeline.setCycleCount(Animation.INDEFINITE);
        KeyFrame balltime = 
      		 new KeyFrame(Duration.seconds(3), e -> {
      			 	ballFlag=1;
	    			ball.generatenewtoken(root);
	    			ball.setValue(ball.generatenevalue());
	    			ball.generatenewvaluetext(root,ball.getPositionX(),ball.getPositionY());
	    });
        ballTimeline.getKeyFrames().add(balltime);
        ballTimeline.play();
        
        destroyTimeline = new Timeline();
        destroyTimeline.setCycleCount(Animation.INDEFINITE);
        KeyFrame destroytime = 
     		 new KeyFrame(Duration.seconds(8), e -> {
     			 	destroyFlag=1;
	    			destroy.generatenewtoken(root);
	    });
        destroyTimeline.getKeyFrames().add(destroytime);
        destroyTimeline.play();

//      timeline.play();
        
        coinTimeline = new Timeline();
        coinTimeline.setCycleCount(coinTimeline.INDEFINITE);
        KeyFrame cointime = 
        		 new KeyFrame(Duration.seconds(r.nextFloat()+2.5), e -> {
        			 	coinFlag=1;
   	    			coin.generatenewtoken(root);
   	    });
        coinTimeline.getKeyFrames().add(cointime);
        coinTimeline.play();

        blockTimeline = new Timeline(
        	    new KeyFrame(Duration.seconds(2.5), e -> {
        	    	rectStack.getChildren().clear();
        	    	textStack.getChildren().clear();
        	        ChainGenerator();
        	    })
        	);
        blockTimeline.setCycleCount(Animation.INDEFINITE);
        blockTimeline.play();

        wallTimeline = new Timeline(
        	    new KeyFrame(Duration.seconds(2.5), e -> {
        	        wall.create(root);
        	    })
        	);
        wallTimeline.setCycleCount(Animation.INDEFINITE);
        wallTimeline.play();
        
        
        magnetPowerTimeline = new Timeline(
        	    new KeyFrame(Duration.seconds(1
        	    		), e -> {
        	    	if(magnetSeconds>0) 
        	    	{
        	    		magnetTimeText.setText(Integer.toString(magnetSeconds--));
        	    	}
	      			if(root.getChildren().contains(coin.getImageView())&& magnetSeconds>0)
	    			{
	    				root.getChildren().removeAll(coin.getC(),coin.getImageView());
	    				coinScore=coinScore+1;
	    				coinScoreText.setText(Integer.toString(coinScore));
	    				burstAnimation((int)coin.getC().getTranslateX(),(int)coin.getC().getTranslateY(),2);
	    				//burstAnimation(snake.getTranslateX()+175,350,1);
	    			}
	      			if((!root.getChildren().contains(magnetTimeText)) && magnetSeconds>0) {
	      				root.getChildren().add(magnetTimeText);
	      			}
	      			if(magnetSeconds<=0) {
	      				root.getChildren().remove(magnetTimeText);
	      			}
        	    })
        	);
        magnetPowerTimeline.setCycleCount(Animation.INDEFINITE);
        if(magnetSeconds >0) {
        	magnetPowerTimeline.play();
        }
        
        shieldPowerTimeline = new Timeline(
        	    new KeyFrame(Duration.seconds(1
        	    		), e -> {
        	    	if(shieldSeconds>0)
        	    	{	
        	    		shieldPower=1;
        	    		shieldTimeText.setText(Integer.toString(shieldSeconds--));
        	    	}
        	    	else{
	      				shieldPower=0;
	      				root.getChildren().remove(shieldTimeText);
	      			}
        	    	if((!root.getChildren().contains(shieldTimeText)) && shieldSeconds>0) {
	      				root.getChildren().add(shieldTimeText);
	      			}

//	      			else {
//	      				System.out.println("off"+shieldPower);
//	      			}
        	    })
        	);
        shieldPowerTimeline.setCycleCount(Animation.INDEFINITE);
        if(shieldSeconds >0) {
        	shieldPowerTimeline.play();
        }

        return root;
    }
	
	private void pause() {
		timer.stop();
//		timeline.pause();
		blockTimeline.pause();
		wallTimeline.pause();
		coinTimeline.pause();
		magnetPowerTimeline.pause();
		shieldPowerTimeline.pause();
		magnetTimeline.pause();
		shieldTimeline.pause();
		ballTimeline.pause();
		destroyTimeline.pause();
		
		Pane pausePane=new Pane();
		Button restartButton=new Button("Restart");
		pausePane.getChildren().add(restartButton);
		
		Scene pause=new Scene(pausePane, 360, 640);
		
		Stage currentStage=(Stage) root.getScene().getWindow();
		Scene previousScene=currentStage.getScene();
		currentStage.setScene(pause);
		restartButton.setOnAction(e ->{
			timer.start();
//			timeline.play();
			blockTimeline.play();
			wallTimeline.play();
			coinTimeline.play();
			shieldPowerTimeline.play();
			magnetPowerTimeline.play();
			magnetTimeline.play();
			shieldTimeline.play();
			ballTimeline.play();
			destroyTimeline.play();
			
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

		if(probability<0.25) {
			while(howManyToDel>0) {
				int index=ran.nextInt(constituentRectanglesList.size()-1)+1;

				if(row.get(index).getValue()>snake.getLength()) {
					constituentRectanglesList.remove(index);
					constituentValList.remove(index);
					row.remove(index);
				}
				howManyToDel--;
			}
		}

		for (Rectangle rect: constituentRectanglesList)
		{
			int index=constituentRectanglesList.indexOf(rect);

			Text val = constituentValList.get(index);
			val.setX(rect.getX()+36);
			val.setY(-16);
			val.setFill(Color.BLACK);
			val.setFont(Font.font ("Verdana", 15));
			rectStack.getChildren().addAll(rect);
			textStack.getChildren().addAll(val);
		}

		if(!( root.getChildren().contains(rectStack) || root.getChildren().contains(textStack) )) {
			root.getChildren().addAll(rectStack, textStack);
		}

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
					if(shieldPower==0) {
						snake.getTrail().getTailtrail().get(i-1).setVisible(false);
						snake.setLength(snake.getLength()-1);
					}
					score=score+1;
					scoreText.setText(Integer.toString(score));
				}
			}
			else {
			
				PauseTransition pause = new PauseTransition(Duration.millis(100));
				pause.setOnFinished(event -> {
					if(snake.getLength()>0) {
						if(shieldPower==0) {
							snake.getTrail().getTailtrail().get(snake.getLength()-1).setVisible(false);
							snake.setLength(snake.getLength()-1);
						}
						score=score+1;
						snake.updatemovement(root);
						scoreText.setText(Integer.toString(score));
					}
					else if(gameOverFlag==0){
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

	private void burstAnimation(int initialX,int initialY,int color) {
		//snake.getTranslateX()+175,350, 7, Color.WHITE
		Random r= new Random();
		//burst animation
		Node[] n=new Node[20];
			for(int i=1;i<8;i++) {
			Circle c1=new Circle();
			if(color==1)
				c1=new Circle(initialX,initialY, 7, Color.WHITE);
			else
				c1=new Circle(initialX,initialY,7,Color.YELLOW);
			//System.out.println(snake.getTranslateX()+175);
			root.getChildren().add(c1);
			n[i]=c1;
			TranslateTransition t= new TranslateTransition();
			int sec=r.nextInt(2);
			ScaleTransition s=new ScaleTransition(Duration.millis(200), c1);
		    s.setByX(-1);
		    s.setByY(-1);
	    	t.setDuration(Duration.millis(200));
	    	switch (i) {
			case 1:
				t.setToX(0);
	    		t.setToY(-100);
				break;
			case 2:
				t.setToX(50);
	    		t.setToY(-50);
				break;
			case 3:
				t.setToX(100);
	    		t.setToY(0);
				break;
			case 4:
				t.setToX(50);
	    		t.setToY(50);
				break;
			case 5:
				t.setToX(0);
	    		t.setToY(100);
				break;
			case 6:
				t.setToX(-50);
	    		t.setToY(-50);
				break;
			case 7:
				t.setToX(-100);
	    		t.setToY(0);
				break;
			case 8:
				t.setToX(-50);
	    		t.setToY(50);
				break;
			default:
				break;
			}
	    	t.setNode(c1);
	    	t.play();
	    	s.play();
		}
	}
	
	private void checkState() {
		Node snakeHead=null;
		if(snake.getLength()!=0) {
			snakeHead=snake.getTrail().getTailtrail().get(0);

		for (Block r: row) {
			Node blk=r.getR();
			if(snakeHead!=null)
			if (blk.getBoundsInParent().intersects(snakeHead.getBoundsInParent()) && blockFlag==1 && root.getChildren().contains(rectStack)) {
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
//							timeline.pause();
							blockTimeline.pause();
							wallTimeline.pause();
							coinTimeline.pause();
							magnetPowerTimeline.pause();
							shieldPowerTimeline.pause();
							magnetTimeline.pause();
							shieldTimeline.pause();
							ballTimeline.pause();
							destroyTimeline.pause();

//							for(int i=r.getValue();i>0;i--) {
//								r.getTextValue().setVisible(false);
								
								PauseTransition pause = new PauseTransition(Duration.millis(100));
								pause.setOnFinished(event -> {
									if(snake.getLength()>0) {
										r.setValue(r.getValue()-1);
										if(shieldPower==0)
											burstAnimation(snake.getTranslateX()+175,350,1);
										r.getTextValue().setText(Integer.toString(r.getValue()));
										if(r.getValue()==0 || shieldPower==1) {
											timer.start();
//											timeline.play();
											blockTimeline.play();
											wallTimeline.play();
											coinTimeline.play();
											shieldPowerTimeline.play();
											magnetPowerTimeline.play();
											magnetTimeline.play();
											shieldTimeline.play();
											ballTimeline.pause();
											destroyTimeline.play();
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
							r.getTextValue().setVisible(false);
							collidingFlag=0;
							burstAnimation(snake.getTranslateX()+175,350,1);
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
			//rectStack.getChildren().removeAl();
//			System.out.println(row.size());
			collidingFlag=1;
			root.getChildren().removeAll(destroy.getC(), destroy.getImageView());
			for(int i=0;i<row.size();i++) {
				burstAnimation(i*72, (int)rectStack.getChildren().get(i).getTranslateY() , 1);
				root.getChildren().removeAll(rectStack,textStack);
			}
			
			destroyFlag=0;
			collidingFlag=0;
		}

		if(ball.getC().getBoundsInParent().intersects(snakeHead.getBoundsInParent()) && ballFlag==1) {
//			setSpeed(speed+0.2);
			collidingFlag=1;
			root.getChildren().removeAll(ball.getImageView(),ball.getC(),ball.getValueText());
			updateLength(1,ball.getValue());
			burstAnimation(snake.getTranslateX()+175,350,1);
			ballFlag=0;
			collidingFlag=0;
		}

		if(coin.getC().getBoundsInParent().intersects(snakeHead.getBoundsInParent()) && coinFlag==1) {
			collidingFlag=1;
			if(root.getChildren().contains(coin.getImageView()))
			{
				root.getChildren().removeAll(coin.getC(),coin.getImageView());
				coinScore=coinScore+1;
				coinScoreText.setText(Integer.toString(coinScore));
				burstAnimation(snake.getTranslateX()+175,350,1);
			}
			coinFlag=0;
			collidingFlag=0;
		}

		if(magnet.getC().getBoundsInParent().intersects(snakeHead.getBoundsInParent()) && magnetFlag==1) {
			collidingFlag=1;
			root.getChildren().removeAll(magnet.getC(),magnet.getImageView());	
			magnetSeconds=10;
			if(!root.getChildren().contains(magnetTimeText)) {
				root.getChildren().add(magnetTimeText);
			}
		    magnetPowerTimeline.play();
		    burstAnimation(snake.getTranslateX()+175,350,1);
			magnetFlag=0;
			collidingFlag=0;
		}

		if(shield.getC().getBoundsInParent().intersects(snakeHead.getBoundsInParent()) && shieldFlag==1) {
			collidingFlag=1;
			root.getChildren().removeAll(shield.getC(), shield.getImageView());
			shieldPower=1;
			shieldSeconds=15;
			if(!root.getChildren().contains(shieldTimeText))
				root.getChildren().add(shieldTimeText);
			shieldPowerTimeline.play();
			burstAnimation(snake.getTranslateX()+175,350,1);
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
		gameOverFlag=1;
		timer.stop();
//        timeline.stop();
        blockTimeline.stop();
        wallTimeline.stop();
        coinTimeline.stop();
		magnetPowerTimeline.stop();
        shieldPowerTimeline.stop();
        magnetTimeline.stop();
        shieldTimeline.stop();
        destroyTimeline.stop();
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

	private static void setSpeed(double s) {
		speed=s;
	}

	private Group setDiffButtons(Stage stage){
		Button easyButton=new Button("Easy");
		easyButton.setLayoutX(140);
		easyButton.setLayoutY(260);

		Button mediumButton=new Button("Medium");
		mediumButton.setLayoutX(140);
		mediumButton.setLayoutY(300);

		Button hardButton=new Button("Hard");
		hardButton.setLayoutX(140);
		hardButton.setLayoutY(340);

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
				setSpeed(3);
				try {
					stage.setScene(new Scene(createContent(stage)));
					stage.getScene().setOnMouseMoved(mouseHandler);
				} catch (FileNotFoundException e4) {
					e4.printStackTrace();
				}
//					System.out.println(speed);
			});

			diffMenuButtons.getChildren().get(1).setOnMouseClicked(e2 -> {
				setSpeed(4.5);
				try {
					stage.setScene(new Scene(createContent(stage)));
					stage.getScene().setOnMouseMoved(mouseHandler);
				} catch (FileNotFoundException e4) {
					e4.printStackTrace();
				}
//					System.out.println(speed);
			});

			diffMenuButtons.getChildren().get(2).setOnMouseClicked(e3 -> {
				setSpeed(5);
				try {
					stage.setScene(new Scene(createContent(stage)));
					stage.getScene().setOnMouseMoved(mouseHandler);
				} catch (FileNotFoundException e4) {
					e4.printStackTrace();
				}
//					System.out.println(speed);
			});
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
		
		stage.setOnHiding(new EventHandler<WindowEvent>() {
	         @Override
	         public void handle(WindowEvent event) {
	             Platform.runLater(new Runnable() {

	                 @Override
	                 public void run() {
	                     System.out.println("Application Closed by click to Close Button(X)");
	                     deserialise();
	                     System.exit(0);
	                 }
	             });
	         }
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
	public static void deserialise() {
		System.out.println("Saving Game State...");
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream("db.txt"));
			out.writeObject(snake.getLength());
			System.out.println(score);
			out.writeObject(score);
			out.writeObject(coinScore);
			out.writeObject(magnetSeconds);
			out.writeObject(shieldSeconds);
			out.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void serialise(Pane root) {

		System.out.println("Fetching Database...");
		ObjectInputStream in=null;
		try {
			in = new ObjectInputStream(new FileInputStream("db.txt"));
			int length=(int)in.readObject();
			if(length==0) {
				System.out.println("New Game");
				snake= new Snake(root,4);
				score=0;
				coinScore=0;
				magnetSeconds=0;
				shieldSeconds=0;
			}
			else {
				System.out.println("Continuing Game");
				snake= new Snake(root,length);
				score=(int)in.readObject();
				coinScore=(int)in.readObject();
				magnetSeconds=(int)in.readObject();
				shieldSeconds=(int)in.readObject();
			}
		}
		catch(Exception e){
			//System.out.println(e.getClass().getName());
			if(e.getClass().getName()=="java.io.FileNotFoundException") {
				File tmp = new File("./","db.txt");
				try {
					tmp.createNewFile();
				}
				catch(Exception e2) {
					System.out.println(e.getMessage());
					e2.getStackTrace();
				}
				snake= new Snake(root,4);
				score=0;
				coinScore=0;
				magnetSeconds=0;
				shieldSeconds=0;
			}
			else {
				System.out.println(e.getMessage());
			}
		}
		finally {
			try {
				in.close();
			} catch (Exception e2) {
				//e2.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
        launch(args);
    }
}
