import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord 
{

    public static void main(String[] args) 
    {
        // Prints "Hello, World" to the terminal window.
        int count = 0;
        String champion = "";
        String word = null;

        while (!StdIn.isEmpty())
        {
            word = StdIn.readString();
            count++;
            if (StdRandom.bernoulli(1.0/(double) count))
            {
                champion = word;
            } 
        }
        StdOut.println(champion);
    }

}
