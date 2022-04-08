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
        return new Picture(picture);
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

        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1)
            return (double) 1000;

        int left = picture.getRGB(x + 1, y);
        int right = picture.getRGB(x - 1, y);
        double deltaX = gradient(left, right);

        int up = picture.getRGB(x, y + 1);
        int down = picture.getRGB(x, y - 1);
        double deltaY = gradient(up, down);

        return Math.sqrt(deltaX + deltaY);
    }


    private double gradient(int rgbP, int rgbM)
    {
       
        int rP = (rgbP >> 16) & 0xFF;
        int gP = (rgbP >>  8) & 0xFF;
        int bP = (rgbP >>  0) & 0xFF;
        
        int rM = (rgbM >> 16) & 0xFF;
        int gM = (rgbM >>  8) & 0xFF;
        int bM = (rgbM >>  0) & 0xFF;

        return (double) (rP - rM) * (rP - rM) +
                        (gP - gM) * (gP - gM) +
                        (bP - bM) * (bP - bM);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam()
    {
        this.transpose();
        int[] route = this.findVerticalSeam();
        this.transpose();
        return route;
    }

    private void transpose() 
    {
        int width = this.width();
        int height = this.height();

        Picture tmpPicture = new Picture(height, width);
        for (int row = 0; row < width; row++) 
            for (int col = 0; col < height; col++)
                tmpPicture.setRGB(col, row, picture.getRGB(row, col));
       
        picture = tmpPicture;
     }


    // sequence of indices for vertical seam
    public int[] findVerticalSeam()
    {
        int width = this.width();
        int height = this.height();


        double[][] energyCache = new double [width] [height];
        double[][] distTo = new double [width][height];
        int[][]    edgeTo = new int [width][height];
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                energyCache[x][y] = energy(x, y);
                distTo[x][y] = Double.POSITIVE_INFINITY;
                if (y == 0) distTo[x][y] = energyCache[x][y];
            }       
        }

        // (x - 1, y + 1), (x, y + 1), (x + 1, y + 1)
        for (int y = 0; y < height - 1; y++)
        {
            for (int x = 0; x < width; x++)
            {
                for (int i = -1; i <= 1; i++)
                {
                    if (x + i < 0 || x + i >= width) continue;

                    if (distTo[x + i][y + 1] > distTo[x][y] + energyCache[x + i][y + 1])
                    {
                        distTo[x + i][y + 1] = distTo[x][y] + energyCache[x + i][y + 1];
                        edgeTo[x + i][y + 1] = x;
                    }
                }       
            }       
        }

        int minCol = -1;
        double minEnergy = Double.POSITIVE_INFINITY;
        for (int x = 0; x < width; x++)
            if (distTo[x][height - 1] < minEnergy)
            {
                minCol = x;
                minEnergy = distTo[x][height - 1];
            }


        int[] route = new int [height];
        int count = height - 1;
        while (count >= 0)
        {
            route[count] = minCol;
            minCol = edgeTo[minCol][count--];
        }
     
        
        return route;
    }

     //  remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam)
    {
        if (seam == null) throw new IllegalArgumentException(); 
        if (height() <= 1) throw new IllegalArgumentException(); 
        if (seam.length != width()) throw new IllegalArgumentException(); 

        transpose();
        removeVerticalSeam(seam);
        transpose();

    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam)
    {
        if (seam == null) throw new IllegalArgumentException(); 
        if (width() <= 1) throw new IllegalArgumentException(); 
        if (seam.length != height()) throw new IllegalArgumentException();

        int width = this.width();
        int height = this.height();
        Picture tmpPicture = new Picture(width - 1, height);
        int prev = seam[0];
        for (int y = 0; y < height; y++)
        {
            if (seam[y] < 0 || seam[y] >= width()) throw new IllegalArgumentException(); 
            if (Math.abs(seam[y] - prev) > 1) throw new IllegalArgumentException();
            for (int x = 0; x < width - 1; x++)
            {
                if (x < seam[y]) tmpPicture.setRGB(x, y, picture.getRGB(x, y));
                else tmpPicture.setRGB(x, y, picture.getRGB(x + 1, y));
            }
            prev = seam[y];
        }

        picture = tmpPicture;
    }
    
   //  unit testing (optional)
//    public static void main(String[] args)
//    {  

//    }

}