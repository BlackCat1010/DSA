import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;
import java.util.LinkedList;

public class Solver {
    private SearchNode resultNode;
    private Board board;
    private boolean solvable;

    private class SearchNode {
        Board board;
        int movesToReachBoard;
        SearchNode prev;
        // int hammingval;
        int manhattenval;

        public SearchNode(Board board, int movesToReachBoard, SearchNode prev) {
            this.board = board;
            this.movesToReachBoard = movesToReachBoard;
            this.prev = prev;
            this.manhattenval = this.board.manhattan() + this.movesToReachBoard;
        }
        
    }

    private static class SearchNodeComparator implements Comparator<SearchNode> {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                // Prefer if hamming is small.
                return Integer.compare(o1.manhattenval, o2.manhattenval);
            }
    }

    

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("input to Solver cannot be null");
        }
        this.board = initial;
        
        MinPQ<SearchNode> prioQ = new MinPQ<>(new SearchNodeComparator());
        MinPQ<SearchNode> prioQTwins = new MinPQ<>(new SearchNodeComparator());
        // Set<Board> visited = new HashSet<>();
        // Set<Board> visitedTwins = new HashSet<>();

        SearchNode initNode = new SearchNode(initial, 0, null);
        SearchNode initNodeTwins = new SearchNode(initial.twin(), 0, null);
        prioQ.insert(initNode);
        prioQTwins.insert(initNodeTwins);

        while (true) {
            if (processStep(prioQ)) {
                this.solvable = true;
                return;
            }
             if (processStep(prioQTwins)) {
                this.solvable = false;
                this.resultNode = null;
                return;
             }

        }
    }

    // private boolean processStep(MinPQ<SearchNode> prioQ,Set<Board> visited) {
    private boolean processStep(MinPQ<SearchNode> prioQ) {
        if (prioQ == null) {
            return false;
        }
        SearchNode min = prioQ.delMin();
        if (min.board.isGoal()) {
            this.resultNode = min;
            return true;
        }
        
        // visited.add(min.board);
        int movestoReachBoard = min.movesToReachBoard + 1;
        SearchNode prev = min;
        for (Board itboard : prev.board.neighbors()) {
            if (prev.prev == null || !itboard.equals(prev.prev.board)) {
                SearchNode node = new SearchNode(itboard, movestoReachBoard, prev);
                prioQ.insert(node);
            }
        }
        return false;


    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        // Counting Inversions. Alternative method is to implement:
        // Concurrent processing of original board and its twin in while true loop until 1 found.
        return this.solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (this.resultNode == null) {
            return -1;
        } else {
            return this.resultNode.movesToReachBoard;
        }
        // TO DO
        
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        // If unsolvable
        if (this.isSolvable()) {
            LinkedList<Board> path = new LinkedList<>();
            SearchNode node = this.resultNode;
            while (node.prev != null) {
                path.addFirst(node.board);
                node = node.prev;
            }
            // Add Final 
            path.addFirst(node.board);
        return path;
        } else {
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
