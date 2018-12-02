import java.io.FileInputStream; 
import java.io.FileNotFoundException;
import java.io.Serializable;
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
public class Destroy extends Token  implements Serializable{	
    Destroy() throws FileNotFoundException{
    	setImage(new Image(new FileInputStream(".\\hammer.png")));
    	setImageView(new ImageView(getImage()));
    	setC(new Circle(15,15,15,Color.rgb(0, 0, 0, 0)));
    }
}