package stacs.starcade.impl.client.Managers;

import javafx.application.Platform;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.control.Label;
import stacs.starcade.impl.client.Interfaces.ITimeManager;

/**
 * The Class TimeManager.
 */
public class TimeManager implements ITimeManager {

    /** The timer. */
    Timer timer = new Timer();
    
    /** The task. */
    TimerTask task;
    
    /** The timer running. */
    private boolean timerRunning = false;
    
    /** The seconds to wait. */
    private int secondsToWait = 0;


    /**
     * Start timer.
     *
     * @param lbl_timer the lbl timer
     * @param secondsElapsed the seconds elapsed
     */
    public void startTimer(Label lbl_timer, int secondsElapsed) {
        this.secondsToWait = secondsElapsed;
        timerRunning = true;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {

                    int minutes = (secondsToWait % 3600) / 60;
                    int seconds = secondsToWait % 60;

                    String strSeconds = seconds < 10 ? "0" + seconds : seconds + "";
                    String strMinutes = minutes < 10 ? "0" + minutes : minutes + "";

                    secondsToWait++;
                    lbl_timer.setText(strMinutes + ":" + strSeconds);
                });
            }
        };
        int minutes = (secondsToWait % 3600) / 60;
        int seconds = secondsToWait % 60;
        String strSeconds = seconds < 10 ? "0" + seconds : seconds + "";
        String strMinutes = minutes < 10 ? "0" + minutes : minutes + "";
        lbl_timer.setText(strMinutes + ":" + strSeconds);
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    /**
     * Stop timer.
     */
    public void stopTimer() {
        if(timerRunning) {
            timerRunning = false;
            timer.cancel();
            timer.purge();
            timer = new Timer();
        }

        secondsToWait = 0;
    }
}
