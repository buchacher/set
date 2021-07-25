package stacs.starcade.impl.server;

import stacs.starcade.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Generates cards for the game of set.
 */
public class CardGenerator {
    // method to create one random set of unique cards
    // method to create 9 unique random cards
    // method to to check for uniqueness against all other cards

    static Random random = new Random();

    // lists of attributes from which attributes will be drawn
    static List<ICard.Shape> shapes;
    static List<ICard.LineStyle> lineStyles;
    static List<ICard.Color> colors;
    static List<Integer> numbers;

    /**
     * Generates 12 unique cards, containing at least one valid set.
     * @return
     */
    public static List<ICard> generateCards() {

        List<ICard> cards = new ArrayList<>();

        setAttributeLists();

        cards.addAll(generateRandomValidSet());
        while (cards.size() < SetModel.NUM_CARDS) cards.add(generateUniqueCard(cards));

        Collections.shuffle(cards);

        return cards;

    }

    /**
     * Sets the attribute lists to be randomly shuffled collections of the values
     * found in the ICard enum properties. Each list has exactly the three values
     * specified in each ICard enum.
     */
    private static void setAttributeLists() {

        shapes = new ArrayList<>();
        shapes.add(ICard.Shape.SQUARE);
        shapes.add(ICard.Shape.TRIANGLE);
        shapes.add(ICard.Shape.CIRCLE);
        Collections.shuffle(shapes);

        lineStyles = new ArrayList<>();
        lineStyles.add(ICard.LineStyle.SOLID);
        lineStyles.add(ICard.LineStyle.DOTTED);
        lineStyles.add(ICard.LineStyle.DASHED);
        Collections.shuffle(lineStyles);

        colors = new ArrayList<>();
        colors.add(ICard.Color.BLUE);
        colors.add(ICard.Color.GREEN);
        colors.add(ICard.Color.RED);
        Collections.shuffle(colors);

        numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        Collections.shuffle(numbers);

    }

    /**
     * Generates a valid set of three cards with randomly valid attributes.
     * @return
     */
    private static List<ICard> generateRandomValidSet() {
        // for each card property, decide if it is going to be all the same or all different
        // go through each card and set the property to be the first randomly selected different property or same property
        List<ICard> set = new ArrayList<>();

        boolean shapeAllSame = random.nextBoolean();
        boolean lineStyleAllSame = random.nextBoolean();
        boolean colorAllSame = random.nextBoolean();

        ICard.Shape fixedShape = shapes.get(0);
        ICard.LineStyle fixedLineStyle = lineStyles.get(0);
        ICard.Color fixedColor = colors.get(0);

        for (int i = 0 ; i < SetModel.NUM_CARDS_IN_SET ; i++) {
            ICard newCard = new Card();
            newCard.setStatus(ICard.Status.UNSELECTED);
            if (shapeAllSame) newCard.setShape(fixedShape);
            else newCard.setShape(shapes.get(i));
            
            if (lineStyleAllSame) newCard.setLineStyle(fixedLineStyle);
            else newCard.setLineStyle(lineStyles.get(i));

            if (colorAllSame) newCard.setColor(fixedColor);
            else newCard.setColor(colors.get(i));

            // number is always randomized to ensure the cards
            // in set will not be completely the same
            newCard.setNumber(numbers.get(i));
            set.add(newCard);
        }

        return set;
    }

    /**
     * Returns a new card which does not have the same combination of
     * properties as any card in the passed list of cards.
     * @param cards The list of cards against which the uniqueness of
     * the returned card will be tested
     */
    private static ICard generateUniqueCard(List<ICard> cards) {
        ICard newCard = new Card();

        do {
            newCard.setShape(shapes.get(random.nextInt(3)));
            newCard.setLineStyle(lineStyles.get(random.nextInt(3)));
            newCard.setColor(colors.get(random.nextInt(3)));
            newCard.setNumber(numbers.get(random.nextInt(3)));
            newCard.setStatus(ICard.Status.UNSELECTED);
        } while (!isCardUnique(cards, newCard));
        
        return newCard;
    }

    /**
     * Returns true if the newCard does not have the same combination of
     * properties as any card in the passed list of cards.
     */
    private static boolean isCardUnique(List<ICard> cards, ICard newCard) {

        boolean isCardUnique = true;

        for (int i = 0 ; i < cards.size() ; i++) {

            boolean differenceFound = false;

            ICard existingCard = cards.get(i);

            if (existingCard.getColor() != newCard.getColor())
                differenceFound = true;
            if (existingCard.getLineStyle() != newCard.getLineStyle())
                differenceFound = true;
            if (existingCard.getShape() != newCard.getShape())
                differenceFound = true;             
            if (existingCard.getNumber() != newCard.getNumber())
                differenceFound = true;
            
            if (!differenceFound) isCardUnique = false;

        }

        return isCardUnique;
    }

}
