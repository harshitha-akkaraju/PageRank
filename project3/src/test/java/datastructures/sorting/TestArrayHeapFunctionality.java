package datastructures.sorting;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

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

    
    @Test(timeout=SECOND)
    public void testBasicSize() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        assertEquals(1, heap.size());
    }
    
    @Test(timeout=SECOND)
    public void testInsertBasic() {
    		IPriorityQueue<Integer> heap = this.makeBasicHeap();
    		assertEquals(8, heap.size());
    }
    
    @Test(timeout=SECOND)
    public void testRemoveBasic() {
		IPriorityQueue<Integer> heap = this.makeBasicHeap();
		// ArrayHeap<Integer> heap = new ArrayHeap();
		int result = heap.removeMin();
		assertEquals(1, result);
    }
    
    // Write tests for
    // insert and remove
    
    //  trying to insert null
    @Test(timeout=SECOND)
    public void insertNull() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
        heap.insert(null);
        } catch (IllegalArgumentException e) {
        	
        }
    }
    
    //  trying to remove from an empty heap
    @Test(timeout=SECOND)
    public void removeEmptyHeap() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
        heap.removeMin();
        } catch (EmptyContainerException e) {
        	
        }
    }
    
    
    //  trying to peek on an empty heap
    @Test(timeout=SECOND)
    public void peekEmptyHeap() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
        heap.peekMin();
        } catch (EmptyContainerException e) {
        	
        }
    }
    
    //  trying to call size on an empty heap
    @Test(timeout=SECOND)
    public void sizeEmptyHeap() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
        heap.size();
        } catch (EmptyContainerException e) {
        	
        }
    }
    
    //  stress test
    @Test(timeout=10*SECOND)
    public void stressTest() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 500; i++) {
        	heap.insert(i + 3);
        	heap.insert(i * 2);
        }
    }
    
    
}
