
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import java.util.Arrays;
import java.util.HashSet;

public class BoggleSolver
{

    private final Trie<Integer> dictionary;
    private HashSet<String> validWords;
    
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary)
    {
        if (dictionary == null) throw new IllegalArgumentException();
        this.dictionary = new Trie<Integer>();
        for (String s : dictionary) this.dictionary.put(s, 1);
        
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board)
    {
        if (board == null) throw new IllegalArgumentException();
        validWords = new HashSet<String>();
        if (dictionary.isEmpty()) return validWords;
        int numR = board.rows();
        int numC = board.cols();       
                
        for (int i = 0; i < numR; ++i)
        {
            for (int j = 0; j < numC; ++j)
            {
                boolean[] marked = new boolean[numR * numC];
                Arrays.fill(marked, false);
                dfs(i, j, numR, numC, marked, "", board);
            }
        }

        return validWords;
    }
    private void dfs(int r, int c, int numR, int numC, boolean[] marked, String s, BoggleBoard board)
    {
        marked[getIdx(r, c, numR, numC)] = true;
        char letter = board.getLetter(r, c);
        if (letter == 'Q') s += "QU";
        else s += letter;

        if (!dictionary.hasPrefix(s)) return;

        if (s.length() > 2 && dictionary.contains(s)) validWords.add(s);
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
            {
                if (i == 0 && j == 0) continue;
                if (!isValid(r + i, c + j, numR, numC)) continue;
                if (marked[getIdx(r + i, c + j, numR, numC)]) continue;
                dfs(r + i, c + j, numR, numC, marked.clone(), s, board);
            }
    }
    private int getIdx(int r, int c, int numR, int numC)
    {
        if (!isValid(r, c, numR, numC)) throw new IllegalArgumentException(); 
        return r * numC + c;
    }
    
    private boolean isValid(int r, int c, int numR, int numC)
    {
       if (r < 0 || r >= numR || c < 0 || c >= numC) return false;
       return true; 
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word)
    {
        if (!dictionary.contains(word)) return 0;
        int length = word.length();
        if (length <= 2) return 0;
        else if (length <= 4) return 1;
        else if (length == 5) return 2;
        else if (length == 6) return 3;
        else if (length == 7) return 5;
        else return 11;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }

}
