package stacs.starcade.impl.client.Interfaces;

/**
 * The Interface ICardManager.
 */
public interface ICardManager {

    /**
     * Gets the x coordinate.
     *
     * @param index the index
     * @return the x coordinate
     */
    int getXCoordinate(int index);
    
    /**
     * Gets the y coordinate.
     *
     * @param index the index
     * @return the y coordinate
     */
    int getYCoordinate(int index);
    
    /**
     * Gets the card width.
     *
     * @return the card width
     */
    int getCard_width();
    
    /**
     * Gets the card height.
     *
     * @return the card height
     */
    int getCard_height();

    /**
     * Gets the shape X coordinate.
     *
     * @param index the index
     * @return the shape X coordinate
     */
    int getShapeXCoordinate(int index);
    
    /**
     * Gets the shape Y coordinate.
     *
     * @param index the index
     * @param number the number
     * @return the shape Y coordinate
     */
    int getShapeYCoordinate(int index, int number);
    
    /**
     * Gets the shape height.
     *
     * @return the shape height
     */
    int getShapeHeight();

    /**
     * Gets the x coordinate for set.
     *
     * @param cardIndex the card index
     * @return the x coordinate for set
     */
    int getXCoordinateForSet(int cardIndex);
    
    /**
     * Gets the y coordinate for set.
     *
     * @return the y coordinate for set
     */
    int getYCoordinateForSet();
    
    /**
     * Gets the card set width.
     *
     * @return the card set width
     */
    int getCardSetWidth();
    
    /**
     * Gets the card set height.
     *
     * @return the card set height
     */
    int getCardSetHeight();

    /**
     * Gets the shape set height.
     *
     * @return the shape set height
     */
    int getShapeSetHeight();
    
    /**
     * Gets the shape set X coordinate.
     *
     * @param index the index
     * @return the shape set X coordinate
     */
    int getShapeSetXCoordinate(int index);
    
    /**
     * Gets the shape set Y coordinate.
     *
     * @param index the index
     * @param number the number
     * @return the shape set Y coordinate
     */
    int getShapeSetYCoordinate(int index, int number);
}
