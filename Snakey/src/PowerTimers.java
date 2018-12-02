import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class PowerTimers {
	private boolean isRunning=true;

	public boolean isShieldPowerRunning(int shieldPower) {
		if(shieldPower==1) isRunning=true;
		else isRunning=false;
		
		return isRunning;
	}

	public PauseTransition createTimer() {
//		Timeline t=new Timeline();
//        t.setCycleCount(t.INDEFINITE);
//		return t;

		PauseTransition wait = new PauseTransition(Duration.seconds(15));
		wait.setOnFinished(e -> {
			
//		        wait.playFromStart();
		});
		return wait;
	}
}