import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 8;

    private Item[] queue;
    private int n;
    // construct an empty randomized queue
    public RandomizedQueue()
    {
        queue = (Item[]) new Object[INIT_CAPACITY];
        n = 0;
    }



    // is the randomized queue empty?
    public boolean isEmpty()
    {
        return n == 0;
    }
      
    // return the number of items on the randomized queue
    public int size()
    {
        return n;
    }
    private void resize(int capacity)
    {
        assert capacity >= n;
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++)
            copy[i] = queue[i];
        queue = copy;
    } 
    // add the item
    public void enqueue(Item item)
    {
        if (item == null) throw new IllegalArgumentException();
        if (n == queue.length) resize(2 * queue.length);
        queue[n++] = item;
    }
    
    // remove and return a random item
    public Item dequeue()
    {
       if (n == 0) throw new NoSuchElementException();
       int pos = StdRandom.uniform(n);
       Item itemRemove = queue[pos];
       queue[pos] = queue[--n];
       queue[n] = null;
       
       // Half size of array when array is 1/4 full
       if (n > 0 && n == queue.length/4) resize(queue.length/2);
       return itemRemove;
    }
    
    // return a random item (but do not remove it)
    public Item sample()
    {
        if (n == 0) throw new NoSuchElementException();
        int pos = StdRandom.uniform(n);
        return queue[pos];
    }
     
    // return an independent iterator over items in random order
    public Iterator<Item> iterator()
    {
        return new ArrayIterator();  
    }
    private class ArrayIterator implements Iterator<Item> 
    {
        int iterCount;
        private int[] indexQueue; 

        // Shuffle the index array
        public ArrayIterator() 
        {
            indexQueue = new int[n];
            for (int i = 0; i < indexQueue.length; i++) indexQueue[i] = i;
            StdRandom.shuffle(indexQueue);
        }

        public boolean hasNext()  { return iterCount != n;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return queue[indexQueue[iterCount++]];
        }
    }
   
    // unit testing (required)
    public static void main(String[] args)
    {
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();

        StdOut.println("Is Empty? " + q.isEmpty());
        StdOut.println("Size: " + q.size());

    }

}