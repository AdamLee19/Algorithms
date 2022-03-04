import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Outcast 
{
    private final WordNet wordnet;
    public Outcast(WordNet wordnet)         // constructor takes a WordNet object
    {
        this.wordnet = wordnet;
    }
    public String outcast(String[] nouns)   // given an array of WordNet nouns, return an outcast
    {
        int maximum = Integer.MIN_VALUE;
        String castWord = null;
        for (int i = 0; i < nouns.length; i++)
        {    
            if (!this.wordnet.isNoun(nouns[i])) continue;
            int distance = 0; 
            for (int j = 0; j < nouns.length; j++)
            {
                if (i == j || !this.wordnet.isNoun(nouns[j])) continue;
                distance += this.wordnet.distance(nouns[i], nouns[j]);
            }
            if (distance > maximum)
            {
                maximum = distance;
                castWord = nouns[i];
            }
        } 
        return castWord;
    }
    
    public static void main(String[] args) 
    {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }

}