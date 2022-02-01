
import java.util.LinkedList;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdOut;

public class Board 
{

    
    private final int[][] tiles;
    private final int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles)
    {
        if (tiles == null) throw new IllegalArgumentException();

        n = tiles.length;
        this.tiles = new int [n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                this.tiles[i][j] = tiles[i][j];
    }
                                     
    // string representation of this board
    public String toString()
    {
        StringBuilder boardRep = new StringBuilder();
        boardRep.append(Integer.toString(n) + "\n"); 
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
                boardRep.append(String.format("%3d ", tiles[i][j]));
            
            boardRep.append("\n");
        }
        return boardRep.toString();
    }
      
    // board dimension n
    public int dimension()
    {
        return n;
    } 
     // does this board equal y?
    public boolean equals(Object y)
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;
        return isEquals(that);
    }
    private boolean isEquals(Board that)
    {
        if (this.n != that.n) return false;
        return Arrays.deepEquals(this.tiles, that.tiles);
    }
    private int goalValueAt(int row, int col)
    {
        if (row * this.n + col + 1 == this.n * this.n) return 0;
        return row * this.n + col + 1;
    }
    // number of tiles out of place
    public int hamming()
    {
        int score = n*n - 1;
        for (int i = 0; i < this.n; i++)
            for (int j = 0; j < this.n; j++)
                if (this.tiles[i][j] != 0 && this.tiles[i][j] == goalValueAt(i, j))
                    score--;
        return score; 
    }

    
    // sum of Manhattan distances between tiles and goal
    public int manhattan()
    {   
        int score = 0;
        for (int i = 0; i < this.n; i++)
            for (int j = 0; j < this.n; j++)
                if (this.tiles[i][j] != 0)
                {
                    int num = this.tiles[i][j];
                    int row = (num - 1) / n;
                    int col = (num - 1) % n;
                    score += (Math.abs(row - i) + Math.abs(col - j));
                }
        return score;
    }
  
    // is this board the goal board?
    public boolean isGoal()
    {
        for (int i = 0; i < this.n; i++)
            for (int j = 0; j < this.n; j++)
               if (this.tiles[i][j] != goalValueAt(i, j))
                    return false;
        return true;
    }

    private boolean swap(int r, int c, int tr, int tc)
    {
        if (tc < 0 || tc >= this.n || tr < 0 || tr >= this.n) return false;
        if (c < 0 || c >= this.n || r < 0 || r >= this.n) return false;
        
        int temp = tiles[r][c];
        tiles[r][c] = tiles[tr][tc];
        tiles[tr][tc] = temp;
        return true;
    }
       
    // all neighboring boards
    public Iterable<Board> neighbors()
    {
        // Maybe will change to heap or priority queue
        LinkedList<Board> nbList = new LinkedList<Board>();

        int blankRow = 0, blankCol = 0;
        for (int i = 0; i < this.n; i++)
            for (int j = 0; j < this.n; j++)
                if (this.tiles[i][j] == 0)
                {
                    blankRow = i;
                    blankCol = j;
                }
        
        if (blankRow - 1 >= 0)
        {
            Board nb = new Board(this.tiles);
            nb.swap(blankRow, blankCol, blankRow - 1, blankCol);
            nbList.add(nb);
        }

        if (blankRow + 1 < this.n)
        {
            Board nb = new Board(this.tiles);
            nb.swap(blankRow, blankCol, blankRow + 1, blankCol);
            nbList.add(nb);
        }

        if (blankCol - 1 >= 0)
        {
            Board nb = new Board(this.tiles);
            nb.swap(blankRow, blankCol, blankRow, blankCol - 1);
            nbList.add(nb);
        }

        if (blankCol + 1 < this.n)
        {
            Board nb = new Board(this.tiles);
            nb.swap(blankRow, blankCol, blankRow, blankCol + 1);
            nbList.add(nb);
        }
        return nbList;
    }   
    

   
    // a board that is obtained by exchanging any pair of tiles
    public Board twin()
    {
        Board b = new Board(this.tiles);

        for (int i = 0; i < n; i++) 
            for (int j = 0; j < n - 1; j++) 
                if (tiles[i][j] != 0 && tiles[i][j + 1] != 0) 
                {
                    b.swap(i, j, i, j + 1);
                    return b;
                }

        return b;
    }
  
    // unit testing (not graded)
    public static void main(String[] args)
    {
        int[][] a = {
            {1, 2, 0}, 
            {7, 5, 4}, 
            {8, 6, 3}
        };
    
        
        // // for (int i = 0; i < 10; i++)
        // //     for (int j = 0; j < 10; j++)
        // //     {
        // //         c[i][j] = i;
        // //     }
        Board b1 = new Board(a);
        for (Board s : b1.neighbors())
            StdOut.println(s.toString());
    }

}