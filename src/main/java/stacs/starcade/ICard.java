package stacs.starcade;

import javafx.scene.layout.AnchorPane;

/**
 * Defines the interface for a card in the game of Set.
 */
public interface ICard {

    /**
     * Used for the GUI
     */
    AnchorPane getPane();

    /**
     * See the enum below for possible values
     */
    Shape getShape();

    /**
     * See the enum below for possible values
     */
    LineStyle getLineStyle();

    /**
     * See the enum below for possible values
     */
    int getNumber();

    /**
     * See the enum below for possible values
     */
    Color getColor();

    /**
     * See the enum below for possible values
     */
    Status getStatus();

    /**
     * This is index is only used in the GUI for developer convenience
     */
    int getIndex();

    void setStatus(Status status);

    void setColor(Color color);
   
    void setShape(Shape shape);
    
    void setLineStyle(LineStyle lineStyle);
     
    void setNumber(int number);
    
    void setPane(AnchorPane pane);

    public enum Status {
        SELECTED, UNSELECTED, BLOCKED
    }

    public enum Color {
        RED, GREEN, BLUE
    }

    public enum Shape {
        TRIANGLE, SQUARE, CIRCLE
    }

    public enum LineStyle {
        SOLID, DASHED, DOTTED
    }
}
