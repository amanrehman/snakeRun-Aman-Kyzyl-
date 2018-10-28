import java.util.ArrayList;
import java.util.Random;

public class Chain {
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
		//new Chain();
		Random r=new Random();

		for(int i=0;i<5;i++) {
			int pos=r.nextInt(positions.size()-1)+1;
			
			row.get(i).setPositionX(positions.get(pos));
			row.get(i).getR().setTranslateX(row.get(i).getPositionX());
			row.get(i).getR().setTranslateY(0);
//			System.out.println(positions.get(pos)+" "+positions.size());
			positions.remove(pos);

//				if(flag[i]!=1) {
//				row[i].setPositionX(positionArray[pos]);
//				row[i].getR().setTranslateX(row[i].getPositionX());
//				row[i].getR().setTranslateY(0);
//				System.out.println(i+" "+pos+" "+positionArray[pos]);

//			}
//			flag[pos]=1;

		//	if(positions.isEmpty()) break;
		}
	}

	public Block newBlockGenerator() {
		return Block.newBlock();
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