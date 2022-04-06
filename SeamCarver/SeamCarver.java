import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    private Picture picture;
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture)
    {

        if (picture == null) throw new IllegalArgumentException();

        this.picture = new Picture(picture);
    }
   

    // current picture
    public Picture picture()
    {
        return picture;
    }

    // width of current picture
    public int width()
    {
        return picture.width();
    }

    // height of current picture
    public int height()
    {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y)
    {
        if (x < 0 || x >= width()) throw new IllegalArgumentException(); 
        if (y < 0 || y >= height()) throw new IllegalArgumentException(); 

        
        return 0.0;
    }

//    // sequence of indices for horizontal seam
//    public int[] findHorizontalSeam()

//    // sequence of indices for vertical seam
//    public int[] findVerticalSeam()

//    // remove horizontal seam from current picture
//    public void removeHorizontalSeam(int[] seam)

//    // remove vertical seam from current picture
//    public void removeVerticalSeam(int[] seam)

   //  unit testing (optional)
   public static void main(String[] args)
   {

   }

}