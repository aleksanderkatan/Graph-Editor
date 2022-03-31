package com.example.graph_editor.model.mathematics;

import static java.lang.Math.PI;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class Geometry {
    public static boolean close(double a, double b) {
        double NEARLY_ZERO = 0.000001;
        return a-b < NEARLY_ZERO && b-a < NEARLY_ZERO;
    }
    public static double distance(Point a, Point b) {
        double xDiff = a.getX()-b.getX();
        double yDiff = a.getY()-b.getY();
        return Math.sqrt( xDiff*xDiff + yDiff*yDiff );
    }

    public static Point centerPoint(Point a, Point b) {
        return new Point((a.getX()+b.getX())/2, (a.getY()+b.getY())/2);
    }

    // distance from point x to segment ab
    public static double distanceFromSegment(Point x, Point a, Point b) {
        if(a.equals(b))
            return distance(x, b);
        Point ax = new Point(x.getX()-a.getX(), x.getY()-a.getY());
        Point ab = new Point(b.getX()-a.getX(), b.getY()-a.getY());
        double projection = (ab.getX()*ax.getX() + ab.getY()*ax.getY())/ab.length();
        double distance = min(max(projection, 0), ab.length());
        return distance(x, getOnSegment(a, b, distance));
    }

    // point in distance 'distance' from start to target
    public static Point getOnSegment(Point start, Point target, double distance) {
        if(start.equals(target))
            throw new RuntimeException("Frame size equals 0 or is negative");
        double part = distance / distance(start, target);
        return new Point(start.getX()+(target.getX()-start.getX())*part, start.getY()+(target.getY()-start.getY())*part);
    }

    //angle in degrees, 0 is to the right
    //circle center at (0.5, 0.5) and radius 0.5
    public static Point getPointOnCircle(int index, int total) {
        double startingAngle = total%2==0? -45 : -90;
        double realAngle = startingAngle + (index*1.0/total)*360;
        double x = Math.cos(realAngle/180.0*PI);
        double y = Math.sin(realAngle/180.0*PI);
        return new Point(x/2+0.5, y/2+0.5);
    }

    public static Point getPointBipartite(int index, int left, int right) {
        int spaces = Math.max(right, left) - 1;
        double dist = spaces == 0 ? 1.0 : 1.0/spaces;

        Point result;
        if (index >= left) {
            double rightStart = right>left ? 1 : 1-(left-right)/2.0*dist;
            result = new Point(1, rightStart - dist*(index-left));
        } else {
            double leftStart = left>right ? 1 : 1-(right-left)/2.0*dist;
            result = new Point (0, leftStart - dist*index);
        }
        return result;
    }

    public static Point getPointBinaryTree(int index, int layers) {
        int layer = log2int(index+1);
        int ind = index - (1 << layer) + 1;

//        double diffY = 1.0/(layers-1);
        double diffX = 1.0/(1 << layer);

        double x = diffX/2 + diffX * ind;
//        double y = 1-(1-diffY*layer);
        double y = 1.0-1.0/(1<<layer);
        return new Point(x, y);
    }

    public static Point getGridPoint(int x, int y, int totalX, int totalY) {
        double diffY = 1.0/(totalY-1);
        double diffX = 1.0/(totalX-1);

        return new Point(x*diffX, y*diffY);
    }

    public static int log2int(int a) {
        if (a<1) return -1;
        if (a==1) return 0;
        return 1+log2int(a/2);
    }


}
