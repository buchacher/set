package stacs.starcade;

/**
 * Defines the behaviour of a generatic timer with units in seconds.
 */
public interface ITimer {

    /**
     * @return The seconds that have elapsed since this timer was created.
     */
    public int getSecondsElapsed();

}
