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

public class TestArrayHeapFunctionality extends BaseTest {
    protected <T extends Comparable<T>> IPriorityQueue<T> makeInstance() {
        return new ArrayHeap<>();
    }
    
    // Make a basic heap
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
    
    // Test the size of the heap
    @Test(timeout=SECOND)
    public void testBasicSize() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        assertEquals(1, heap.size());
    }
    
    // Empty the heap 
    @Test(timeout=SECOND)
    public void testAddAndRemoveBasic() {
    		IPriorityQueue<Integer> heap = this.makeBasicHeap();
    		int[] expected = {1, 1, 2, 2, 2, 5, 6, 9};
    		int[] actual = new int[heap.size()];
    		for (int i = 0; i < actual.length; i++) {
    			actual[i] = heap.removeMin();
    		}
    		assertTrue(matches(expected, actual));
    }
    
    // Tests if the expected out matches the actual output
    private boolean matches(int[] expected, int[] actual) {
    		assertEquals(expected.length, actual.length);
    		boolean matches = true;
    		for (int i = 0; i < expected.length; i++) {
    			matches = expected[i] == actual[i];
    		}
    		return matches;
    } 
    
    // Make a heap with repeated values
    @Test(timeout=SECOND)
    public void testInsertBasic() {
    		IPriorityQueue<Integer> heap = this.makeBasicHeap();
    		assertEquals(8, heap.size());
    }
    
    // Test two consecutive removes
    @Test(timeout=SECOND)
    public void testRemoveBasic() {
		IPriorityQueue<Integer> heap = this.makeBasicHeap();
		int result = heap.removeMin();
		assertEquals(1, result);
		result = heap.removeMin();
		assertEquals(1, result);
    }
        
    //  Test inserting a null value into the heap
    @Test(timeout=SECOND)
    public void testNullInsertErrorHandling() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
        		heap.insert(null);
        } catch (IllegalArgumentException e) {
        		System.out.println("You attempted to insert a null value into the heap.");
        }
    }
    
    // Test removing from an empty heap
    @Test(timeout=SECOND)
    public void testEmptyHeapErrorHandling() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
        		heap.removeMin();
        } catch (EmptyContainerException e) {
        		System.out.println("Cannot call removeMin(), the heap is empty.");
        }
    }
        
    //  Test peekMin() on an empty heap
    @Test(timeout=SECOND)
    public void testPeekMinErrorHandling() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
        		heap.peekMin();
        } catch (EmptyContainerException e) {
        	
        }
    }
    
    //  Test .size() on an empty heap
    @Test(timeout=SECOND)
    public void testSizeErrorHandling() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
        heap.size();
        } catch (EmptyContainerException e) {
        	
        }
    }
    
    //  Insert and remove a large amount of data and compare with expected out 
    @Test(timeout=10*SECOND)
    public void stressTest() {
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
    
    //  Test adding repeated values
    @Test(timeout=SECOND)
    public void addRemoveSame() {
		IPriorityQueue<Integer> heap = this.makeBasicHeap();
		int[] expected = new int[10];
		for (int i = 0; i < 10; i++) {
			if (i % 2 == 0) {
				heap.insert(0);
				expected[i] = 0;
			} else {
				heap.insert(1);
				expected[i] = 1;
			}
		}
		Arrays.sort(expected);
		for (int i = 0; i < 10; i++) {
			assertTrue(expected[i] == heap.removeMin());
		}
    }
    
    //  multi call test
    @Test(timeout=SECOND)
    public void longMultiCallTest() {
		IPriorityQueue<Integer> heap = this.makeBasicHeap();
		assertTrue(heap.size() == 8);
		heap.removeMin();
		assertTrue(heap.size() == 7);
		heap.removeMin();
		assertTrue(heap.peekMin() == 2);
		heap.insert(25);
		heap.insert(12);
		heap.insert(6);
		
		int[] expected = {2, 2, 2, 5, 6, 6, 9, 12, 25};
		int[] actual = new int[heap.size()];
		for (int i = 0; i < actual.length; i++) {
			actual[i] = heap.removeMin();
		}
		System.out.println(Arrays.toString(actual));
		assertTrue(matches(expected, actual));
		
		try {
			heap.insert(null);
		} catch (IllegalArgumentException e) {
				
		}
    }
    
    
    
}
