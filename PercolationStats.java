import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private double[] fractionslogged;
    private int trials;
    private static final double CONFIDENCE_95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n cannot be <= 0. trials cannot be <= 0");
        }
        this.trials = trials;
        this.fractionslogged = new double[trials];

        for (int t = 0; t < trials; t++) {
            Percolation grid = new Percolation(n);
            int counter = 0;
            while (!grid.percolates()) { 
                counter++;
                int randomintX = StdRandom.uniformInt(1, n+1);
                int randomintY = StdRandom.uniformInt(1, n+1);
                // System.out.println("Randomizedint X:"+randomintX+" Y:"+randomintY);
                grid.open(randomintX, randomintY);
                // System.out.println("Opening time "+counter);
            }
            // System.out.println("Percolation for test "+t+"done in "+counter+" time!");
             
            this.fractionslogged[t] = (double) counter / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractionslogged);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.fractionslogged);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = mean();
        double stdev = stddev();
        return mean - ((CONFIDENCE_95*stdev) / Math.sqrt(this.trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double stdev = stddev();
        return mean + ((CONFIDENCE_95*stdev) / Math.sqrt(this.trials));
    }

   // test client (see below)
   public static void main(String[] args) {

       int n = Integer.parseInt(args[0]);
       int t = Integer.parseInt(args[1]);

       PercolationStats statsholder = new PercolationStats(n, t);
       String confidence = statsholder.confidenceLo() + ", " + statsholder.confidenceHi();
       StdOut.println("mean                    = " + statsholder.mean());
       StdOut.println("stddev                  = " + statsholder.stddev());
       StdOut.println("95% confidence interval = " + confidence);
       
       }
    
}