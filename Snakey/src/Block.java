import java.io.Serializable;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Block extends Rectangle implements Serializable{
	static Random ran=new Random();
	private Rectangle r;
	private int value;
	private Text textValue;
	private int positionX;
	private Snake s;

	public Block() {
		r=new Rectangle();
		r.setArcWidth(30.0); 
		r.setArcHeight(20.0);
		value = generateValue(ran.nextInt(2));
//		textValue=new Text(Integer.toString(value));
//		textValue.setFont(Font.font ("Verdana", 20));
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

	private int generateValue(int v) {
		int min = 1;
		int max = 25;
		int snakeLen=Snake.getLength();

		if(v==0) value = ran.nextInt(max-min+1)+min;
		else if(v!=0 && snakeLen>2) value = ran.nextInt((snakeLen-2)-min+1)+min;
		else value=1;
		setValue(value);
		textValue=new Text(Integer.toString(value));
		return value;
	}

	public Rectangle generateBlock() {
		Rectangle block;

		generateValue(ran.nextInt(3));
		if(value>=1 && value<=5) block = new Rectangle(75, 75, Color.AQUA);
		else if(value>=6 && value<=10) block = new Rectangle(75, 75, Color.MEDIUMSPRINGGREEN);
		else if(value>=11 && value<=15) block = new Rectangle(75, 75, Color.LAWNGREEN);
		else if(value>=16 && value<=20) block = new Rectangle(75, 75, Color.GREEN);
		else if(value>=21 && value<=25) block = new Rectangle(75, 75, Color.ORANGE);
		else if(value>=26 && value<=30) block = new Rectangle(75, 75, Color.YELLOW);
		else if(value>=31 && value<=35) block = new Rectangle(75, 75, Color.HOTPINK);
		else block = new Rectangle(75, 75, Color.RED);

		block.setArcWidth(60.0); 
		block.setArcHeight(80.0);
		return block;
	}

	public Block newBlock() {
		Block b= new Block();
		return new Block(b.generateBlock(), getValue());
	}
				
//	public static void main(String[] args) {
//		Block b=newBlock();
//
//		System.out.println(b.value);
//		System.out.println(b.r);
//		System.out.println(b.positionX);
//	}
}