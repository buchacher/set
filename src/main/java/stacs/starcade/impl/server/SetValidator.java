package stacs.starcade.impl.server;

import java.util.HashMap;
import java.util.List;

import stacs.starcade.ICard;

/**
 * Class with static methods for varifying the validity of sets.
 */
public class SetValidator {
    
    /**
     * Returns true if the three cards constitute a valid set. That
     * means all attributes are all-same or all-different across the
     * cards.
     * @param cards The three cards being tested.
     */
    public static boolean isValidSet(List<ICard> cards) {

        return (    (sameColors(cards) || allDifferentColors(cards)) &&
                    (sameShapes(cards) || allDifferentShapes(cards)) &&
                    (sameLines(cards)  || allDifferentLines(cards))  &&
                    (sameNumbers(cards)|| allDifferentNumbers(cards)));

    }

    /**
     * Returns true if all the cards passed have the same color
     */
    private static boolean sameColors(List<ICard> cards) {
        for (ICard card : cards)
            if (card.getColor() != cards.get(0).getColor())
                return false;

        return true;
    }

    /**
     * Returns true if all cards passed have a different color
     */
    private static boolean allDifferentColors(List<ICard> cards) {

        HashMap<ICard.Color, Integer> map = new HashMap<>();

        for (ICard card : cards) {
            int currentCount = map.getOrDefault(card.getColor(), 0);
            int newCount = currentCount + 1;
            map.put(card.getColor(), newCount);
        }

        for (int count : map.values())
            if (count > 1) return false;

        return true;
    }
    
    /**
     * Returns true if all cards passed have the same shape
     */
    private static boolean sameShapes(List<ICard> cards) {
        for (ICard card : cards)
            if (card.getShape() != cards.get(0).getShape())
                return false;

        return true;
    }

    /**
     * Returns true if all cards passed have the same shape
     */
    private static boolean allDifferentShapes(List<ICard> cards) {
        HashMap<ICard.Shape, Integer> map = new HashMap<>();
        
        for (ICard card : cards) {
            int currentCount = map.getOrDefault(card.getShape(), 0);
            int newCount = currentCount + 1;
            map.put(card.getShape(), newCount);
        }

        for (int count : map.values())
            if (count > 1) return false;

        return true;
    }
    
    /**
     * Returns true if all cards passed have the same line style
     */
    private static boolean sameLines(List<ICard> cards) {
        for (ICard card : cards)
            if (card.getLineStyle() != cards.get(0).getLineStyle())
                return false;

        return true;
    }

    /**
     * Returns true if all cards passed have a different line style
     */
    private static boolean allDifferentLines(List<ICard> cards) {
        HashMap<ICard.LineStyle, Integer> map = new HashMap<>();
        
        for (ICard card : cards) {
            int currentCount = map.getOrDefault(card.getLineStyle(), 0);
            int newCount = currentCount + 1;
            map.put(card.getLineStyle(), newCount);
        }

        for (int count : map.values())
            if (count > 1) return false;

        return true;
    }
    
    /**
     * Returns true if all cards passed have the same number
     */
    private static boolean sameNumbers(List<ICard> cards) {
        for (ICard card : cards)
            if (card.getNumber() != cards.get(0).getNumber())
                return false;

        return true;
    }

    /**
     * Returns true if all cards passed have a different number
     */
    private static boolean allDifferentNumbers(List<ICard> cards) {
        HashMap<Integer, Integer> map = new HashMap<>();
        
        for (ICard card : cards) {
            int currentCount = map.getOrDefault(card.getNumber(), 0);
            int newCount = currentCount + 1;
            map.put(card.getNumber(), newCount);
        }

        for (int count : map.values())
            if (count > 1) return false;

        return true; 
    }
}
