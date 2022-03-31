package com.example.graph_editor.model;

import com.example.graph_editor.model.mathematics.Geometry;
import com.example.graph_editor.model.mathematics.Point;
import com.example.graph_editor.model.mathematics.Rectangle;

import java.util.List;
import java.util.Set;

public class DrawManager {
    public static Point getRelative(Rectangle rectangle, Point point) {
        double x = (point.getX() - rectangle.getLeft())/rectangle.getWidth();
        double y = (point.getY() - rectangle.getTop())/rectangle.getHeight();
        return new Point(x, y);
    }

    public static Point getAbsolute(Rectangle rectangle, Point point) {
        double x = rectangle.getLeft() + point.getX() * rectangle.getWidth();
        double y = rectangle.getTop() + point.getY() * rectangle.getHeight();
        return new Point(x, y);
    }
    public static double getRelativeDistanceFrom(Rectangle rectangle, Point relativePoint, Vertex vertex) {
        return Geometry.distance(relativePoint, getRelative(rectangle, vertex.getPoint()));
    }
    public static double getRelativeDistanceFrom(Rectangle rectangle, Point relativePoint, Edge edge) {
        return Geometry.distanceFromSegment(relativePoint,
                getRelative(rectangle, edge.getSource().getPoint()),
                getRelative(rectangle, edge.getTarget().getPoint()));
    }

    //returns null if there are no vertices
    public static Vertex getNearestVertex(Graph graph, Rectangle rectangle, Point relativePoint, double delta, Set<Vertex> excluded) {
        Point point = getAbsolute(rectangle, relativePoint);
        double nearest = Double.MAX_VALUE;
        Vertex result = null;
        for(Vertex vertex : graph.getVertices()) {
            if (excluded.contains(vertex))
                continue;
            double distance = Geometry.distance(point, vertex.getPoint());
            if( distance < nearest){
                result = vertex;
                nearest = distance;
            }
        }

        if (result != null && getRelativeDistanceFrom(rectangle, relativePoint, result) > delta)
            return null;
        return result;
    }

    //returns null if there are no edges
    public static Edge getNearestEdge(Graph graph, Rectangle rectangle, Point relativePoint, double delta) {
        Point point = getAbsolute(rectangle, relativePoint);
        double nearest = Double.MAX_VALUE;
        Edge result = null;
        for(Edge edge : graph.getEdges()) {
            double distance = Geometry.distanceFromSegment(point,
                    edge.getSource().getPoint(), edge.getTarget().getPoint());
            if( distance < nearest){
                result = edge;
                nearest = distance;
            }
        }
        if (result != null && getRelativeDistanceFrom(rectangle, relativePoint, result) > delta)
            return null;

        return result;
    }

    private static Rectangle getExtremeRectangle(Graph graph) {
        Point extremeLeftTop = new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        Point extremeRightBot = new Point(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        for(Vertex vertex : graph.getVertices()) {
            Point point = vertex.getPoint();
            if(point.getX()<extremeLeftTop.getX())
                extremeLeftTop = new Point(point.getX(), extremeLeftTop.getY());
            if(point.getY()<extremeLeftTop.getY())
                extremeLeftTop = new Point(extremeLeftTop.getX(), point.getY());
            if(point.getX()>extremeRightBot.getX())
                extremeRightBot = new Point(point.getX(), extremeRightBot.getY());
            if(point.getY()>extremeRightBot.getY())
                extremeRightBot = new Point(extremeRightBot.getX(), point.getY());
        }
        return new Rectangle(extremeLeftTop, extremeRightBot);
    }

    public static void normalizeGraph(Graph graph) {
        Rectangle extremeRectangle = getExtremeRectangle(graph);
        Point extremeLeftTop = extremeRectangle.getLeftTop();
        Point extremeRightBot = extremeRectangle.getRightBot();
        double extremeWidth = extremeRightBot.getX() - extremeLeftTop.getX();
        double extremeHeight = extremeRightBot.getY() - extremeLeftTop.getY();
        for(Vertex vertex : graph.getVertices()) {
            Point point = vertex.getPoint();
            double x = (extremeWidth == 0.0)? 0.0 : (point.getX()-extremeLeftTop.getX())/extremeWidth;
            double y = (extremeHeight == 0.0)? 0.0 : (point.getY()-extremeLeftTop.getY())/extremeHeight;
            vertex.setPoint(new Point(x, y));
        }
    }

    public static Rectangle getOptimalRectangle(Graph graph, double paddingPercent, Rectangle rectangle) {
        List<Vertex> vertices = graph.getVertices();
        if(vertices.isEmpty()){
            return new Rectangle(new Point(0,0), new Point(1, rectangle.getHeight()/rectangle.getWidth()));
        }

        Rectangle extremeRectangle = getExtremeRectangle(graph);
        Point extremeLeftTop = extremeRectangle.getLeftTop();
        Point extremeRightBot = extremeRectangle.getRightBot();

        double extremeWidth = extremeRightBot.getX() - extremeLeftTop.getX();
        double extremeHeight = extremeRightBot.getY() - extremeLeftTop.getY();
        double scale = Math.max(extremeWidth/rectangle.getWidth(), extremeHeight/rectangle.getHeight());
        Point center = new Point(extremeLeftTop.getX()+extremeWidth/2, extremeLeftTop.getY()+extremeHeight/2);
        Point resultLeftTop = new Point(center.getX()-(0.5+paddingPercent)*rectangle.getWidth()*scale,
                center.getY()-(0.5+paddingPercent)*rectangle.getHeight()*scale);
        Point resultRightBot = new Point(center.getX()+rectangle.getWidth()*scale*(0.5+paddingPercent),
                center.getY()+rectangle.getHeight()*scale*(0.5+paddingPercent));

        if(vertices.size() == 1) {
            resultLeftTop = new Point(resultLeftTop.getX()-1, resultLeftTop.getY()-1);
            resultRightBot = new Point(resultRightBot.getX()+1, resultRightBot.getY()+1);
        }

        return new Rectangle(resultLeftTop, resultRightBot);
    }

    public static void translate(Rectangle rectangle, double dxNew, double dyNew) {
        Rectangle temp = new Rectangle(rectangle, dxNew*rectangle.getScale(), dyNew*rectangle.getScale());
        rectangle.setLeftTop(temp.getLeftTop());
        rectangle.setRightBot(temp.getRightBot());
    }
    static int x = 0;
    public static Rectangle getZoomedRectangle(Rectangle original, Point startA, Point startB, Point endARelative, Point endBRelative) {
        Point endA = getAbsolute(original, endARelative), endB = getAbsolute(original, endBRelative);
        double scale = Geometry.distance(startA, startB)/Geometry.distance(endA, endB);
        Point startCenter = Geometry.centerPoint(startA, startB), endCenter = Geometry.centerPoint(endA, endB);

        double toLeft = endCenter.getX() - original.getLeft();
        double toTop = endCenter.getY() - original.getTop();

        Point newLeftTop = new Point(startCenter.getX()-toLeft*scale, startCenter.getY()-toTop*scale);
        return new Rectangle(newLeftTop, original.getWidth()*scale, original.getHeight()*scale);
    }
}
