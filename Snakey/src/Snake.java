import java.io.Serializable;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Snake implements Serializable{
	private static int length;
	private Trail trail;
	private int speed;
	private Text snakelength;

	public static int getLength() {
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

	public Text getSnakelength() {
		return snakelength;
	}

	public void setSnakelength(Text snakelength) {
		this.snakelength = snakelength;
	}

	public Snake(Pane root) {
		length=4;
//		hiddenLength=92;
		snakelength=new Text(Integer.toString(length));
		snakelength.setFill(Color.WHITE);
		snakelength.setFont(Font.font ("Verdana", 15));
		trail=new Trail(root,length,180,360,snakelength);
	}

	public void setTranslateY(int y) {
		trail.getTailtrail().get(0).setTranslateY(y);
	}

	public void setTranslateX(int x) {
		trail.getTailtrail().get(0).setTranslateX(x);
	}

	public int getTranslateY() {
		return (int) trail.getTailtrail().get(0).getTranslateY();
	}

	public int getTranslateX() {
		return (int) trail.getTailtrail().get(0).getTranslateX();
	}

	public void updatemovement(Pane root) {
//		if(!snakelength.getText().equals(Integer.toString(length))) {
//			root.getChildren().remove(snakelength);
//			snakelength=new Text(Integer.toString(length));
//			snakelength.setFill(Color.WHITE);
//			snakelength.setX(180);
//			snakelength.setY(340);
////			System.out.println(trail.getTailtrail().get(0).getTranslateY()-20);
//			root.getChildren().add(snakelength);
//		}
		snakelength.setText(Integer.toString(length));

		if(trail.getTailtrail().size()!=0) snakelength.setX(trail.getTailtrail().get(0).getTranslateX()+175);
		else snakelength=new Text("1");

		for(int i=1;i<100;i++) {
			TranslateTransition t= new TranslateTransition();
        	t.setDuration(Duration.millis(35));
        	t.setToX(trail.getTailtrail().get(i-1).getTranslateX());
        	t.setToY(getTranslateY());
        	t.setNode(trail.getTailtrail().get(i));
        	t.play();
		}	
	}
}
