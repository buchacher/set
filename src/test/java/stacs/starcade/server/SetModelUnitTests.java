package stacs.starcade.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.*;

import stacs.starcade.*;
import stacs.starcade.impl.server.*;
import stacs.starcade.ISetModel;
import stacs.starcade.ITimer;

public class SetModelUnitTests {
    ISetModel model;
    List<ICard> cards;
    ITimer timer;
    final int SECONDS_ELAPSED = 7000;
    final String PLAYER_NAME = "testPlayerName";

    @BeforeEach
    public void setup() {
        timer = Mockito.mock(ITimer.class);
        Mockito.when(timer.getSecondsElapsed()).thenReturn(SECONDS_ELAPSED);
        model = new SetModel(PLAYER_NAME);
        model.setTimer(timer);
    }

    // toggling out of bounds card results in IllegalArgumentException
    @Test
    public void toggleOutOfBoundsCardGivesException() {
        assertThrows(IllegalArgumentException.class, () -> {
            model.toggleCard(12);
        });
    }

    // getting the playerName results in the correct name
    @Test
    public void playerNameIsCorrect() {
        assertEquals(PLAYER_NAME, model.getPlayerName());
    }

    // getting the time results in the correct time
    @Test
    public void correctTimeIsReported() {
        assertEquals(SECONDS_ELAPSED, model.getSecondsElapsed());
    }
}
