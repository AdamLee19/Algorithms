import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Queue;

import java.util.ArrayList;

public class SAP {
    private class Cache
    {
        public int v;
        public int w;
        public int sht;
        public int acstr;
        public Iterable<Integer> V;
		public Iterable<Integer> W;
        Cache()
        {
            this.v = -1;
            this.w = -1;
            this.V = null;
            this.W = null;
            this.sht = -1;
            this.acstr = -1;
        } 

        public void set(int v, int w)
        {
            this.v = v;
            this.w = w;
            this.sht = -1;
            this.acstr = -1;
        }
        
        
        public void set(Iterable<Integer> V, Iterable<Integer> W)
        {
            this.V = V;
            this.W = W;
            this.sht = -1;
            this.acstr = -1;
        } 
    }
    // constructor takes a digraph (not necessarily a DAG)
    private final Digraph G;
    private Cache cache;
    public SAP(Digraph G)
    {
        
        if (G == null) throw new IllegalArgumentException();
        this.G = new Digraph(G);
        cache = new Cache();
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w)
    {
        if (v < 0 || v >= G.V()) throw new IllegalArgumentException();
        if (w < 0 || w >= G.V()) throw new IllegalArgumentException();

        BreadthFirstDirectedPaths vAnc = new BreadthFirstDirectedPaths(this.G, v);
        BreadthFirstDirectedPaths wAnc = new BreadthFirstDirectedPaths(this.G, w);
        cache.set(v, w);
        reachable(vAnc, wAnc);
        return cache.sht;  
    }
    private void reachable(BreadthFirstDirectedPaths v, BreadthFirstDirectedPaths w)
    {
        Queue<Integer> queue = new Queue<Integer>();
        for (int i = 0; i < this.G.V(); i++)
            if (v.hasPathTo(i) && w.hasPathTo(i))
                queue.enqueue(i);
        if (queue.isEmpty())
        {
            cache.acstr = -1;
            cache.sht = -1;
            return;
        }
           
        int shortest = Integer.MAX_VALUE;
        for (int a : queue)
        {
            int dist1 = v.distTo(a);
            // if (dist1 >= shortest) continue;
            int dist2 = w.distTo(a);
            // if (dist2 >= shortest) continue;
            
            if (dist1 + dist2 < shortest)
            {
                shortest = dist1 + dist2;
                cache.acstr = a;
                cache.sht = shortest;
            }
        }
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w)
    {
        if (v < 0 || v >= G.V()) throw new IllegalArgumentException();
        if (w < 0 || w >= G.V()) throw new IllegalArgumentException();
       

        BreadthFirstDirectedPaths vAnc = new BreadthFirstDirectedPaths(this.G, v);
        BreadthFirstDirectedPaths wAnc = new BreadthFirstDirectedPaths(this.G, w);
        cache.set(v, w);
        reachable(vAnc, wAnc);
        return cache.acstr;   
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w)
    {
        if (v == null) throw new IllegalArgumentException();
        if (w == null) throw new IllegalArgumentException();
        for (Integer x : v) if (x == null || x < 0 || x >= G.V()) throw new IllegalArgumentException();
		for (Integer x : w) if (x == null || x < 0 || x >= G.V()) throw new IllegalArgumentException();
		if (!v.iterator().hasNext() || !w.iterator().hasNext()) return -1;


        BreadthFirstDirectedPaths vAnc = new BreadthFirstDirectedPaths(this.G, v);
        BreadthFirstDirectedPaths wAnc = new BreadthFirstDirectedPaths(this.G, w);
        cache.set(v, w);
        reachable(vAnc, wAnc);
        return cache.sht; 
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
    {
        if (v == null) throw new IllegalArgumentException();
        if (w == null) throw new IllegalArgumentException();
        for (Integer x : v) if (x == null || x < 0 || x >= G.V()) throw new IllegalArgumentException();
		for (Integer x : w) if (x == null || x < 0 || x >= G.V()) throw new IllegalArgumentException();
		if (!v.iterator().hasNext() || !w.iterator().hasNext()) return -1;

        // if (cache.V != null && cache.W != null && cache.V.equals(v) && cache.W.equals(w))
        // {
        //     int acstr = cache.acstr;
        //     cache.set(null, null);
        //     return acstr;
        // }

        BreadthFirstDirectedPaths vAnc = new BreadthFirstDirectedPaths(this.G, v);
        BreadthFirstDirectedPaths wAnc = new BreadthFirstDirectedPaths(this.G, w);
        cache.set(v, w);
        reachable(vAnc, wAnc);
        return cache.acstr;
    }

    public static void main(String[] args) 
    {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        // ArrayList<Integer> a = new ArrayList<Integer>();
        // a.add(0);
        // a.add(null);
        // a.add(9);
        // a.add(12);
        // a.add(7);
        // ArrayList<Integer> b = new ArrayList<Integer>();
        // b.add(1);
        // b.add(2);
        // b.add(4);
        // b.add(5);
        // b.add(10);
        // int length = sap.length(a, b);
        // int ancestor = sap.ancestor(a, b);
        //  StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        while (!StdIn.isEmpty()) 
        {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
        
    }    
} 