import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public final class Board {
    private final int[] boardArray;
    private final int[][] tiles;
    private final int length;

    public int getlength(){
        return this.length;
    }

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = tiles;
        this.length = tiles.length;
        this.boardArray = new int[length*length];

        // Clone
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                boardArray[i*length+j]=tiles[i][j];
                System.out.println(boardArray[i*length+j] +" added. ");
            }
        }

    }
                                           
    // string representation of this board
    @Override
    public String toString() {
        // System.out.println("check this "+boardArray[2]);
        String builder = String.valueOf(length);
        for (int i = 0; i < length*length; i++) {
            if(i % length == 0){
                builder = builder + "\n";
            }
            builder = builder +" "+ String.valueOf(boardArray[i]);
        }
        
        return builder;
    }

    // board dimension n
    public int dimension() {
        return length;
    }

    // number of tiles out of place - did not handle for 0. dont unds whats 0
    public int hamming() {
        int hammingCounter = 0;
        for (int i = 0; i < length*length-1; i++) {
            if(boardArray[i]!=i+1){
                hammingCounter++;
            }
        }

        // Handling 0

        if(boardArray[length*length-1]!=0) {
                hammingCounter++;
            }
        return hammingCounter;
    }

    // sum of Manhattan distances between tiles and goal. 5 in 1 means 2 moves. 8 in 1 means 3 moves. n = 3. 5-1 will give difference in values. 
    // |current - goal| gives abs distance. abs distance / length means how many skips u can do in 1 move by vertical movement
    // remainder is added to this value to give total moves needed.
    // Proof: How far is 8 from goal if in pos1 --> 8-1 = 7 moves horizontally. 7/3 = 2 with 1 remainder. 2+1 = 3.
    // Proof: How far is 5 from goal if in pos9 --> 5-9 = 4 moves horizontally. 4/3 = 1 with 1 remainder. 1+1 = 2
    // - did not handle for 0. dont unds whats 0
    public int manhattan() {
        int manhattendist = 0;
        for (int i = 0; i < length*length-1; i++){
            if(boardArray[i]!=i+1 && boardArray[i]!=0){
                int diff = Math.abs(boardArray[i]- (i+1));
                manhattendist = manhattendist+ diff/length + diff%length;
            }

            // For handling 0 value out of position
            if(boardArray[i]!=i+1 && boardArray[i]==0){
                int diff = Math.abs(length*length- (i+1));
                manhattendist = manhattendist+ diff/length + diff%length;
            }

            
        }

        // For Handling 0 position
        if(boardArray[length*length-1]!=0){
            int diff = Math.abs(boardArray[length*length-1]- (length*length));
            manhattendist = manhattendist+ diff/length + diff%length;
        }
        return manhattendist;
    }

    // is this board the goal board?
    public boolean isGoal(){
        for (int i = 0; i < length*length-1; i++){
            if(boardArray[i]!=i){
                return false;
            }
        }
        // No need to handle 0's final position. It will be false if even 1 is wrong.
        return true;
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y){

        // 1. Check for self-comparison
        if (this == y) {
            return true;
        }

        // 2. Check for null
        if (y == null){
            return false;
        }

        // 3. Check for type compatibility
        if (this.getClass() != y.getClass()) {
            return false;
        }

        // 4. Cast to the correct type
        Board that = (Board) y;

        // 5. Compare relevant fields
        // For primitive types, use ==
        // For object types, use equals() or Objects.equals() to handle nulls
        // For arrays, use Arrays.equals() or Arrays.deepEquals() for nested arrays
        return this.length == that.length &&
           Arrays.equals(this.boardArray,that.boardArray) &&
           Arrays.deepEquals(this.tiles, that.tiles);
    }

    // Below also overwritten as part of Java requirement for Collections-based Objects comparison
    @Override
    public int hashCode() {
        return Objects.hash(length, Arrays.hashCode(boardArray), Arrays.hashCode(tiles));
    }


    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighborsList = new ArrayList<>();

        int blankRowIndx = 0;
        int blankColIndx = 0;
        for(int i = 0; i < length; i++){
            for (int j = 0; j < length;j++){
                if(tiles[i][j] == 0) {
                blankRowIndx = i;
                blankColIndx = j;
                break;
                }
            }
        }
        
        int[][] cardinalDirections = 
        { {1,0}, // Down
        {0,1}, // Right
        {0,-1}, // Left
        {-1,0} // Up
        };

        for (int i = 0; i < 4; i++) {
            int newRow = blankRowIndx + cardinalDirections[i][0];
            int newCol = blankColIndx + cardinalDirections[i][1];

            if (newRow < length && newRow >= 0 && newCol >= 0 && newCol < length){
                // Valid
                int[][] tilesClone = new int[length][length];

                for (int r = 0; r < length; r++) {
                    System.arraycopy(tiles[r], 0, tilesClone[r], 0, length);
                }

                tilesClone[blankRowIndx][blankColIndx] = tilesClone[newRow][newCol];
                tilesClone[newRow][newCol] = 0;
                neighborsList.add(new Board(tilesClone));
            }
        }
        return neighborsList;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        int[][] twinTiles = new int[length][length];

        for (int r = 0; r < length; r++) {
            System.arraycopy(tiles[r], 0, twinTiles[r], 0, length);
        }

        for (int i = 0; i < length; i++ ){
            for (int j = 0; j < length; j++ ){
                if (twinTiles[i][j]!=0 && twinTiles[i][j+1]!=0) {
                    int temp = twinTiles[i][j];
                    twinTiles[i][j] = twinTiles[i][j+1];
                    twinTiles[i][j+1] = temp;
                    
                    return new Board(twinTiles);

                }
            }
        }
        // Should never return this
        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] sampledata = new int[3][3];
        sampledata[0][0] = 1;
        sampledata[0][1] = 2;
        sampledata[0][2] = 3;
        sampledata[1][0] = 4;
        sampledata[1][1] = 5;
        sampledata[1][2] = 6;
        sampledata[2][0] = 7;
        sampledata[2][1] = 8;
        sampledata[2][2] = 9;

        int[][] sampledata2 = new int[3][3];
        sampledata2[0][0] = 1;
        sampledata2[0][1] = 2;
        sampledata2[0][2] = 3;
        sampledata2[1][0] = 4;
        sampledata2[1][1] = 5;
        sampledata2[1][2] = 6;
        sampledata2[2][0] = 7;
        sampledata2[2][1] = 8;
        sampledata2[2][2] = 9;

        Board brd = new Board(sampledata);
        Object brd2 = new Board(sampledata2);
        System.out.println(brd.toString());
        System.out.println(brd.equals(brd2));
    }

}