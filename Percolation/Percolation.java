import edu.princeton.cs.algs4.WeightedQuickUnionUF;



public class Percolation {

    private WeightedQuickUnionUF grid;
    private WeightedQuickUnionUF noBackwash;
    private int N;
    private boolean[] isOpenedGrid;
    private int openSitesNum;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        if (n <= 0) throw new IllegalArgumentException("n must be grater than 0!");
        grid = new WeightedQuickUnionUF(n*n + 2);
        noBackwash = new WeightedQuickUnionUF(n*n + 2);
        N = n;


        isOpenedGrid = new boolean[n*n];
        for (int i = 0; i < n*n; i++)
            isOpenedGrid[i] = false;
        openSitesNum = 0;

        for (int i = 1; i <= n; i++)
        {
            
            grid.union(n*n + 1, n * (n - 1) + i);
            
        }

    }

    private int xyTo1D(int row, int col)
    {
        return (row - 1) * N + (col - 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {

        if (row <= 0 || row > N) throw new IllegalArgumentException("row index i out of bounds");   
        if (col <= 0 || col > N) throw new IllegalArgumentException("col index i out of bounds");
            
           

        int idx = xyTo1D(row, col);
        if (isOpenedGrid[idx]) return;

        isOpenedGrid[idx] = true;
        openSitesNum++;

        if (row == 1)
        {
            grid.union(0, idx + 1);
            noBackwash.union(0, idx + 1);
        }
        if (row == N)
           grid.union(N * N + 1, idx + 1); 

        if ((row - 1 > 0) && (isOpenedGrid[xyTo1D(row - 1, col)]))
        {
             grid.union(idx + 1, xyTo1D(row - 1, col) + 1);
             noBackwash.union(idx + 1, xyTo1D(row - 1, col) + 1);
        }
           

        if ((row + 1 <= N) && (isOpenedGrid[xyTo1D(row + 1, col)]))
        {
             grid.union(idx + 1, xyTo1D(row + 1, col) + 1); 
             noBackwash.union(idx + 1, xyTo1D(row + 1, col) + 1); 
        }
           
    
        if ((col - 1 > 0) && (isOpenedGrid[xyTo1D(row, col - 1)]))
        {
            grid.union(idx + 1, xyTo1D(row, col - 1) + 1);
            noBackwash.union(idx + 1, xyTo1D(row, col - 1) + 1);
        }       
        if ((col + 1 <= N) && (isOpenedGrid[xyTo1D(row, col + 1)]))
        {
            grid.union(idx + 1, xyTo1D(row, col + 1) + 1);
            noBackwash.union(idx + 1, xyTo1D(row, col + 1) + 1);
        }
        
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        if (row <= 0 || row > N) throw new IllegalArgumentException("row index i out of bounds");   
        if (col <= 0 || col > N) throw new IllegalArgumentException("col index i out of bounds");

        return isOpenedGrid[xyTo1D(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        if (row <= 0 || row > N) throw new IllegalArgumentException("row index i out of bounds");   
        if (col <= 0 || col > N) throw new IllegalArgumentException("col index i out of bounds");

        if (!isOpenedGrid[xyTo1D(row, col)]) return false;

        if (noBackwash.find(0) != noBackwash.find(xyTo1D(row, col) + 1)) return false;

        // Backwash. Happens at dummy bottom node. 
        
        return (isOpenedGrid[xyTo1D(row, col)]) && (noBackwash.find(0) == noBackwash.find(xyTo1D(row, col) + 1));
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return openSitesNum;
    }

    // does the system percolate?
    public boolean percolates()
    {
        
        return (grid.find(0) == grid.find(N*N + 1));
    }

    // test client (optional)
    public static void main(String[] args)
    {
        Percolation p = new Percolation(2);
        

        p.open(-2, 1);
       
        System.out.println(p.percolates());
        System.out.println(p.numberOfOpenSites()); 
    }
}