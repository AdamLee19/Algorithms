import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;

import java.util.HashMap;
import java.util.ArrayList;

public class WordNet {

    private final Digraph wn;
    private final SAP sap;
    private final HashMap<String, ArrayList<Integer>> nouns = new HashMap<String, ArrayList<Integer>>();
    private final HashMap<Integer, String> synsets = new HashMap<Integer, String>();
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms)
    {
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException();
        
        int size = 0;
        In in = new In(synsets);
        while (!in.isEmpty()) 
        {
            String ss = in.readLine();
            String[] fileds = ss.split(",");
            
            int synID = Integer.parseInt(fileds[0]);
            this.synsets.put(synID, fileds[1]);
            for (String n : fileds[1].split(" "))
            {
                if (!nouns.containsKey(n)) nouns.put(n, new ArrayList<Integer>());
                nouns.get(n).add(synID);
            }
            size++;
        }
        in.close();
        wn = new Digraph(size);

        in = new In(hypernyms);
        while (!in.isEmpty()) 
        {
            String[] line = in.readLine().split(",");
            int synset = Integer.parseInt(line[0]);
            for (int i = 1; i < line.length; i++) 
                wn.addEdge(synset, Integer.parseInt(line[i]));
           
        }
        in.close();

        DirectedCycle finder = new DirectedCycle(wn);
        if (finder.hasCycle()) throw new IllegalArgumentException("Given graph is not rooted DAG");

        int outDeg = 0;
        for (int i = 0; i < wn.V(); i++)
        {
            if (wn.outdegree(i) == 0) outDeg++;
            if (outDeg > 1)  throw new IllegalArgumentException("Given graph is not rooted DAG"); 
        }
            
        

        
        sap = new SAP(wn);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns()
    {
        return nouns.keySet(); 
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word)
    {
        
        if (word == null) throw new IllegalArgumentException();
        return nouns.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB)
    {
        if (nounA == null || nounB == null) throw new IllegalArgumentException();
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        ArrayList<Integer> v = this.nouns.get(nounA);
        ArrayList<Integer> w = this.nouns.get(nounB);
		
		return this.sap.length(v, w);
    }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB)
   {

        if (nounA == null || nounB == null) throw new IllegalArgumentException();
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
		
		ArrayList<Integer> v = this.nouns.get(nounA);
		ArrayList<Integer> w = this.nouns.get(nounB);
		
		int ancestor = this.sap.ancestor(v, w);
		
		return this.synsets.get(ancestor);
   }

   // do unit testing of this class
    public static void main(String[] args)
    {
        WordNet w = new WordNet("synsets6.txt", "hypernyms6InvalidCycle+Path.txt");
        // for (String s : w.nouns())
        // {
        //     StdOut.println(s);
        // }
        StdOut.println(w.isNoun("a"));
        StdOut.println(w.isNoun("Hello"));  
    }
}
