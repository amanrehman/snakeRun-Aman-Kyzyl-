import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Chain implements Serializable{
	ArrayList<Block> row=new ArrayList<Block>();
//	Block generator;
	private ArrayList<Integer> positions=new ArrayList<Integer>();
	private static final int BLOCK_POS=72;
	
	public Chain() {
		positions.add(-1);
		for(int i=0;i<5;i++) positions.add(i*BLOCK_POS);
	}

	public void add() {
		positions.add(-1);
		for(int i=0;i<5;i++) positions.add(i*BLOCK_POS);
	}

	public void generatePositionX() {
		Random r=new Random();

		for(int i=0;i<5;i++) {
			int pos=r.nextInt(positions.size()-1)+1;
			row.get(i).getR().setX(positions.get(pos));
			row.get(i).getR().setY(-50);
			positions.remove(pos);
		}
	}

	public Block newBlockGenerator() {
		Block b=new Block();
		return b.newBlock();
	}

	public void flushAndRefreshPositions() {
		positions.clear();
		add();
	}

	public ArrayList<Block> generateRow() {
		row.clear();
		for(int i=0;i<5;i++) row.add(newBlockGenerator());
//		Chain c=new Chain();
		generatePositionX();
		
		flushAndRefreshPositions();

		return row;
	}

	
	public void generator() {
		generateRow();
	}

//	public static void main(String[] args) {
//		Chain c=new Chain();
//		c.flushAndRefreshPositions();
//	}
}