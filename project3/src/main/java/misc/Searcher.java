package misc;

import datastructures.concrete.ArrayHeap;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import datastructures.interfaces.IPriorityQueue;

public class Searcher {
    /**
     * This method takes the input list and returns the top k elements
     * in sorted order.
     *
     * So, the first element in the output list should be the "smallest"
     * element; the last element should be the "biggest".
     *
     * If the input list contains fewer then 'k' elements, return
     * a list containing all input.length elements in sorted order.
     *
     * This method must not modify the input list.
     *
     * @throws IllegalArgumentException  if k < 0
     */
    public static <T extends Comparable<T>> IList<T> topKSort(int k, IList<T> input) {
    		if (k < 0) {
			throw new IllegalArgumentException();
		} else if (input.isEmpty() || k == 0) {
			return new DoubleLinkedList<T>();
		}
		IPriorityQueue<T> heap = new ArrayHeap<T>();
		// insert first k elements into the heap
		for (int i = 0; i < k; i++) {
			if (i < input.size()) {
				heap.insert(input.get(i));
			}
		}
		// the root of the heap is the kth largest element so far (smallest element)
		// by the end of this loop, the heap only has the top k elements
		for (int i = k; i < input.size(); i++) {
			T element = input.get(i);
			T heapMin = heap.peekMin();
			// check if the new element is larger
			// if it is, it should be the new kth largest element (aka smallest element)
			if (element.compareTo(heapMin) > 0) {
				heap.removeMin();
				// replace the min with a 'larger' element
				heap.insert(element);
			}
		}
		IList<T> result = new DoubleLinkedList<T>();
		int count = Math.min(k, input.size()); // to handle when k > input.size
		// empty the heap
		for (int i = 0; i < count; i++) {
			result.add(heap.removeMin());
		}
		return result;
    }
}
