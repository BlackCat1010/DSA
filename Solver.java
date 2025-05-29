import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Solver {
    private searchNode resultNode;
    private Board board;

    private class searchNode {
        Board board;
        int movesToReachBoard;
        Board prev;
        // int hammingval;
        int manhattenval;

        public searchNode(Board board, int movesToReachBoard,Board prev) {
            this.board = board;
            this.movesToReachBoard = movesToReachBoard;
            this.prev = prev;
            this.manhattenval = this.board.manhattan() + this.movesToReachBoard;
        }
        
    }

    private static class searchNodeComparator implements Comparator<searchNode> {
            @Override
            public int compare (searchNode o1, searchNode o2) {
                // Prefer if hamming is small.
                return Integer.compare(o1.manhattenval,o2.manhattenval);
            }
    }

    

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("input to Solver cannot be null");
        }
        this.board = initial;
        
        MinPQ<searchNode> prioQ = new MinPQ<>(4,new searchNodeComparator());
        Set<Board> visited = new HashSet<>();

        int movesToReachBoard = 0;
        Board prev;
        Board initboard = initial;
        visited.add(board);

        searchNode initNode = new searchNode(initboard,movesToReachBoard,null);
        prioQ.insert(initNode);
        searchNode min = prioQ.delMin();

        while (!min.board.isGoal()) {
            visited.add(min.board);
            movesToReachBoard = min.movesToReachBoard+1;
            prev = min.board;
            for (Board itboard :prev.neighbors()) {

                // Critical Optimization - Only enqueue if not equals
                // Added Set to hold visited boards from recurring nodes.
                if (!itboard.equals(prev)) {
                    if (!visited.contains(itboard)) {
                        searchNode node = new searchNode(itboard,movesToReachBoard,prev);
                        prioQ.insert(node);
                    }
                }
            }
            min = prioQ.delMin();
        }
        this.resultNode = min;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        // Counting Inversions. Alternative method is to implement:
        // Concurrent processing of original board and its twin in while true loop until 1 found.
        int inversion = 0;
        for (int i = 0; i < board.dimension(); i++) {
            for (int j = i + 1; j < board.dimension(); j++ ){
                if (board.getBoardArray()[i] != 0 && board.getBoardArray()[j] != 0 && board.getBoardArray()[i] > board.getBoardArray()[j]){
                    inversion++;
                }
            }
        }
        return inversion % 2 == 0;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (this.resultNode==null) {
            return -1;
        } else{
            return this.resultNode.movesToReachBoard;
        }
        // TO DO
        
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        // If unsolvable
        if (this.isSolvable()){
            
            return null;   
        }else{
            return null;
        }


    }

    // test client (see below) 
    public static void main(String[] args) {

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
