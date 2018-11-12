import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
public class Trail{
	ObservableList<Node> tailtrail;
	Group snakeBody;
	int initialPosX, initialPosY;
	public Trail(Pane root,int length, int initialPosX, int initialPosY,Text snakelength) {
		snakeBody = new Group();
		this.initialPosX=initialPosX;
		this.initialPosY=initialPosY;
		snakelength.setX(initialPosX);
		snakelength.setY(initialPosY-20);
		root.getChildren().add(snakelength);
		updateTrail(root, length);
	}
	public void updateTrail(Pane root, int length) {
		tailtrail = snakeBody.getChildren();
		for(int i=0;i<length;i++) {
			tailtrail.add(new Circle(initialPosX,initialPosY+(i*20),10,Color.WHITESMOKE));
		}
		root.getChildren().addAll(snakeBody);
	}

}
