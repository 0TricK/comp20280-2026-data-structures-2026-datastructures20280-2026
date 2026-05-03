package project20280.hashtable;

import project20280.interfaces.Entry;

import java.util.ArrayList;

public class ProbeHashMap<K, V> extends AbstractHashMap<K, V> {
    private MapEntry<K, V>[] table;
    private final MapEntry<K, V> DEFUNCT = new MapEntry<>(null, null);

    public ProbeHashMap() {
        super();
    }

    /**
     * Creates a hash table with given capacity and prime factor 109345121.
     */
    public ProbeHashMap(int cap) {
        super(cap);
    }

    /**
     * Creates a hash table with the given capacity and prime factor.
     */
    public ProbeHashMap(int cap, int p) {
        super(cap, p);
    }

    @Override
    protected void createTable() {
        table = new MapEntry[capacity];
    }

    int findSlot(int h, K k) {
        int available = -1;           // index of first DEFUNCT slot seen
        int j = h;                    // index of current probe

        do {
            if (table[j] == null || table[j] == DEFUNCT) {
                if (available == -1) available = j;
                if (table[j] == null) break;
            } else if (table[j].getKey().equals(k)) {
                return j;
            }
            j = (j + 1) % capacity;
        } while (j != h);             // stops if we've wrapped all the way back to start

        return -(available + 1);
    }

    @Override
    protected V bucketGet(int h, K k) {
        int j = findSlot(h, k);
        if (j < 0) return null;       // negative result means key was not found
        return table[j].getValue();
    }

    @Override
    protected V bucketPut(int h, K k, V v) {
        int j = findSlot(h, k);

        if (j >= 0) {
            return table[j].setValue(v);
        }

        table[-(j + 1)] = new MapEntry<>(k, v);
        n++;
        return null;
    }

    @Override
    protected V bucketRemove(int h, K k) {
        int j = findSlot(h, k);
        if (j < 0) return null;

        V old = table[j].getValue();
        table[j] = DEFUNCT;
        n--;
        return old;
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        ArrayList<Entry<K, V>> entries = new ArrayList<>();
        for (int j = 0; j < capacity; j++) {
            if (!(table[j] == null || table[j] == DEFUNCT)) {
                entries.add(table[j]);
            }
        }
        return entries;
    }
}
