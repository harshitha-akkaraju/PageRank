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
    
    public ArrayHeap() {
    	this.heap = makeArrayOfT(10);
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
    
    /**
     * Removes and return the smallest element in the queue.
     *
     * If two elements within the queue are considered "equal"
     * according to their compareTo method, this method may break
     * the tie arbitrarily and return either one.
     *
     * @throws EmptyContainerException  if the queue is empty
     */
    @Override
    public T removeMin() {
    	if (this.size == 0) {
    		throw new EmptyContainerException();
		}
   		T min = this.heap[0];
   		this.heap[0] = this.heap[this.size - 1];
        this.size--;
        percolateDown(0);
        if (this.size != 0 && this.heap.length / this.size <= 0.25) {
       		downSize();
        }
        return min;
    }

    /**
     * Returns, but does not remove, the smallest element in the queue.
     *
     * This method must break ties in the same way the removeMin
     * method breaks ties.
     *
     * @throws EmptyContainerException  if the queue is empty
     */
    @Override
    public T peekMin() {
    	if (this.size == 0) {
   			throw new EmptyContainerException();
   		}
   		return this.heap[0];
    }

    /**
     * Inserts the given item into the queue.
     *
     * @throws IllegalArgumentException  if the item is null
     */
    @Override
    public void insert(T item) {
    	if (item == null) {
    		throw new IllegalArgumentException();
   		}
   		if (this.size == this.heap.length) {
   			ensureCapacity();
   		}
   		this.heap[this.size] = item;
    	percolateUp(this.size);
    	this.size++;
    }
    
    /**
     * Returns the number of elements contained within this queue.
     */
    @Override
    public int size() {
        return this.size;
    }
    
    /**
     * Starts at the given index and recursively swaps the parent with the child if parent
     * is greater than the child
     * @param index
     */
    private void percolateUp(int index) {
    	int parentIndex = (index - 1) / NUM_CHILDREN;
    	if (index > 0 && this.heap[index].compareTo(this.heap[parentIndex]) < 0) {
   			T temp = this.heap[parentIndex];
   			this.heap[parentIndex] = this.heap[index];
   			this.heap[index] = temp;
   			percolateUp(parentIndex);
   		}
    }
    
    /**
     * Starts at the given index and replaces the parent with it's smallest child recursively
     * down the tree
     * @param index
     */
    private void percolateDown(int index) {
    	int childIndex = NUM_CHILDREN * index;
    	int minIndex = index;
   		T minElement = this.heap[index];
   		int count = 0;
		while (childIndex < this.size && count < NUM_CHILDREN) {
			childIndex++;
			count++;
			if (minElement.compareTo(this.heap[childIndex]) > 0) {
				minElement = this.heap[childIndex];
				minIndex = childIndex;
			}
		}
    		// swap with the smallest child
   		T temp = this.heap[index];
   		this.heap[index] = minElement;
   		this.heap[minIndex] = temp;
   		if (minIndex != index) {
   			percolateDown(minIndex);
    	}
    }
    
    /** Helper method
     * If 'heap' is full, ensureCapacity doubles the size of heap so it can hold at least this.size elements.
     */
    private void ensureCapacity() {
    	int newSize = this.size * 2;
   		T[] temp = makeArrayOfT(newSize);
   		for (int i = 0; i < this.size; i++) {
   			temp[i] = this.heap[i];
   		}
   		this.heap = temp;
    }
    
    /** Helper method
     * If the heap is less than 25% full, the heap size is reduced by half
     */
    private void downSize() {
		T [] temp = makeArrayOfT(this.heap.length / 2);
		for (int i = 0; i < this.heap.length; i++) {
			temp[i] = this.heap[i];
		}
		this.heap = temp;
    }
}
