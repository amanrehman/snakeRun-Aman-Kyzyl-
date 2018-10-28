import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
public class Snake {
	int length;
	Trail trail;
	int speed;
	public Snake(Pane root) {
		length=7;
		trail=new Trail(root,length,180,360);
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
