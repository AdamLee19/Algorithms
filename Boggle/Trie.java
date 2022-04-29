
public class Trie<Integer>
{
    private static final int R = 26;
    private Node root = new Node();
    private int nKey;
    private static class Node
    {
        private int value = -1;
        private Node[] next = new Node[R];
    }
    public Trie() 
    { nKey = 0; }

    public void put(String key, int val)
    { root = put(root, key, val, 0); }

    private Node put(Node x, String key, int val, int d)
    {
        if (x == null) x = new Node();
        if (d == key.length()) 
        { 
            x.value = val; 
            nKey += 1; 
            return x; 
        }
        char c = key.charAt(d);
        x.next[c - 'A'] = put(x.next[c - 'A'], key, val, d+1);
        return x;
    }
    public boolean contains(String key)
    { return get(key) == 1; }

    public int get(String key)
    {
        Node x = get(root, key, 0);
        if (x == null) return -1;
        return x.value;
    }
    
    private Node get(Node x, String key, int d)
    {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c - 'A'], key, d+1);
    }
    public boolean hasPrefix(String key)
    { return prefixCheck(key); }

    private boolean prefixCheck(String key)
    { 
        Node x = get(root, key, 0);
        if (x == null) return false;
        return true;
    }


    public int size()
    { return nKey; }

    public boolean isEmpty()
    { return size() == 0; }
}