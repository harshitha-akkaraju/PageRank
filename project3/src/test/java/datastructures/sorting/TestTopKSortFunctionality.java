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
    
	//  Tests simple usage
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
    
    //  Tests Strings
    @Test(timeout=SECOND)
    public void testStrings() {
    	IList<String> list = new DoubleLinkedList<>();
    	String alphabetTop10 = "qrstuvwxyz";
    	String alphabetReverse = "zyxwvutsrqpomnlkjihgfedcba";
        
    	for (int i = 0; i < 26; i++) {
    		list.add(alphabetReverse.charAt(i) + "");
    	}

        IList<String> top = Searcher.topKSort(10, list);
        assertEquals(10, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(alphabetTop10.charAt(i) + "", top.get(i));
        }
    }
    
    //  Tests Doubles
    @Test(timeout=SECOND)
    public void testDoubles() {
    	IList<Double> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i * 1.0);
        }

        IList<Double> top = Searcher.topKSort(5, list);
        assertEquals(5, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(15 + i * 1.0, top.get(i));
        }
    }
    
    
}
