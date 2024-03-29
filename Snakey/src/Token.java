import java.io.Serializable;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView; 

abstract class Token implements Serializable{
	private Image image;
	private Group img;
	private ImageView imageView;
	private Circle c;
	
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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Group getImg() {
		return img;
	}

	public void setImg(Group img) {
		this.img = img;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	public Circle getC() {
		return c;
	}

	public void setC(Circle c) {
		this.c = c;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public void generatenewtoken(Pane root) {
		Random r=new Random();
    	this.positionX=(r.nextInt(320)+20);
    	this.positionY=0;
    	imageView.setX(positionX); 
        imageView.setY(positionY);
        c.setTranslateX(imageView.getX()-165);
        c.setTranslateY(imageView.getY()+10);
        
        if(!( (root.getChildren().contains(imageView) || root.getChildren().contains(c)) ))
        	root.getChildren().addAll(imageView,c);
    }
    public void updatemovement(Pane root, double speed) {
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
