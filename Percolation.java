import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    // Need to use QuickUnionFindImplementation - Tree 
    private boolean[] percolatemap;
    private WeightedQuickUnionUF weightedQuickUnionUFArray;
    private final int virtualTop;
    private final int virtualBottom;
    private int n;
    private int totalopensites;

    // creates n-by-n grid, with all sites initially blocked. Done
    public Percolation(int n) {
        this.percolatemap = new boolean[n*n]; // defaults to false
        this.weightedQuickUnionUFArray = new WeightedQuickUnionUF(n*n+2); // adding 2 more for virtual top/bottom
        this.virtualTop = n*n;
        this.virtualBottom = n*n+1;
        this.n = n;
        this.totalopensites = 0;
        // int counter = 0; 
        if (n <= 0) {
            // System.out.println ( "Constructor.n: " + n );
            throw new IllegalArgumentException("IllegalArgumentException - Input <=0 not allowed.");
        }
    }
    private void validate(int row, int col) {
        // smoking
        if (row > n+1 || col > n+1 || row <= 0 || col <= 0) {
            throw new IllegalArgumentException("IllegalArgumentException - Argument out of range"+ row+ " "+col);
        }
    }

    // opens the site (row, col) if it is not open already. 
    public void open(int row, int col) {
        validate(row, col);
        int target = (row-1)*n + (col-1);
        if (this.percolatemap[target]) {
            // If true meaning spot already opened, do nothing
        } else {
            // If false, meaning spot unopened, open
            this.totalopensites++;
            this.percolatemap[target] = true;
            if (row == 1) {
                this.weightedQuickUnionUFArray.union(target, virtualTop);
                // System.out.println("TopOpened");
            }
            if (row == n) {
                this.weightedQuickUnionUFArray.union(target, virtualBottom);
                // System.out.println("BottomOpened");
            }

            // union w top of target
            int toptarget = (row-1-1)*n + (col-1);
            if (row-1-1 >= 0 && isOpen(row-1, col)) {
                // If top target exists and isOpen,
                this.weightedQuickUnionUFArray.union(target, toptarget);
            }

            // union w left of target
            int lefttarget = (row-1)*n+(col-1-1);
            if (col-1-1 >= 0 && isOpen(row, col-1)) {
                // If left target exists,
                this.weightedQuickUnionUFArray.union(target, lefttarget); 
            }
            

            // union w right of target
            int righttarget = (row-1)*n + (col-1+1);
            if (col-1+1 < this.n && isOpen(row, col+1)) {
                // If right of target exists,
                this.weightedQuickUnionUFArray.union(target, righttarget);  
            }
            

            // union w bottom of target
            int bottomtarget = (row-1+1)*n + (col-1);
            if (row-1+1 < this.n && isOpen(row+1, col)) {
                // If bottom of target exists,
                this.weightedQuickUnionUFArray.union(target, bottomtarget);
            }
            
        }
    }
    // is the site (row, col) open? 
    public boolean isOpen(int row, int col) {
        validate(row, col);
        int target = (row-1)*n + (col-1);
        return this.percolatemap[target];
    }

    // is the site (row, col) full? 
    public boolean isFull(int row, int col) {
        validate(row, col);
        int target = (row-1)*n + (col-1);

        // Compare target with topsite 1 by 1.
        // for (int i = 0; i < n; i++) {
            // if (isOpen(1, i+1)) {
            //    int topsite = 0;
            //    if (this.weightedQuickUnionUFArray.find(target) == this.weightedQuickUnionUFArray.find(target)) {
            //        return true;
            //    }
            // }
        // }
        // return false;

        return this.weightedQuickUnionUFArray.find(virtualTop) == this.weightedQuickUnionUFArray.find(target);
    }

    // returns the number of open sites. Done.
    public int numberOfOpenSites() {
        return this.totalopensites;
    }

    // does the system percolate?
    public boolean percolates() {
        // sweep starting from bottom left-most of grid
        // for (int j = 0; j < n; j++) {
        //    boolean currentResult = this.isFull(n, j+1);
            // System.out.println("percolateResult: "+currentResult);
        //    if (currentResult) {
                // System.out.println("System percolates");
        //        return currentResult;
        //    }
        // }
        return this.weightedQuickUnionUFArray.find(virtualTop) == this.weightedQuickUnionUFArray.find(virtualBottom);
    }

    // test client (optional)    
    public static void main(String[] args) {
            // Percolation grid = new Percolation(10);
            // Test Case for Percolation. To Percolate
            // grid.open(1, 4);
            // grid.open(2, 4);
            // grid.open(3, 4);
            // grid.open(4, 4);
            // grid.open(5, 4); 
            // grid.open(6, 4);
            // grid.open(7, 4);
            // grid.open(8, 4);
            // grid.open(9, 4);
            // grid.open(10, 4);

             // System.out.println("Open sites: " + grid.numberOfOpenSites());
             // System.out.println("Does system percolate? " + grid.percolates());
             // System.out.println(grid.percolates());
             // System.out.println("Percolate finish");

    }
}

