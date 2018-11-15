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
	private int initialPosX, initialPosY;

	public Trail(Pane root,int length, int initialPosX, int initialPosY,Text snakelength) {
		snakeBody = new Group();
		this.initialPosX=initialPosX;
		this.initialPosY=initialPosY;
		snakelength.setX(initialPosX);
		snakelength.setY(initialPosY-20);
		root.getChildren().add(snakelength);
		updateTrail(root, length);
	}

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
		root.getChildren().addAll(snakeBody);
	}

}
