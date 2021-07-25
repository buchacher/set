package stacs.starcade.impl.server;

import java.util.ArrayList;
import java.util.List;

import stacs.starcade.*;

/**
 * Static methods for creating a game of set with a timer, cards
 * and the 'maxSets' field set.
 */
public class Builder {

    /**
     * Creates a new valid set model with 12 randomly generated cards,
     * in which there is at least one valid set.
     */
    public static ISetModel buildModel(String playerName) {
        ISetModel model = new SetModel(playerName);
        
        List<ICard> cards = CardGenerator.generateCards();
        model.setCards(cards);

        int maxSets = SetCounter.getMaxNumSets(cards);
        model.setMaxSets(maxSets);

        ITimer timer = new Timer();
        model.setTimer(timer);

        return model;
    }

    public static ISetModel buildTestModel(String playerName) {
        ISetModel model = new SetModel(playerName);

        List<ICard> cards = new ArrayList<>();
        ICard cardOne = makeCard(Card.Shape.SQUARE, ICard.Color.BLUE, ICard.LineStyle.DOTTED, 1);
        ICard cardTwo = makeCard(Card.Shape.SQUARE, ICard.Color.BLUE, ICard.LineStyle.DOTTED, 1);
        ICard cardThree = makeCard(Card.Shape.SQUARE, ICard.Color.BLUE, ICard.LineStyle.DOTTED, 1);
        ICard cardFour = makeCard(Card.Shape.SQUARE, ICard.Color.BLUE, ICard.LineStyle.DOTTED, 1);
        ICard cardFive = makeCard(Card.Shape.SQUARE, ICard.Color.BLUE, ICard.LineStyle.DOTTED, 1);
        ICard cardSix = makeCard(Card.Shape.SQUARE, ICard.Color.BLUE, ICard.LineStyle.DOTTED, 1);
        ICard cardSeven = makeCard(Card.Shape.SQUARE, ICard.Color.BLUE, ICard.LineStyle.DOTTED, 1);
        ICard cardEight = makeCard(Card.Shape.SQUARE, ICard.Color.BLUE, ICard.LineStyle.DOTTED, 1);
        ICard cardNine = makeCard(Card.Shape.SQUARE, ICard.Color.BLUE, ICard.LineStyle.DOTTED, 1);
        ICard cardTen = makeCard(Card.Shape.SQUARE, ICard.Color.BLUE, ICard.LineStyle.DOTTED, 1);
        ICard cardEleven = makeCard(Card.Shape.SQUARE, ICard.Color.BLUE, ICard.LineStyle.DOTTED, 1);
        ICard cardTwelve = makeCard(Card.Shape.SQUARE, ICard.Color.BLUE, ICard.LineStyle.DOTTED, 1);

        cards.add(cardOne);
        cards.add(cardTwo);
        cards.add(cardThree);
        cards.add(cardFour);
        cards.add(cardFive);
        cards.add(cardSix);
        cards.add(cardSeven);
        cards.add(cardEight);
        cards.add(cardNine);
        cards.add(cardTen);
        cards.add(cardEleven);
        cards.add(cardTwelve);

        model.setCards(cards);
        model.setMaxSets(4);

        ITimer timer = new Timer();
        model.setTimer(timer);

        return model;
    }

    public static ICard makeCard(ICard.Shape shape, ICard.Color color, ICard.LineStyle lineStyle, int number) {
        ICard card = new Card();
        card.setShape(shape);
        card.setColor(color);
        card.setLineStyle(lineStyle);
        card.setNumber(number);
        return card;
    }

}
