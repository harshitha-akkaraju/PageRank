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
        	System.out.println("Cannot insert a null value into the heap");
        }
    }
    
    // Test removing from an empty heap
    @Test(timeout=SECOND)
    public void testEmptyHeapErrorHandling() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
        	heap.removeMin();
        } catch (EmptyContainerException e) {
        	System.out.println("Cannot call removeMin(), the heap is empty");
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
    
    //  Multi call test
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
		assertTrue(matches(expected, actual));
		
		try {
			heap.insert(null);
		} catch (IllegalArgumentException e) {
				
		}
    }
    
    //  Test adding and removing Strings
    @Test(timeout=SECOND)
    public void testStrings() {
    	IPriorityQueue<String> heap = new ArrayHeap<String>();    	
    	heap.insert("Shaarika");
    	heap.insert("Harshitha");
    	heap.insert("Evan");
    	heap.insert("Anran");
    	heap.insert("Kaiyu");
    	
    	assertEquals(heap.removeMin(), "Anran");
    	assertEquals(heap.removeMin(), "Evan");
    	
    	String[] expected = new String[] {"Harshitha", "Kaiyu", "Shaarika"};
    	for (int i = 0; i < heap.size(); i++) {
    		expected[i] = heap.removeMin();
    	}
    	
    }
    
    // Test adding and removing Doubles
    @Test(timeout=SECOND)
    public void testDoubles() {
    	IPriorityQueue<Double> heap = new ArrayHeap<Double>();
    	heap.insert(2.0);
    	heap.insert(3.7);
    	heap.insert(19.5);
    	heap.insert(19.4);
    	heap.insert(-200.1);
    	heap.insert(15.3);
    	heap.insert(70.5);
    	
    	assertEquals(heap.removeMin(), -200.1);
    	assertEquals(heap.removeMin(), 2.0);
    	
    	Double[] expected = new Double[] {3.7, 15.3, 19.4, 19.5, 70.5};
    	for (int i = 0; i < heap.size(); i++) {
    		expected[i] = heap.removeMin();
    	}
    }
    
}
