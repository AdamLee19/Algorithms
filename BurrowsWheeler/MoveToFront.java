
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import edu.princeton.cs.algs4.StdOut;

public class MoveToFront 
{

    private static class CharList
    {
        private class Node 
        {
            private char item;
            private Node next;

            private Node(char item, Node next)
            {
                this.item = item;
                this.next = next;
            }
        }
        private Node dummy;

        private CharList()
        {
            for (int i = R - 1; i >= 0; i--)
                dummy = new Node((char) i, dummy);
            dummy = new Node('a', dummy);
        }
    
        private int encode(char c)
        {
            int i = 0;
            for (Node x = dummy; x.next != null; x = x.next, i++)
                if (x.next.item == c)
                {
                    x.next = x.next.next;
                    break;
                }
            dummy.next = new Node(c, dummy.next);
            return i;
        }
        private char decode(int idx)
        {
            Node x = dummy;
            char c = ' ';
            for (int i = 0; i < R; i++, x = x.next)
                if (idx == i)
                {
                    c = x.next.item;
                    x.next = x.next.next;
                    break;
                }
            dummy.next = new Node(c, dummy.next);
            return c; 
        }    
    }
    
    private static final int R = 256;
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode()
    {
        CharList cl = new CharList();

        while (!BinaryStdIn.isEmpty())
        {
            char c = BinaryStdIn.readChar();
            int idx = cl.encode(c);
            BinaryStdOut.write(idx, 8);     
        }
        BinaryStdOut.close();   
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode()
    {
        CharList cl = new CharList();

        while (!BinaryStdIn.isEmpty())
        {
            char in = BinaryStdIn.readChar();
            char out = cl.decode((int) in);
            BinaryStdOut.write(out);     
        }
        BinaryStdOut.close();   
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args)
    {
        String sign = args[0];
        if (sign.equals("-")) MoveToFront.encode();
        else if (sign.equals("+")) MoveToFront.decode();
    }
}