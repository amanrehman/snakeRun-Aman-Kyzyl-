import java.io.Serializable;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Block extends Rectangle implements Serializable{
	private Rectangle r;
	private int value;
	private Text textValue;
	private int positionX;

	public Block() {
		r=new Rectangle();
		r.setArcWidth(30.0); 
		r.setArcHeight(20.0);
		textValue=new Text("1");
		textValue.setFont(Font.font ("Verdana", 20));
		value = 1;
		positionX=-1;
	}
		
	public Block(Rectangle rec, int val) {
		r=rec;
		r.setArcWidth(30.0); 
		r.setArcHeight(20.0);
		value = val;
		convertToText();
		positionX=-1;
	}

	public Rectangle getR() {
		return r;
	}

	public void setR(Rectangle r) {
		this.r=r;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public Text getTextValue() {
		return textValue;
	}

	public void setTextValue(Text textValue) {
		this.textValue = textValue;
	}

	private void convertToText() {
		textValue=new Text(Integer.toString(value));
	}
	private int generateValue() {
		Random r=new Random();
		value=(int) r.nextInt(10);

		return value;
	}

	public Rectangle generateBlock() {
		Rectangle block = new Rectangle(75, 75, Color.web("#EDF060"));
		block.setArcWidth(40.0); 
		block.setArcHeight(30.0);
		return block;
	}

	public static Block newBlock() {
		Block b= new Block();
		return new Block(b.generateBlock(), b.generateValue());
	}
				
//	public static void main(String[] args) {
//		Block b=newBlock();
//
//		System.out.println(b.value);
//		System.out.println(b.r);
//		System.out.println(b.positionX);
//	}
}