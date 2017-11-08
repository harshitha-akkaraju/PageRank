/* Team Members
 * Harshitha Akkaraju
 * Shaarika Kaul
 */

package datastructures.concrete.dictionaries;

import datastructures.concrete.KVPair;
import datastructures.interfaces.IDictionary;
import misc.exceptions.NoSuchKeyException;
import misc.exceptions.NotYetImplementedException;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * See the spec and IDictionary for more details on what each method should do
 */
public class ChainedHashDictionary<K, V> implements IDictionary<K, V> {
    // You may not change or rename this field: we will be inspecting
    // it using our private tests.
    private IDictionary<K, V>[] chains;

    // You're encouraged to add extra fields (and helper methods) though!
    private int size;
    
    private int[] resizeValues;
    private int resizeIndex;
    
    public ChainedHashDictionary() {
        this.chains = makeArrayOfChains(11);
        this.size = 0;
        
        this.resizeValues = new int[] {19, 41, 83, 167, 331, 661, 1321, 2663}; //
        this.resizeIndex = 0; //
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain IDictionary<K, V> objects.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private IDictionary<K, V>[] makeArrayOfChains(int size) {
        // Note: You do not need to modify this method.
        // See ArrayDictionary's makeArrayOfPairs(...) method for
        // more background on why we need this method.
        return (IDictionary<K, V>[]) new IDictionary[size];
    }

    /**
     * Returns the value corresponding to the given key.
     *
     * @throws NoSuchKeyException if the dictionary does not contain the given key.
     */
    @Override
    public V get(K key) {
    		int index = indexOf(key, this.chains.length);
    		IDictionary<K, V> group = this.chains[index];
		if (group != null) {
			return group.get(key);
		}
    		throw new NoSuchKeyException();
    }

    /**
     * Adds the key-value pair to the dictionary. If the key already exists in the dictionary,
     * replace its value with the given one.
     */
    @Override
    public void put(K key, V value) {
    		ensureCapacity();
    		int index = indexOf(key, this.chains.length);
		if (this.chains[index] == null) {
    			this.chains[index] = new ArrayDictionary<K, V>();
    		}
    		if (!this.chains[index].containsKey(key)) {
    			this.size++;
    		}
    		this.chains[index].put(key, value);
    }

    /**
     * Remove the key-value pair corresponding to the given key from the dictionary.
     *
     * @throws NoSuchKeyException if the dictionary does not contain the given key.
     */
    @Override
    public V remove(K key) {
	    	if (!containsKey(key)) {
	    		throw new NoSuchKeyException();
	    	}
	    	int index = indexOf(key, this.chains.length);
		IDictionary<K, V> group = this.chains[index];
		this.size--;
		return group.remove(key);
    }

    /**
     * Returns 'true' if the dictionary contains the given key and 'false' otherwise.
     */
    @Override
    public boolean containsKey(K key) {
	    	int index = indexOf(key, this.chains.length);
	    	IDictionary<K, V> group = this.chains[index];
	    	if (group != null) {
	    		return group.containsKey(key);
	    	}
	    	return false;
	}

    /**
     * Returns the number of key-value pairs stored in this dictionary.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Returns a list of all key-value pairs contained within pairs.
     */
    @Override
    public Iterator<KVPair<K, V>> iterator() {
        // Note: you do not need to change this method
        return new ChainedIterator<>(this.chains);
    }
    
    /**
     * Returns the index where the the key could be hashed to.
     * @param key
     * @param size
     * @return
     */
    private int indexOf(K key, int size) {
    		if (key == null) {
    			return 0;
    		}
    		return Math.abs(key.hashCode() % size);
    }
    
    /** Helper method
     * If pairs is full, ensureCapacity doubles the size of pairs so it can hold at least this.size elements.
     */
    private void ensureCapacity() {
    		double loadFactor = this.size / this.chains.length;
    		if (loadFactor > 0.75) {
    			IDictionary<K, V>[] temp;
    			if (this.resizeIndex < this.resizeValues.length) {
    				 temp = makeArrayOfChains(this.resizeValues[this.resizeIndex]);
    			} else {
    				temp = makeArrayOfChains(this.chains.length * 2);
    			}
    			for (int i = 0; i < this.chains.length; i++) {
    				if (this.chains[i] != null) {
    					Iterator<KVPair<K, V>> itr = this.chains[i].iterator();
    					while (itr.hasNext()) {
    						KVPair<K, V> pair = itr.next();
    						int index = indexOf(pair.getKey(), temp.length);
    						if (temp[index] == null) {
    							temp[index] = new ArrayDictionary<K, V>();
    						}
    						temp[index].put(pair.getKey(), pair.getValue());
    					}
    				}
    			}
    			this.chains = temp;
    			this.resizeIndex++;
    		}
    }
    
    /**
     * Hints:
     *
     * 1. You should add extra fields to keep track of your iteration
     *    state. You can add as many fields as you want. If it helps,
     *    our reference implementation uses three (including the one we
     *    gave you).
     *
     * 2. Before you try and write code, try designing an algorithm
     *    using pencil and paper and run through a few examples by hand.
     *
     * 3. Think about what exactly your *invariants* are. An *invariant*
     *    is something that must *always* be true once the constructor is
     *    done setting up the class AND must *always* be true both before and
     *    after you call any method in your class.
     *
     *    Once you've decided, write them down in a comment somewhere to
     *    help you remember.
     *
     *    You may also find it useful to write a helper method that checks
     *    your invariants and throws an exception if they're violated.
     *    You can then call this helper method at the start and end of each
     *    method if you're running into issues while debugging.
     *
     *    (Be sure to delete this method once your iterator is fully working.)
     *
     * Implementation restrictions:
     *
     * 1. You **MAY NOT** create any new data structures. Iterators
     *    are meant to be lightweight and so should not be copying
     *    the data contained in your dictionary to some other data
     *    structure.
     *
     * 2. You **MAY** call the `.iterator()` method on each IDictionary
     *    instance inside your 'chains' array, however.
     */
    
    //  1. go thru chains, if empty here, go next. if not, create iterator over arrayDictionary chaingroup
    //  until you reach index chains.length - 1
    private static class ChainedIterator<K, V> implements Iterator<KVPair<K, V>> {
        private IDictionary<K, V>[] chains;
        private int hashIndex;
        private int chainIndex;
        
        public ChainedIterator(IDictionary<K, V>[] chains) {
            this.chains = chains;
            this.hashIndex = 0;
            this.chainIndex = 0;
        }

        /**
         * Returns 'true' if the iterator still has elements to look at;
         * returns 'false' otherwise.
         */
        @Override
        public boolean hasNext() {
        		// increments hashIndex until it reaches an occupied index
        		while (this.hashIndex < this.chains.length && this.chains[this.hashIndex] == null) {
        			this.hashIndex++;
        		}
        		// base case
        		if (this.hashIndex >= this.chains.length) {
        			return false;
        		}
        		Iterator<KVPair<K, V>> itr = this.chains[this.hashIndex].iterator();
        		// movies the pointer to the last retrieved value
        		for (int i = 0; i < this.chainIndex; i++) {
        			itr.next();
        		}
        		if (itr.hasNext()) {
        			return itr.hasNext();
        		}
        		// when at the end of the chain
        		this.hashIndex++;
        		this.chainIndex = 0; // reset position
        		return hasNext();
         }

        /**
         * Returns the next item in the iteration and internally updates the
         * iterator to advance one element forward.
         * 
         * @throws NoSuchElementException if we have reached the end of the iteration and
         * there are no more elements to look at.
         */
        @Override
        public KVPair<K, V> next() {
        		if (!hasNext()) {
        			throw new NoSuchElementException();
        		}
        		IDictionary<K, V> group = this.chains[hashIndex];
        		Iterator<KVPair<K, V>> itr = group.iterator();
        		for (int i = 0; i < this.chainIndex; i++) {
        			itr.next();
        		}
        		this.chainIndex++;
    			return itr.next();
        }
    }
}

