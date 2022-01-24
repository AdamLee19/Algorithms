import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

    private double[] threshold;

    
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        if (n <= 0 || trials <= 0) 
            throw new IllegalArgumentException();

        threshold = new double [trials];

        for (int t = 0; t < trials; t++)
        {
            Percolation perc = new Percolation(n);
            while (!perc.percolates())
            {
                int i = StdRandom.uniform(1, n + 1);
                int j = StdRandom.uniform(1, n + 1);
                if (!perc.isOpen(i, j))
                    perc.open(i, j);
            }
            threshold[t] = (double) perc.numberOfOpenSites() / (double) (n * n);
        }
        
    }

    // sample mean of percolation threshold
    public double mean()
    {
        return StdStats.mean(threshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return StdStats.stddev(threshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
        return mean() - (1.96 * stddev() / Math.sqrt(threshold.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        return mean() + (1.96 * stddev() / Math.sqrt(threshold.length));
    }

//    test client (see below)
   public static void main(String[] args)
   {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);


        StdOut.printf("%-23s = %f\n", "mean", ps.mean());
        StdOut.printf("%-23s = %f\n", "stddev", ps.stddev());
        StdOut.printf("%-23s = [%f, %f]\n", "95% confidence interval", ps.confidenceLo(), ps.confidenceHi());
           
   }

}