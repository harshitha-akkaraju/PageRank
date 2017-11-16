package datastructures.sorting;

import misc.BaseTest;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import misc.Searcher;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.Collections;
import java.util.List;
import java.util.LinkedList;

/**
 * See spec for details on what kinds of tests this class should include.
 * 
 */


public class TestTopKSortFunctionality extends BaseTest {
    
	
	//  When k = 0
	@Test(timeout=SECOND)
    public void testKEqualsZero() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        IList<Integer> top = Searcher.topKSort(0, list);
        assertEquals(0, top.size());
    }
	
	//  When k > size
	@Test(timeout=SECOND)
    public void testKGreaterThanSize() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        IList<Integer> top = Searcher.topKSort(50, list);
        assertEquals(20, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(i, top.get(i));
        }
    }	
	
	//  Simple usage
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
    
	//  Test reverse order
	@Test(timeout=SECOND)
	public void testReverseOrder() {
	    IList<String> list = new DoubleLinkedList<>();
	    String alphabet = "abcdefghijklmnopqrstuvwxyz";
	    String alphabetReverse = "zyxwvutsrqpomnlkjihgfedcba";
	    for (int i = 0; i < 26; i++) {
	    	list.add(alphabetReverse.charAt(i) + "");
	    }
        IList<String> top = Searcher.topKSort(26, list);
        assertEquals(26, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(alphabet.charAt(i) + "", top.get(i));
        }
	}
	
    //  Test Strings
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
    
    //  Test Doubles
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
