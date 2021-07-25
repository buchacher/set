package stacs.starcade.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import stacs.starcade.*;
import stacs.starcade.impl.server.*;

public class CardGeneratorUnitTests {
    
    @Test
    public void iterativelyCheckAllCardsAreUnique() {
        for (int i = 0 ; i < 1000 ; i++) {
            List<ICard> cards = CardGenerator.generateCards();
            if (!areAllCardsUnique(cards))
                for (ICard card : cards) {
                    System.out.println("Card " + i);
                    System.out.println(card.getNumber() + " " +
                                       card.getShape() + " " +
                                       card.getLineStyle() + " " +
                                       card.getColor());
                }
            assertTrue(areAllCardsUnique(cards));
        }
    }

    private boolean areAllCardsUnique(List<ICard> cards) {
        // this could be made faster (with lots more code) by:
            // converting card properties into strings
            // concatenate all these strings
            // add or increment an entry into Hashmape of strings to counts for that concatenated string
            // check if any value in hashmap is above one

        for (int i = 0 ; i < cards.size() ; i++) {
            for (int j = i + 1 ; j < cards.size() ; j++) {

                boolean differenceFound = false;

                ICard outerCard = cards.get(i);
                ICard innerCard = cards.get(j);

                if (outerCard.getColor() != innerCard.getColor())
                    differenceFound = true;
                if (outerCard.getLineStyle() != innerCard.getLineStyle())
                    differenceFound = true;
                if (outerCard.getShape() != innerCard.getShape())
                   differenceFound = true;             
                if (outerCard.getNumber() != innerCard.getNumber())
                   differenceFound = true;
                
                if (!differenceFound) return false;
            }
        }
        return true;
    }

    @Test
    public void thereAreExactly12Cards() {
        List<ICard> cards = CardGenerator.generateCards();
        assertEquals(12, cards.size());
    }

}
