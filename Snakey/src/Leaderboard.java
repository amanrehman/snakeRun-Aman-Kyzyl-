import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;

public class Leaderboard implements Serializable{

	public TableView createTable() {
		TableView table = new TableView();
        table.setEditable(false);
        
        TableColumn Date = new TableColumn("Date");
        TableColumn Score = new TableColumn("Score");

        final ObservableList<Object> data=FXCollections.observableArrayList();
        
        System.out.println("Fetching Database...");
		ObjectInputStream in=null;
		try {
			in = new ObjectInputStream(new FileInputStream("ldbdb.txt"));
			String nextObject=(String)in.readObject();
			while(nextObject!=null) {
				String todaydate =(String)nextObject;
				Integer score=(Integer)in.readObject();
				data.add(todaydate);
				data.add(score);
				System.out.println((String)nextObject);
				Integer nextI=(Integer)in.readObject();
				System.out.println(nextI);
			}			
		}
		catch(Exception e){
			if(e.getClass().getName()=="java.io.FileNotFoundException") {
				File tmp = new File("./","ldbdb.txt");
				try {
					tmp.createNewFile();
				}
				catch(Exception e2) {
					System.out.println(e.getMessage());
					e2.getStackTrace();
				}
				
			}
			else {
				System.out.println(e.getMessage());
			}
		}
		finally {
			try {
				in.close();
			} catch (Exception e2) {
				//e2.printStackTrace();
			}
		}
        
        table.getColumns().addAll(Date, Score);

        table.setItems(data);
        return table;

	}
}
