import java.util.Random;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView; 

abstract class Token {
	Random r=new Random();
	Image image;
	Group img;
	ImageView imageView;
	Circle c;
	
	private int positionX;
	private int positionY;
	
	int frequency;
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
    public void generatenewtoken(Pane root) {
    	this.positionX=(r.nextInt(320)+20);
    	this.positionY=0;
    	imageView.setX(r.nextInt(320)+20); 
        imageView.setY(0);
        c.setTranslateX(imageView.getX()-165);
        c.setTranslateY(imageView.getY()+10);
        root.getChildren().addAll(imageView,c);
    }
    public void updatemovement(Pane root, int speed) {
    	if(imageView!=null) 
    	{
			if(imageView.getY()<640) {
				this.positionX=(int) imageView.getX();
		    	this.positionY=(int) (imageView.getY()+speed);
				imageView.setX(imageView.getX()); 
				imageView.setY(imageView.getY()+speed);
				c.setTranslateX(imageView.getX());
		        c.setTranslateY(imageView.getY());
			}
			else {
				root.getChildren().removeAll(imageView,c);
			}
		}
	}
    
}
