package com.example.graph_editor.model.mathematics;

public class Rectangle {
    private Point leftTop;
    private Point rightBot;

    public Rectangle(Point leftTop, Point rightBot) {
        this.leftTop = leftTop;
        this.rightBot = rightBot;
    }

    public Rectangle(Point leftTop, double width, double height) {
        this.leftTop = leftTop;
        this.rightBot = new Point(leftTop.getX()+width, leftTop.getY()+height);
    }

    public Rectangle(Rectangle original, double scale) {
        double width = original.getWidth() * scale;
        double height = original.getHeight() * scale;

        Point centre = original.getCentre();

        leftTop = new Point(centre.getX() - width/2, centre.getY() - height/2);
        rightBot = new Point(centre.getX() + width/2, centre.getY() + height/2);
    }

    public Rectangle(Rectangle original, double dx, double dy) {
        leftTop = original.getLeftTop();
        rightBot = original.getRightBot();

        leftTop = new Point(leftTop.getX() - dx, leftTop.getY() - dy);
        rightBot = new Point(rightBot.getX() - dx, rightBot.getY() - dy);
    }

    public Point getLeftTop() { return leftTop; }
    public Point getRightBot() { return rightBot; }

    public double getLeft() { return  leftTop.getX(); }
    public double getTop() { return  leftTop.getY(); }

    public void setLeftTop(Point leftTop) { this.leftTop = leftTop; }
    public void setRightBot(Point rightBot) { this.rightBot = rightBot; }

    public double getHeight() { return rightBot.getY() - leftTop.getY(); }
    public double getWidth() { return rightBot.getX() - leftTop.getX(); }
    public double getScale() { return getWidth(); }

    public Point getCentre() { return new Point((rightBot.getX() + leftTop.getX())/2, (rightBot.getY() + leftTop.getY())/2); }

    public Rectangle deepCopy() {
        return new Rectangle(leftTop.deepCopy(), rightBot.deepCopy());
    }
}
