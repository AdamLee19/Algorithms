import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;



public class PointSET {
    private final SET<Point2D> pSet;
    public PointSET()                               // construct an empty set of points
    {
        pSet = new SET<Point2D>();
    }
    public boolean isEmpty()                      // is the set empty? 
    {
        return pSet.isEmpty();
    }
    public int size()                         // number of points in the set 
    {
        return pSet.size();
    }
    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if (p == null) throw new IllegalArgumentException();
        pSet.add(p);
    }
    public boolean contains(Point2D p)            // does the set contain point p? 
    {
        if (p == null) throw new IllegalArgumentException();
        return pSet.contains(p);
    }
    public void draw()                         // draw all points to standard draw 
    {
        for (Point2D p : pSet) StdDraw.point(p.x(), p.y());
    }
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary) 
    {
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> rangeList = new ArrayList<Point2D>();
        for (Point2D p : pSet)
            if (rect.contains(p))
                rangeList.add(p);
        return rangeList;
    }
    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
    {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        double minDist = Double.POSITIVE_INFINITY;
        Point2D nearPoint = null;
        for (Point2D that : pSet)
        {
            double dist = p.distanceSquaredTo(that);
            if (dist < minDist)
            {
                nearPoint = that;
                minDist = dist;
            }
        }
        return nearPoint;
    } 
    public static void main(String[] args)                  // unit testing of the methods (optional) 
    {
    }
}