import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
public class Snake {
	int length;
	Trail trail;
	int speed;
	Text snakelength;
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public Trail getTrail() {
		return trail;
	}
	public void setTrail(Trail trail) {
		this.trail = trail;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public Snake(Pane root) {
		length=4;
		snakelength=new Text(Integer.toString(length));
		snakelength.setFill(Color.WHITE);
		trail=new Trail(root,length,180,360,snakelength);
	}
	public void setTranslateY(int y) {
		trail.tailtrail.get(0).setTranslateY(y);
	}
	public void setTranslateX(int x) {
		trail.tailtrail.get(0).setTranslateX(x);
	}
	public int getTranslateY() {
		return (int) trail.tailtrail.get(0).getTranslateY();
	}
	public int getTranslateX() {
		return (int) trail.tailtrail.get(0).getTranslateX();
	}
	public void updatemovement() {
		snakelength.setX(trail.tailtrail.get(0).getTranslateX()+175);
		for(int i=1;i<length;i++) {
			TranslateTransition t= new TranslateTransition();
        	t.setDuration(Duration.millis(35));
        	t.setToX(trail.tailtrail.get(i-1).getTranslateX());
        	t.setToY(getTranslateY());
        	t.setNode(trail.tailtrail.get(i));
        	t.play();
		}	
	}
}
