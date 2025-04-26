import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    double[] fractionslogged;
    int n;
    int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if (n<=0 ||trials<=0) {
            throw new IllegalArgumentException ("n cannot be <=0. trials cannot be <=0");
            
        }
        this.n = n;
        this.trials = trials;
        this.fractionslogged = new double[trials];

        for (int t=0;t<trials;t++){
            Percolation grid = new Percolation(n);
            int counter = 0;
            while(!grid.percolates()){
                counter++;
                int randomintX = StdRandom.uniformInt(1,n+1);
                int randomintY = StdRandom.uniformInt(1,n+1);
                System.out.println("Randomizedint X:"+randomintX+" Y:"+randomintY);
                grid.open(randomintX,randomintY);
                System.out.println("Opening time "+counter);
            }
            System.out.println("Percolation for test "+t+"done in "+counter+" time!");
             
            this.fractionslogged[t] = (double) counter/(n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        double totalfractions = 0.00;

        for (double i : this.fractionslogged){
            totalfractions+= i;
        }
        return totalfractions/trials;
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        double totalsum = 0.00;
        double mean = this.mean();
        for (double i : this.fractionslogged){
            totalsum+= Math.pow((i-mean),2);
        }
        return Math.sqrt(totalsum/(this.trials-1));
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        double mean = mean();
        double stdev = stddev();
        return mean-((1.96*stdev)/Math.sqrt(this.trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        double mean = mean();
        double stdev = stddev();
        return mean+((1.96*stdev)/Math.sqrt(this.trials));
    }

   // test client (see below)
   public static void main(String[] args){
    //WIP
       System.out.println("Enter for n");
       int n = StdIn.readInt();
       System.out.println("Enter for trials");
       int trials = StdIn.readInt(); 
       System.out.println("Starting");

       PercolationStats statsholder = new PercolationStats(n,trials);
       System.out.println("Mean: "+ statsholder.mean());
       System.out.println("Standard Deviation: "+ statsholder.stddev());
       System.out.println("95% confidence interval: ["+ statsholder.confidenceLo()+", "+statsholder.confidenceHi()+"]");
       
        
       }
    
}