import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private MinPQ prioQ;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null){
            throw new IllegalArgumentException("input to Solver cannot be null");
        }
        MinPQ prioQ = new MinPQ(initial.dimension()*initial.dimension());
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable(){

    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){

        // TO DO
        // Return -1 if unsolvable
        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution(){
        // If unsolvable
        if (this.moves()==-1){
            return null;
        }




    }

    // test client (see below) 
    public static void main(String[] args){

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
