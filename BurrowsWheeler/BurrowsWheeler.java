
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
    
    private static final int R = 256;
    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output 
    public static void transform()
    {
        String input = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(input);
        
        int first = 0;
        int length = input.length();

        for (int i = 0; i < length; i++)
            if (csa.index(i) == 0) first = i;
        BinaryStdOut.write(first);

        for (int offset = 0; offset < length; offset++)
        {
            char t = input.charAt((csa.index(offset) + length - 1) % length);
            BinaryStdOut.write(t);
        }
        BinaryStdOut.close();
    }

    // // apply Burrows-Wheeler inverse transform,
    // // reading from standard input and writing to standard output
    public static void inverseTransform()
    {
        int first = BinaryStdIn.readInt();
        String t = BinaryStdIn.readString();
        
        int N = t.length();
        int[] count = new int[R + 1];
        int[] next = new int[N];
        char[] aux = new char[N];
        for (int i = 0; i < N; i++)
            count[t.charAt(i) + 1]++;
         
        for (int r = 0; r < R; r++)
            count[r + 1] += count[r];
       
        for (int i = 0; i < N; i++)
        {
            int idx = count[t.charAt(i)]++;
            next[idx] = i;
            aux[idx] = t.charAt(i);
        }
            
        for (int i = 0; i < N; i++)
        {
            BinaryStdOut.write(aux[first]);
            first = next[first];
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args)
    {
        String sign = args[0];
        if (sign.equals("-")) BurrowsWheeler.transform();
        else if (sign.equals("+")) BurrowsWheeler.inverseTransform();
    }

}