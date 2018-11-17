import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
public class Trail{
	private ObservableList<Node> tailtrail;
	private Group snakeBody;
	private Group hiddenSnakeBody;
	private int initialPosX, initialPosY;
	private int actualLength;

	public Trail(Pane root,int length, int initialPosX, int initialPosY,Text snakelength) {
		snakeBody = new Group();
		hiddenSnakeBody=new Group();
//		addToHiddenSnakeBody();
		actualLength=100;
		this.initialPosX=initialPosX;
		this.initialPosY=initialPosY;
		snakelength.setX(initialPosX);
		snakelength.setY(initialPosY-20);
		root.getChildren().add(snakelength);
		updateTrail(root, length);
		root.getChildren().addAll(snakeBody);
	}

////	private void addToHiddenSnakeBody() {
//		for(int i=0;i<Snake.hiddenLength;i++)
//			hiddenSnakeBody.getChildren().add(new Circle(initialPosX,initialPosY+(i*20),10,Color.WHITESMOKE));
//	}
	public ObservableList<Node> getTailtrail() {
		return tailtrail;
	}

	public void setTailtrail(ObservableList<Node> tailtrail) {
		this.tailtrail = tailtrail;
	}

	public Group getSnakeBody() {
		return snakeBody;
	}

	public void setSnakeBody(Group snakeBody) {
		this.snakeBody = snakeBody;
	}

	public void updateTrail(Pane root, int length) {
		tailtrail = snakeBody.getChildren();
		for(int i=0;i<length;i++) {
			tailtrail.add(new Circle(initialPosX,initialPosY+(i*20),10,Color.WHITESMOKE));
		}
		for(int i=length;i<actualLength;i++) {
			tailtrail.add(new Circle(initialPosX,initialPosY+(i*20),10,Color.WHITESMOKE));
			tailtrail.get(i).setVisible(false);
		}
//		root.getChildren().addAll(snakeBody);
	}

}
