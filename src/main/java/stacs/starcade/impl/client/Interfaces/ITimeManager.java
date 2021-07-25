package stacs.starcade.impl.client.Interfaces;

import javafx.scene.control.Label;

/**
 * The Interface ITimeManager.
 */
public interface ITimeManager {

    /**
     * Start timer.
     *
     * @param lbl_timer the lbl timer
     * @param secondsElapsed the seconds elapsed
     */
    void startTimer(Label lbl_timer, int secondsElapsed);
    
    /**
     * Stop timer.
     */
    void stopTimer();
}
