package datastructures.sorting;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.Arrays;

import misc.BaseTest;
import datastructures.concrete.ArrayHeap;
import datastructures.interfaces.IPriorityQueue;
import misc.exceptions.EmptyContainerException;
import org.junit.Test;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestSortingStress extends BaseTest {
	    protected <T extends Comparable<T>> IPriorityQueue<T> makeInstance() {
	        return new ArrayHeap<>();
	    }
	    
	//  Make basic heap
	protected IPriorityQueue<Integer> makeBasicHeap() {
		IPriorityQueue<Integer> heap = this.makeInstance();
		heap.insert(2);
		heap.insert(5);
		heap.insert(1);
		heap.insert(6);
		heap.insert(1);
		heap.insert(2);
		heap.insert(9);
		heap.insert(2);
    return heap;
}
	

    // ARRAYHEAP STRESS TESTS
    // Test worst case for building a heap (when elements are in reverse sorted order)
    @Test(timeout=10*SECOND)
    public void testBuildHeapWorstCase() {
    		IPriorityQueue<Integer> heap = this.makeInstance();
    		int[] expected = new int[10000000];
    		for (int i = 10000000 - 1; i >= 0; i--) {
    			heap.insert(i);
    			expected[i] = i;
    		}
    		Arrays.sort(expected);
    		for (int i = 0; i < expected.length; i++) {
    			assertTrue(expected[i] == heap.removeMin());
    		}
    }
    
    //  Insert and remove a large amount of data and compare with expected out 
    @Test(timeout=10*SECOND)
    public void heapStressTest() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        int[] expected = new int[1000000];
        for (int i = 0; i < 1000000; i++) {
	        	heap.insert(i);
	        	expected[i] = i;
        }
        Arrays.sort(expected);
        for (int i = 0; i < 1000000; i++) {
        		int heapMin = heap.removeMin();
        		assertTrue(heapMin == expected[i]);
        }
    }
	
    
    //  SORTING STRESS TESTS
//    @Test(timeout=10*SECOND)
//    public void largeDataset() {
//    	int[] heap = new int[1000];
//    	for (int i = 0; i < 500; i++) {
//    		heap.(i + 10 - i *20);
//    	}
//    	int[]expected = new int[1000];
//    	expected = Arrays.sort(heap);;
//
//        assertTrue(true);
//    }
}
