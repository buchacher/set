package stacs.starcade.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.*;

import stacs.starcade.*;
import stacs.starcade.impl.server.*;
import stacs.starcade.ISetModel;
import stacs.starcade.ITimer;

public class ModelIntegrationTests {

    ISetModel model;
    List<ICard> cards;
    ITimer timer;
    final int SECONDS_ELAPSED = 7000;
    final String PLAYER_NAME = "testPlayerName";

    @BeforeEach
    public void setup() {
        model = new SetModel(PLAYER_NAME);

        timer = Mockito.mock(Timer.class);
        Mockito.when(timer.getSecondsElapsed()).thenReturn(SECONDS_ELAPSED);
        model.setTimer(timer);
        model.setCards(generateCards());
        model.setMaxSets(1);
    }
    
    // toggling the first card once makes it 'selected'
    @Test
    public void toggleCardOnceMakesItSelected() {
        model.toggleCard(1);
        assertEquals(Card.Status.SELECTED, model.getCard(1).getStatus());
    }

    // toggling the first card twice makes it 'unselected'
    @Test
    public void toggleCardTwiceMakesItUnselected() {
        model.toggleCard(1);
        model.toggleCard(1);
        assertEquals(Card.Status.UNSELECTED, model.getCard(1).getStatus());
    }

    // toggling the first card thrice makes it 'selected'
    @Test
    public void toggleCardThriceMakesItSelected() {
        model.toggleCard(1);
        model.toggleCard(1);
        model.toggleCard(1);
        assertEquals(Card.Status.SELECTED, model.getCard(1).getStatus());
    }

    // selecting an invalid set unselects all three cards within that invalid set
    @Test
    public void selectingInvalidSetUnselectsAllThreeCards() {
        model.toggleCard(0);
        model.toggleCard(1);
        model.toggleCard(2);
        assertEquals(Card.Status.UNSELECTED, model.getCard(0).getStatus());
        assertEquals(Card.Status.UNSELECTED, model.getCard(1).getStatus());
        assertEquals(Card.Status.UNSELECTED, model.getCard(2).getStatus());
    }

    // selecting a valid set results in all three cards within that
    // valid set being set to 'blocked'
    @Test
    public void selectingValidSetRestulsInAllThreeCardsBeingBlocked() {
        model.toggleCard(1);
        model.toggleCard(2);
        model.toggleCard(3);
        assertEquals(Card.Status.BLOCKED, model.getCard(1).getStatus());
        assertEquals(Card.Status.BLOCKED, model.getCard(2).getStatus());
        assertEquals(Card.Status.BLOCKED, model.getCard(3).getStatus());
    }

    // selecting a valid set results in all three cards within that
    // valid set being set to 'blocked'
    @Test
    public void togglingBlockedCardGivesException() {
        model.toggleCard(1);
        model.toggleCard(2);
        model.toggleCard(3);
        assertThrows(IllegalArgumentException.class, () -> {
            model.toggleCard(1);
        });
    }

    // unselecting a set makes all constituent cards 'unselected'
    @Test
    public void unselectingSetMakesAllConstituentCardsUnselected() {
        model.toggleCard(1);
        model.toggleCard(2);
        model.toggleCard(3);

        model.unselectSet(0);

        assertEquals(Card.Status.UNSELECTED, model.getCard(1).getStatus());
        assertEquals(Card.Status.UNSELECTED, model.getCard(2).getStatus());
        assertEquals(Card.Status.UNSELECTED, model.getCard(3).getStatus());;
    }

    // selecting the max number of valid sets results in a returned game model that is "finished"
    @Test
    public void selectingMaxNumValidSetsFinishesGame() {
        // set one of two
        model.toggleCard(1);
        model.toggleCard(2);
        model.toggleCard(3);
        
        assertTrue(model.isFinished());
    }

    // finishing game increments the size of the league table entries list
    @Test
    public void selectingMaxNumValidSetsGrowsLeagueTable() {
        LeagueTable.clearEntries();
        // set one of two
        model.toggleCard(1);
        model.toggleCard(2);
        model.toggleCard(3);
        
        assertEquals(1, LeagueTable.getEntries().size());
    }

    //league table name and time are entered correctly
    @Test
    public void leagueTableEntryRecordedCorrectly() {
        LeagueTable.clearEntries();
        // set one of two
        model.toggleCard(1);
        model.toggleCard(2);
        model.toggleCard(3);
        
        assertEquals(PLAYER_NAME, LeagueTable.getEntries().get(0).getName());
        assertEquals(SECONDS_ELAPSED, LeagueTable.getEntries().get(0).getDuration());
    }
    
    // generates a list of cards with specified properties for testing
    public List<ICard> generateCards() {
        List<ICard> cards = new ArrayList<>();

        // This is not part of a set
        ICard cardOne = new Card();
            cardOne.setShape(Card.Shape.TRIANGLE);
            cardOne.setColor(Card.Color.GREEN);
            cardOne.setLineStyle(Card.LineStyle.DOTTED);
            cardOne.setNumber(2);

        // Cards 2 - 4 are part of one set
        ICard cardTwo = new Card();
            cardTwo.setShape(Card.Shape.SQUARE);
            cardTwo.setColor(Card.Color.BLUE);
            cardTwo.setLineStyle(Card.LineStyle.DOTTED);
            cardTwo.setNumber(1);
        ICard cardThree = new Card();
            cardThree.setShape(Card.Shape.SQUARE);
            cardThree.setColor(Card.Color.BLUE);
            cardThree.setLineStyle(Card.LineStyle.DASHED);
            cardThree.setNumber(1);

        // card 4 is part of both the first and second set
        ICard cardFour = new Card();
            cardFour.setShape(Card.Shape.SQUARE);
            cardFour.setColor(Card.Color.BLUE);
            cardFour.setLineStyle(Card.LineStyle.SOLID);
            cardFour.setNumber(1);

        // cards 4 - 6 are part of a second set
        ICard cardFive = new Card();
            cardFive.setShape(Card.Shape.CIRCLE);
            cardFive.setColor(Card.Color.GREEN);
            cardFive.setLineStyle(Card.LineStyle.DASHED);
            cardFive.setNumber(2);
        ICard cardSix = new Card();
            cardSix.setShape(Card.Shape.TRIANGLE);
            cardSix.setColor(Card.Color.RED);
            cardSix.setLineStyle(Card.LineStyle.DOTTED);
            cardSix.setNumber(3);
        
        cards.add(cardOne);
        cards.add(cardTwo);
        cards.add(cardThree);
        cards.add(cardFour);
        cards.add(cardFive);
        cards.add(cardSix);

        return cards;
    }

}
