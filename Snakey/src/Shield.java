import java.io.File;
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
public class Shield extends Token{	
    Shield() throws FileNotFoundException{
    	image= new Image(new FileInputStream(".\\shield.png"));
    	imageView = new ImageView(image);
    	c=new Circle(0,0,20,Color.rgb(0, 0, 0, 0));
    }
}
