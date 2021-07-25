package stacs.starcade.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.*;

import stacs.starcade.*;
import stacs.starcade.impl.server.*;

public class SetValidatorUnitTests {

    List<ICard> cards;
    
    @BeforeEach
    public void setup() {
        cards = createCards();
    }

    @Test
    public void firstThreeCardsAreNotSet() {
        List<ICard> testSet = cards.subList(0, 3);
        assertFalse(SetValidator.isValidSet(testSet));
    }

    @Test
    public void cardsTwoThroughFourAreValidSet() {
        List<ICard> testSet = cards.subList(1, 4);
        assertTrue(SetValidator.isValidSet(testSet));
    }

    @Test
    public void cardsFiveThroughSevenAreValidSet() {
        List<ICard> testSet = cards.subList(4, 7);
        assertTrue(SetValidator.isValidSet(testSet));
    }

    public List<ICard> createCards() {
        // cards 2-4 have the same qualities
        // cards 5-7 are all different
        // so there are two sets in this model
        List<ICard> cards = new ArrayList<>();
        ICard cardOne = Mockito.mock(ICard.class);
            Mockito.when(cardOne.getShape()).thenReturn(Card.Shape.TRIANGLE);
            Mockito.when(cardOne.getColor()).thenReturn(Card.Color.GREEN);
            Mockito.when(cardOne.getLineStyle()).thenReturn(Card.LineStyle.DOTTED);
            Mockito.when(cardOne.getNumber()).thenReturn(2);
        ICard cardTwo = Mockito.mock(ICard.class);
            Mockito.when(cardTwo.getShape()).thenReturn(Card.Shape.SQUARE);
            Mockito.when(cardTwo.getColor()).thenReturn(Card.Color.BLUE);
            Mockito.when(cardTwo.getLineStyle()).thenReturn(Card.LineStyle.DOTTED);
            Mockito.when(cardTwo.getNumber()).thenReturn(1);
        ICard cardThree = Mockito.mock(ICard.class);
            Mockito.when(cardThree.getShape()).thenReturn(Card.Shape.SQUARE);
            Mockito.when(cardThree.getColor()).thenReturn(Card.Color.BLUE);
            Mockito.when(cardThree.getLineStyle()).thenReturn(Card.LineStyle.DASHED);
            Mockito.when(cardThree.getNumber()).thenReturn(1);
        ICard cardFour = Mockito.mock(ICard.class);
            Mockito.when(cardFour.getShape()).thenReturn(Card.Shape.SQUARE);
            Mockito.when(cardFour.getColor()).thenReturn(Card.Color.BLUE);
            Mockito.when(cardFour.getLineStyle()).thenReturn(Card.LineStyle.SOLID);
            Mockito.when(cardFour.getNumber()).thenReturn(1);
        ICard cardFive = Mockito.mock(ICard.class);
            Mockito.when(cardFive.getShape()).thenReturn(Card.Shape.SQUARE);
            Mockito.when(cardFive.getColor()).thenReturn(Card.Color.BLUE);
            Mockito.when(cardFive.getLineStyle()).thenReturn(Card.LineStyle.SOLID);
            Mockito.when(cardFive.getNumber()).thenReturn(2);
        ICard cardSix = Mockito.mock(ICard.class);
            Mockito.when(cardSix.getShape()).thenReturn(Card.Shape.CIRCLE);
            Mockito.when(cardSix.getColor()).thenReturn(Card.Color.GREEN);
            Mockito.when(cardSix.getLineStyle()).thenReturn(Card.LineStyle.DASHED);
            Mockito.when(cardSix.getNumber()).thenReturn(1);
        ICard cardSeven = Mockito.mock(ICard.class);
            Mockito.when(cardSeven.getShape()).thenReturn(Card.Shape.TRIANGLE);
            Mockito.when(cardSeven.getColor()).thenReturn(Card.Color.RED);
            Mockito.when(cardSeven.getLineStyle()).thenReturn(Card.LineStyle.DOTTED);
            Mockito.when(cardSeven.getNumber()).thenReturn(3);
        
        cards.add(cardOne);
        cards.add(cardTwo);
        cards.add(cardThree);
        cards.add(cardFour);
        cards.add(cardFive);
        cards.add(cardSix);
        cards.add(cardSeven);

        return cards;
    }
}
