import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Leaderboard {

	public TableView createTable() {
		TableView table = new TableView();
        table.setEditable(false);
        
        TableColumn firstNameCol = new TableColumn("Name");
        TableColumn lastNameCol = new TableColumn("Score");

        table.getColumns().addAll(firstNameCol, lastNameCol);
        
        return table;

	}
}
