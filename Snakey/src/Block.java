import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block extends Rectangle{
	private Rectangle r;
	private static int value;
	private int positionX;

	public Block() {
		r=new Rectangle();
		r.setArcWidth(30.0); 
		r.setArcHeight(20.0);
		value = 1;
		positionX=-1;
	}
		
	public Block(Rectangle rec, int val) {
		r=rec;
		r.setArcWidth(30.0); 
		r.setArcHeight(20.0);
		value = val;
		positionX=-1;
	}

	public Rectangle getR() {
		return r;
	}

	public void setR(Rectangle r) {
		this.r=r;
	}

	public static int getValue() {
		return value;
	}

	public static void setValue(int value) {
		Block.value = value;
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	private static int generateValue() {
		Random r=new Random();
		value=(int) r.nextInt(10);

		return value;
	}

	public static Rectangle generateBlock() {
		Rectangle block = new Rectangle(75, 75, Color.web("#EDF060"));
		block.setArcWidth(40.0); 
		block.setArcHeight(30.0);
		return block;
	}

	public static Block newBlock() {
		return new Block(generateBlock(), generateValue());
	}
				
//	public static void main(String[] args) {
//		Block b=newBlock();
//
//		System.out.println(b.value);
//		System.out.println(b.r);
//		System.out.println(b.positionX);
//	}
}