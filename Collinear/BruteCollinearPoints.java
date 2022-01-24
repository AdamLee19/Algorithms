import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints 
{
    private ArrayList<LineSegment> segs = new ArrayList<LineSegment>();

    public BruteCollinearPoints(Point[] points)
    {
        if (points == null) throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) if (points[i] == null) throw new IllegalArgumentException();
        Point[] copyP = points.clone();

        Arrays.sort(copyP);    // This is the only way which won't use extra space
        for (int i = 0; i < copyP.length - 1; i++)
        {
            if (copyP[i] == null) throw new IllegalArgumentException();
            if (copyP[i].compareTo(copyP[i + 1]) == 0) throw new IllegalArgumentException(); // Duplicates
        }


        for (int p = 0; p < copyP.length - 3; p++)
        {
            Point pointP = copyP[p];
            for (int q = p + 1; q < copyP.length - 2; q++)
            {
                Point pointQ = copyP[q];
                double pq = pointP.slopeTo(pointQ); 
                for (int r = q + 1; r < copyP.length - 1; r++)
                {
                    Point pointR = copyP[r];
                    double pr = pointP.slopeTo(pointR);
                    if (pq != pr) continue;
                    for (int s = r + 1; s <= copyP.length - 1; s++)
                    {
                        Point pointS = copyP[s];
                        double ps = pointP.slopeTo(pointS);        
                        if (pq == ps) segs.add(new LineSegment(pointP, pointS));                                                                                                            
                    }
                }
            }
        }
    }
    
    public int numberOfSegments()        // the number of line segments
    {
        return segs.size();
    }
    public LineSegment[] segments()                // the line segments
    {
        return segs.toArray(new LineSegment[segs.size()]);
    }
    
    public static void main(String[] args) 
    {
        
         // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.01);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        StdDraw.setPenColor(StdDraw.RED);
       StdDraw.setPenRadius(0.001);
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}