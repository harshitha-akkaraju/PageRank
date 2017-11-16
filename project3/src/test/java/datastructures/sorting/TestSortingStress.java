package datastructures.sorting;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Random;

import misc.BaseTest;
import misc.Searcher;
import datastructures.concrete.ArrayHeap;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import datastructures.interfaces.IPriorityQueue;
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
    
    //  SORTING STRESS TEST
    //  Test ordering randomly generated integers
    @Test(timeout=20*SECOND)
    public void randomIntStressTest() {
    	Random r = new Random();
    	List<Integer> expected = new LinkedList<Integer>();
    	IList<Integer> actual = new DoubleLinkedList<Integer>();
    	for (int i = 0; i < 100000; i++) {
    		int rand = r.nextInt(11);
    		expected.add(rand);
    		actual.add(rand);
    	}
    	Collections.sort(expected); 
    	actual = Searcher.topKSort(100000, actual); 	
    	for (int i = 0; i < 100000; i++) {
    		assertEquals(expected.get(i), (actual.get(i)));
    	}

    }

}
