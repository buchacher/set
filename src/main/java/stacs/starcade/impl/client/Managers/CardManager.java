package stacs.starcade.impl.client.Managers;

import stacs.starcade.impl.client.Interfaces.ICardManager;

/**
 * The Class CardManager.
 */
public class CardManager implements ICardManager {

    /** The card width. */
    public int card_width = 140;
    
    /** The card height. */
    public int card_height = 166;

    /** The card set width. */
    public int card_set_width = 72;
    
    /** The card set height. */
    public int card_set_height = 84;

    /**
     * Gets the x coordinate.
     *
     * @param index the index
     * @return the x coordinate
     */
    public int getXCoordinate(int index) {
        index = index % 4;
        switch (index) {
            case 0:
                return 8;
            case 1:
                return 16 + card_width;
            case 2:
                return 24 + (card_width * 2);
            default:
                return 32 + (card_width * 3);
        }
    };

    /**
     * Gets the y coordinate.
     *
     * @param index the index
     * @return the y coordinate
     */
    public int getYCoordinate(int index) {
        if (index < 4) {
            return 8;
        } else if (index < 8) {
            return 16 + card_height;
        } else {
            return 24 + (card_height * 2);
        }
    };

    /**
     * Gets the card width.
     *
     * @return the card width
     */
    public int getCard_width() {
        return card_width;
    }

    /**
     * Gets the card height.
     *
     * @return the card height
     */
    public int getCard_height() {
        return card_height;
    }

    /**
     * Gets the shape X coordinate.
     *
     * @param index the index
     * @return the shape X coordinate
     */
    public int getShapeXCoordinate(int index) {
        return (card_width / 2) - (getShapeHeight() / 2);
    };

    /**
     * Gets the shape Y coordinate.
     *
     * @param index the index
     * @param number the number
     * @return the shape Y coordinate
     */
    public int getShapeYCoordinate(int index, int number) {
        switch (index) {
            case 0:
                return number == 3 ? 16 : number == 2 ? (card_height - ((getShapeHeight() * 2) + 16)) / 2 : (card_height / 2) - (getShapeHeight() / 2);
            case 1:
                return number == 3 ? 32 + getShapeHeight() : number == 2 ? ((card_height - (getShapeHeight() * 2)) / 2) + getShapeHeight() + 8 : 8;
            case 2:
                return 48 + (getShapeHeight() * 2);
            default: return 0;
        }
    };

    /**
     * Gets the shape height.
     *
     * @return the shape height
     */
    public int getShapeHeight() {
        int spaces = 4 * 16;
        int total = card_height - spaces;
        return total / 3;
    }

    /**
     * Gets the x coordinate for set.
     *
     * @param cardIndex the card index
     * @return the x coordinate for set
     */
    public int getXCoordinateForSet(int cardIndex) {
        switch (cardIndex) {
            case 0:
                return 8;
            case 1:
                return 16 + card_set_width;
            default:
                return 24 + (card_set_width * 2);
        }
    }

    /**
     * Gets the y coordinate for set.
     *
     * @return the y coordinate for set
     */
    public int getYCoordinateForSet() {
        return 8;
    }

    /**
     * Gets the card set width.
     *
     * @return the card set width
     */
    public int getCardSetWidth() {
        return card_set_width;
    }

    /**
     * Gets the card set height.
     *
     * @return the card set height
     */
    public int getCardSetHeight() {
        return card_set_height;
    }

    /**
     * Gets the shape set height.
     *
     * @return the shape set height
     */
    public int getShapeSetHeight() {
        int spaces = 4 * 8;
        int total = card_set_height - spaces;
        return total / 3;
    }

    /**
     * Gets the shape set X coordinate.
     *
     * @param index the index
     * @return the shape set X coordinate
     */
    public int getShapeSetXCoordinate(int index) {
        return (card_set_width / 2) - (getShapeSetHeight() / 2);
    };

    /**
     * Gets the shape set Y coordinate.
     *
     * @param index the index
     * @param number the number
     * @return the shape set Y coordinate
     */
    public int getShapeSetYCoordinate(int index, int number) {
        switch (index) {
            case 0:
                return number == 3 ? 8 : number == 2 ? (card_set_height - ((getShapeSetHeight() * 2) + 16)) / 2 : (card_set_height / 2) - (getShapeSetHeight() / 2);
            case 1:
                return number == 3 ? 16 + getShapeSetHeight() : number == 2 ? ((card_set_height - (getShapeSetHeight() * 2)) / 2) + getShapeSetHeight() + 8 : 8;
            case 2:
                return 24 + (getShapeSetHeight() * 2);
            default: return 0;
        }
    };
}

