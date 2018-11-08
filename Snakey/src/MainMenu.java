import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainMenu {
	private Pane layout= new Pane();

	public Button createStartButton() {
		Button startButton= new Button("Start game");
		startButton.setLayoutX(140);
		startButton.setLayoutY(300);
		return startButton;
	}
	
	public Button createLeadButton() {
		Button leadButton=new Button("Leaderboard");
		leadButton.setLayoutX(135);
		leadButton.setLayoutY(340);
		return leadButton;
	}
	
	public Button createPauseButton() {
		Button pauseButton=new Button("||");
		return pauseButton;
	}
}
