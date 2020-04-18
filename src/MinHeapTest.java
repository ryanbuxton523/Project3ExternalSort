import student.TestCase;

public class MinHeapTest extends TestCase {
    private MinHeap<Integer> heap;


    public void setUp() {
        Integer[] values = { new Integer(3), new Integer(7), new Integer(1),
            new Integer(5), new Integer(9)};
        heap = new MinHeap<Integer>(values, 5, 5);
        heap.buildheap();
    }
    
    public void testRemoveMin() {
        Integer i = heap.removemin();
        assertEquals("1", i.toString());
        assertEquals(4, heap.heapsize());
        assertEquals("3", heap.removemin().toString());
        assertEquals(3, heap.heapsize());
        assertEquals("5", heap.removemin().toString());
        assertEquals(2, heap.heapsize());
        heap.insert(new Integer(6));
        heap.insert(new Integer(2));
        heap.insert(new Integer(3));
        assertEquals(5, heap.heapsize());
        assertEquals("2", heap.removemin().toString());
        assertEquals(4, heap.heapsize());
        assertEquals("3", heap.removemin().toString());
        assertEquals(3, heap.heapsize());
        assertEquals("6", heap.removemin().toString());
        assertEquals(2, heap.heapsize());
        assertEquals("7", heap.removemin().toString());
        assertEquals(1, heap.heapsize());
        assertEquals("9", heap.removemin().toString());
        assertEquals(0, heap.heapsize());
    }
    
    public void testRemoveOpposite() {
        Integer[] values = { new Integer(5), new Integer(4), new Integer(3),
            new Integer(2), new Integer(1)};
        heap = new MinHeap<Integer>(values, 5, 5);
        heap.buildheap();
        assertEquals("1", heap.removemin().toString());
        assertEquals(4, heap.heapsize());
        assertEquals("2", heap.removemin().toString());
        assertEquals(3, heap.heapsize());
        assertEquals("3", heap.removemin().toString());
        assertEquals(2, heap.heapsize());
        assertEquals("4", heap.removemin().toString());
        assertEquals(1, heap.heapsize());
        assertEquals("5", heap.removemin().toString());
        assertEquals(0, heap.heapsize());
    }
    
    public void testLast() {
        Integer[] values = { new Integer(5), new Integer(4), new Integer(3),
            new Integer(2), new Integer(1)};
        heap = new MinHeap<Integer>(values, 5, 5);
        
        assertEquals("1", heap.getMin().toString());
        heap.setLast(new Integer(7));
        assertEquals(4, heap.heapsize());
        heap.siftdown(0);
        assertEquals("2", heap.getMin().toString());
        heap.setLast(new Integer(3));
        assertEquals(3, heap.heapsize());
        heap.siftdown(0);
        assertEquals("3", heap.getMin().toString());
        heap.setLast(new Integer(4));
        assertEquals(2, heap.heapsize());
        heap.siftdown(0);
        assertEquals("4", heap.getMin().toString());
        heap.setLast(new Integer(2));
        assertEquals(1, heap.heapsize());
        heap.siftdown(0);
        assertEquals("5", heap.getMin().toString());
        heap.setLast(new Integer(8));
        assertEquals(0, heap.heapsize());  
        heap.shiftHeapArray();
        assertEquals(5, heap.heapsize());
        
    }
    
    public void testLast2() {
        Integer[] values = { new Integer(5), new Integer(4), new Integer(3),
            new Integer(2), new Integer(1)};
        heap = new MinHeap<Integer>(values, 5, 5);
        
        assertEquals("1", heap.getMin().toString());
        heap.setLast(new Integer(7));
        assertEquals(4, heap.heapsize());
        heap.siftdown(0);
        assertEquals("2", heap.getMin().toString());
        heap.setLast(new Integer(3));
        assertEquals(3, heap.heapsize());
        heap.siftdown(0);
        heap.setLast(new Integer(5));
        heap.siftdown(0);
        assertEquals(2, heap.heapsize());
        assertEquals("4", heap.removemin().toString());
        assertEquals(1, heap.heapsize());
        assertEquals("5", heap.removemin().toString());
        assertEquals(0, heap.heapsize());
        heap.shiftHeapArray();
        heap.buildheap();
        assertEquals(3, heap.heapsize());
        assertEquals("3", heap.removemin().toString());
        assertEquals("5", heap.removemin().toString());
        assertEquals("7", heap.removemin().toString());
    }
}
