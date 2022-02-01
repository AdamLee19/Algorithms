import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;



public class Solver 
{
    // private final int steps;
    private boolean isSolv; 
    private final SearchNode lastOne;
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial)
    {
        if (initial == null) throw new IllegalArgumentException();
        MinPQ<SearchNode> twinTree = new MinPQ<SearchNode>();
        MinPQ<SearchNode> gameTree = new MinPQ<SearchNode>();
        isSolv = true;

        SearchNode sn, tn;
        gameTree.insert(new SearchNode(initial, null));        
        twinTree.insert(new SearchNode(initial.twin(), null)); 
        while (!gameTree.min().board.isGoal()) 
        {   
            sn = gameTree.delMin();
            tn = twinTree.delMin();
            if (tn.board.isGoal())
            {
                isSolv = false;
                break;
            }
            for (Board b : sn.board.neighbors())
                if (sn.prev == null || !b.equals(sn.prev.board))
                    gameTree.insert(new SearchNode(b, sn));
            for (Board b : tn.board.neighbors())
                if (tn.prev == null || !b.equals(tn.prev.board))
                    twinTree.insert(new SearchNode(b, tn));
        }
        
        lastOne = gameTree.min();
        
    }
    
    // is the initial board solvable? (see below)
    public boolean isSolvable()
    {
        return isSolv;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves()
    {
        if (!isSolv) return -1;
        return lastOne.moves;
    
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution()
    {
        if (!isSolv) return null;
        Stack<Board> soluStac = new Stack<Board>();
        for (SearchNode n = lastOne; n != null; n = n.prev)
            soluStac.push(n.board);
        return soluStac;
    }

    
    private class SearchNode implements Comparable<SearchNode>
    {
        private final int moves;
        private final Board board;
        private final SearchNode prev;
        private final int priority;
        public SearchNode(Board board, SearchNode prev)
        {
            this.moves = (prev == null) ? 0 : prev.moves + 1;
            this.board = board;
            this.prev = prev;
            this.priority = this.board.manhattan() + this.moves;
        }
        public int compareTo(SearchNode that)
        {
            if (this == that) return 0;
            return Integer.compare(this.priority, that.priority);
        }
    }

    // test client 
    /*
     * solve a slider puzzle 
     */
    public static void main(String[] args) 
    {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

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
    
