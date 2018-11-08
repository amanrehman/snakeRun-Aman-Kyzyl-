import java.io.FileInputStream; 
import java.io.FileNotFoundException;
import java.util.Random;
import javafx.application.Application; 
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;  
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
public class Coin{	
	Random r=new Random();
	Image image;
	Group img;
	ImageView imageView;
	Circle c;
		
	private int positionX;
	private int positionY;
		
	int frequency;
	
	
    Coin() throws FileNotFoundException{
    	image = new Image(new FileInputStream(".\\coin.png"));
    	imageView = new ImageView(image);
    	c = new Circle(0,0,20,Color.rgb(0, 0, 0, 0));
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
    public void generatenewtoken(Pane root) {
    	this.positionX=(r.nextInt(320)+20);
    	this.positionY=0;
    	imageView.setX(r.nextInt(320)+20); 
        imageView.setY(0);
        c.setTranslateX(imageView.getX()-165);
        c.setTranslateY(imageView.getY()+10);
        root.getChildren().addAll(imageView,c);
    }
    public void updatemovement(Pane root) {
    	if(imageView!=null) 
    	{
			if(imageView.getY()<640) {
				this.positionX=(int) imageView.getX();
		    	this.positionY=(int) (imageView.getY()+5);
				imageView.setX(imageView.getX()); 
				imageView.setY(imageView.getY()+5);
				c.setTranslateX(imageView.getX());
		        c.setTranslateY(imageView.getY());
			}
			else {
				root.getChildren().removeAll(imageView,c);
			}
		}
	}
}
