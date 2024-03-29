/* Team Members
 * Harshitha Akkaraju
 * Shaarika Kaul
 */

package datastructures.concrete;

import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.ISet;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * See ISet for more details on what each method is supposed to do.
 */
public class ChainedHashSet<T> implements ISet<T> {
    // This should be the only field you need
    private IDictionary<T, Boolean> map;

    public ChainedHashSet() {
        this.map = new ChainedHashDictionary<>();
    }

    /**
     * Adds the given item to the set.
     *
     * If the item already exists in the set, this method does nothing.
     */    
    @Override
    public void add(T item) {
	    	if (!contains(item)) {
	    		this.map.put(item, true);
	    	}
    }
    
    /**
     * Removes the given item from the set.
     *
     * @throws NoSuchElementException  if the set does not contain the given item
     */
    @Override
    public void remove(T item) {
	    	if (!contains(item)) {
	    		throw new NoSuchElementException();
	    	}
	    	this.map.remove(item);
    }

    /**
     * Returns 'true' if the set contains this item and false otherwise.
     */
    @Override
	    public boolean contains(T item) {
	    	return this.map.containsKey(item); 	
    }
    
    /**
     * Returns the number of items contained within this set.
     */
    @Override
    public int size() {
        return this.map.size();
    }
    
    
    @Override
    public Iterator<T> iterator() {
        return new SetIterator<>(this.map.iterator());
    }


    private static class SetIterator<T> implements Iterator<T> {
        // This should be the only field you need
        private Iterator<KVPair<T, Boolean>> iter;

        public SetIterator(Iterator<KVPair<T, Boolean>> iter) {
            this.iter = iter;
        }
        
        /**
         *  Returns 'true' if there is another item in the set
         */
        @Override
        public boolean hasNext() {
            return iter.hasNext();
        }

        /**
         *  Returns the next item in the set
         */
        @Override
        public T next() {
        		return iter.next().getKey();
        }
    }
}
