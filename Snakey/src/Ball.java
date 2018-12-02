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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
public class Ball extends Token implements Serializable{	
	private int value;
	private Text valueText;
    public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public Text getValueText() {
		return valueText;
	}
	public void setValueText(Text valueText) {
		this.valueText = valueText;
	}
	Ball() throws FileNotFoundException{
		valueText=new Text(Integer.toString(0));
		value=generatenevalue();
		valueText.setFill(Color.BLACK);
		valueText.setFont(Font.font ("Verdana", 15));
    	setImage( new Image(new FileInputStream(".\\ball.png")));
    	setImageView(new ImageView(getImage()));
    	setC(new Circle(15,15,15,Color.rgb(0, 0, 0, 0)));
    }
	public int generatenevalue() {
		Random r=new Random();
		value= r.nextInt(8)+1;
		valueText.setText(Integer.toString(value));
		return value;
	}
	public void generatenewvaluetext(Pane root, int posX, int posY) {
		root.getChildren().add(valueText);	
		updatemovementText(root, posX, posY);
	}
	void updatemovementText(Pane root,int posX,int posY){
		valueText.setTranslateX(posX+15);
		valueText.setTranslateY(posY+20);
		if(posY>639)
			root.getChildren().remove(valueText);
	}
}
