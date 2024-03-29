import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.Serializable;
import java.util.Random;

import javafx.application.Application;

public class Wall implements Serializable{
	Rectangle rec;
	private int positionX;
	private int positionY;
	
	public Wall() {
		rec=new Rectangle();
		rec.setArcWidth(40.0); 
		rec.setArcHeight(30.0);
	}

	public Rectangle getRec() {
		return rec;
	}

	public void setRec(Rectangle rec) {
		this.rec = rec;
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public void create(Pane root) {
		Random r=new Random();
		rec=new Rectangle(5,r.nextInt(200)+20, Color.WHITE);
		setPositionX(0);
		rec.setX((r.nextInt(4)+1)*72);
		rec.setY(80);
		rec.setArcWidth(20.0); 
		rec.setArcHeight(20.0);

		root.getChildren().add(rec);
	}
	public void updatemovent(Pane root, double speed) {
		if(rec!=null) 
    	{
			if(rec.getTranslateY()<640) {
				rec.setTranslateX(rec.getTranslateX()); 
				rec.setTranslateY(rec.getTranslateY()+speed);
			}
			else {
				root.getChildren().remove(rec);
			}
		}
	}

}