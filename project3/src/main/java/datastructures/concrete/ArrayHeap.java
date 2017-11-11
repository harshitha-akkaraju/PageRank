package datastructures.concrete;

import datastructures.interfaces.IPriorityQueue;
import misc.exceptions.EmptyContainerException;
import misc.exceptions.NotYetImplementedException;

import java.util.Arrays;

/**
 * See IPriorityQueue for details on what each method must do.
 */
public class ArrayHeap<T extends Comparable<T>> implements IPriorityQueue<T> {
    // See spec: you must implement a implement a 4-heap.
    private static final int NUM_CHILDREN = 4;

    // You MUST use this field to store the contents of your heap.
    // You may NOT rename this field: we will be inspecting it within
    // our private tests.
    private T[] heap;
    // Feel free to add more fields and constants.
    private int size;
    
    public ArrayHeap(T[] heap) {
    	this.heap = makeArrayOfT(17);
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain elements of type T.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private T[] makeArrayOfT(int size) {
        // This helper method is basically the same one we gave you
        // in ArrayDictionary and ChainedHashDictionary.
        //
        // As before, you do not need to understand how this method
        // works, and should not modify it in any way.
        return (T[]) (new Comparable[size]);
    }

    
    //  ensure capacity
    
    
    @Override
    public T removeMin() {
    		if (this.size == 0) {
    			throw new EmptyContainerException();
		}
        this.size--;
    }

    @Override
    public T peekMin() {
    		if (this.size == 0) {
    			throw new EmptyContainerException();
    		}
    		return this.heap[0];
    }

    @Override
    public void insert(T item) {
    		if (item == null) {
    			throw new IllegalArgumentException();
    		}
    		this.heap[this.size] = item;
    		percolateUp(this.size);
    		this.size++;
    }
    
    private void percolateUp(int index) {
    		int parentIndex = (index - 1) / 4;
    		if (this.heap[index].compareTo(this.heap[parentIndex]) < 0) {
    			T temp = this.heap[parentIndex];
    			this.heap[parentIndex] = this.heap[index];
    			this.heap[index] = temp;
    		}
    		percolateUp(parentIndex);
    }

    @Override
    public int size() {
        return this.size;
    }
}
