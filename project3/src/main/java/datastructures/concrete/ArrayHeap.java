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
    private int heapSize;
    private int index;
    private int nodesAti;
    
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
    	// T temp = heap[1];
    	// heap [1] = heap[heap[heapSize - 1]];
    	// nodeValue = heap[2];
    	// while (temp > nodeValue) {
    	// 
    	
        throw new NotYetImplementedException();
        //heapSize--;
    }

    @Override
    public T peekMin() {
    		return this.heap[0];
    }

    @Override
    public void insert(T item) {
    	// 4*i, 4i + 1, 4*i + 2, 4*i + 3, 4*1 + 3 4*i + 4
    		if (this.heapSize == 0) {
    			this.heap[0] = item;
    		} else if (nodesAti > 4) { 
    			this.nodesAti = 0;
    			this.index++;
    		} else {
    			this.nodesAti++;
    			this.heap[(4 * this.index + this.nodesAti)] = item;	
    		}
    		this.heapSize++;
    }

    @Override
    public int size() {
        return this.heapSize;
    }
}
