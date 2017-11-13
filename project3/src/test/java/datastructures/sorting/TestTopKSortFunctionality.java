package datastructures.sorting;

import misc.BaseTest;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import misc.Searcher;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.Arrays;

import datastructures.concrete.ArrayHeap;
import datastructures.interfaces.IPriorityQueue;
import misc.exceptions.EmptyContainerException;

/**
 * See spec for details on what kinds of tests this class should include.
 * 
 */


public class TestTopKSortFunctionality extends BaseTest {
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
	
	@Test(timeout=SECOND)
    public void testSimpleUsage() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }

        IList<Integer> top = Searcher.topKSort(5, list);
        assertEquals(5, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(15 + i, top.get(i));
        }
    }
    
    // TODO
    @Test(timeout=SECOND)
    public void testStrings() {
    	
    }
    
    // TODO
    @Test(timeout=SECOND)
    public void testDoubles() {
    	
    }

    
}
