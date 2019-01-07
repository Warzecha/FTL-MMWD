package gui;

import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class FilledRectangle {

    private Rectangle rectangle;
    private Line left, right, up, down;
    private double maxFillWidth;
    private double fill;

    public FilledRectangle(double x, double y, double width, double height) {
        arrangeElements(x, y, width, height);
        maxFillWidth = width;
    }

    private void arrangeElements(double x, double y, double width, double height) {
        rectangle = new Rectangle(x, y, width, height);
        left = new Line(x, y, x, y + height);
        right = new Line(x + width, y, x + width, y + height);
        up = new Line(x, y, x + width, y);
        down = new Line(x, y + height, x + width, y + height);
    }

    public void connectToGroup(Group group) {
        group.getChildren().addAll(rectangle, left, right, up, down);
    }

    public double getMaxFillWidth() {
        return maxFillWidth;
    }

    public void setMaxFillWidth(double maxFillWidth) {
        this.maxFillWidth = maxFillWidth;
    }

    public double getFill() {
        return fill;
    }

    public void setFill(double fill) {
        this.fill = Math.min(fill, maxFillWidth);
        rectangle.setWidth(maxFillWidth*(this.fill/maxFillWidth));
    }

    public void setPercentageFill(double fill) {
        try {
            setFill(fill/100*maxFillWidth);
        } catch (ArithmeticException e) {
            // don't fill, ignore
        }
    }
}