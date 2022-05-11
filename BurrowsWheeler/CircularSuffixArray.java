import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {

    private String string;
    private Integer[] index;

   
    // circular suffix array of s
    public CircularSuffixArray(String s)
    {
        if (s == null) throw new IllegalArgumentException();
        
        string = s;
        index = new Integer[s.length()];
        for (int i = 0; i < string.length(); ++i) index[i] = i;

        Arrays.sort(index, csaOrder());
    }
    private Comparator<Integer> csaOrder() 
    {
        return new csaOrderComp();
    }
    private class csaOrderComp implements Comparator<Integer>
    {
        private final int length = string.length();
        public int compare(Integer i1, Integer i2)
        {
            
            for (int i = 0; i < length; i++) 
            {
                char c1 = string.charAt((i + i1) % length);
                char c2 = string.charAt((i + i2) % length);
                if (c1 > c2) return 1;
                if (c1 < c2) return -1;
            }
            return 0;
        } 
    }
    // // length of s
    public int length()
    {
        return string.length();
    }

    // // returns index of ith sorted suffix
    public int index(int i)
    {
        if (i < 0 || i >= string.length()) throw new IllegalArgumentException();
        return index[i];
    }

    // unit testing (required)
    public static void main(String[] args)
    {
        CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");
        StdOut.println(csa.index(0));
    }

}