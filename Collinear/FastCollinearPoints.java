import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;



public class FastCollinearPoints 
{
    private ArrayList<LineSegment> segs; 

    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
        segs = new ArrayList<LineSegment>();
        if (points == null) throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) if (points[i] == null) throw new IllegalArgumentException();
        Point[] copyP = points.clone();
        Arrays.sort(copyP);    // This is the only way which won't use extra space
        for (int i = 0; i < copyP.length - 1; i++) if (copyP[i].compareTo(copyP[i + 1]) == 0) throw new IllegalArgumentException(); // Duplicates
        
        

        for (int i = 0; i < copyP.length; i++)
        {
            
            Arrays.sort(copyP); 
            Point currentP = copyP[i];

            Arrays.sort(copyP, currentP.slopeOrder());
            ArrayList<Point> sameSlopeP = new ArrayList<Point>();
            for (int j = 1; j < copyP.length;)
            {
                double slope = currentP.slopeTo(copyP[j]);    
                while (j < copyP.length && slope == currentP.slopeTo(copyP[j]))
                    sameSlopeP.add(copyP[j++]);
                if (sameSlopeP.size() >= 3)
                {
                    Point[] arrayPoints =  sameSlopeP.toArray(new Point[sameSlopeP.size()]);
                    Arrays.sort(arrayPoints);
                    Point s = arrayPoints[0];
                    Point e = arrayPoints[arrayPoints.length -1];
                    if (currentP.compareTo(s) < 0)
                       segs.add(new LineSegment(currentP, e)); 
                }
                sameSlopeP.clear();       
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
        for (int i = 0; i < n; i++) 
        {
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}