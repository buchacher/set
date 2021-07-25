package stacs.starcade.impl.server;

import java.time.Instant;
import java.time.Duration;

import stacs.starcade.*;

/**
 * Implements a timer with units in seconds.
 */
public class Timer implements ITimer {

    Instant start;

    public Timer() {
        this.start = Instant.now();
    }

    /**
     * Returns the seconds elapsed since this timer was created.
     */
    @Override
    public int getSecondsElapsed() {
        Instant now = Instant.now(); 
        return (int) Duration.between(start, now).toSeconds();
    }
    
}
