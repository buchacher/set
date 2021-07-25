package stacs.starcade.impl.server;

import javafx.scene.layout.AnchorPane;
import stacs.starcade.ICard;

/**
 * Implements a card in the game of Set.
 */
public class Card implements ICard {

    private Color color;
    private Shape shape;
    private LineStyle lineStyle;
    private int number;
    private AnchorPane pane;
    private Status status;
    private int index;

    public Card() {
        this.status = Status.UNSELECTED;
    }

    public Card(Color color, Shape shape, LineStyle lineStyle, int number, Status status, int index) {
        this.color = color;
        this.shape = shape;
        this.lineStyle = lineStyle;
        this.number = number;
        this.status = status;
        this.index = index;
    }

    @Override
    public AnchorPane getPane() {
        return pane;
    }

    @Override
    public Shape getShape() {
        return shape;
    }

    @Override
    public LineStyle getLineStyle() {
        return lineStyle;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }
 
    @Override
    public void setColor(Color color) {
        this.color = color;
    }
   
    @Override
    public void setShape(Shape shape) {
        this.shape = shape;
    }
    
    @Override
    public void setLineStyle(LineStyle lineStyle) {
        this.lineStyle = lineStyle;
    }
     
    @Override
    public void setNumber(int number) {
        this.number = number;
    }
    
    @Override
    public void setPane(AnchorPane pane) {
        this.pane = pane;
    }

    public int getIndex() {
        return index;
    }
}
