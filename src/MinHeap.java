// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.
/**
 * MinHeap used by the ReplacementSelectionDriver to sort data with the min
 * value always existing at the root of the heap.
 * 
 * @author Ryan Buxton <ryanbuxton523>
 * @author Ross Manfred <rmm26079>
 * @version 04/17/2020
 */
public class MinHeap<T extends Comparable<T>> {
    private T[] Heap; // Pointer to the heap array
    private int size; // Maximum size of the heap
    private int n; // Number of things now in heap
    private int last;


    /**
     * Create a new MinHeap using the given array, number of elements, and max
     * number of elements.
     * 
     * Pre-condition: h has been instantiated as a proper array of T objects.
     * max is the size of h.
     * num is less than or equal to h.
     * Post-condition: the MinHeap has been created and heapified.
     * 
     * @param h
     *            the underlying array to be used.
     * @param num
     *            current number of elements in the array
     * @param max
     *            maximum number of elements the heap can hold
     */
    public MinHeap(T[] h, int num, int max) {
        Heap = h;
        n = num;
        size = max;
        buildheap();
        last = max - 1;
    }


    /**
     * Get the number of elements currently in the heap.
     * 
     * Pre-condition: this is a proper MinHeap.
     * Post-condition: the number of elements has been returned.
     * 
     * @return the number of elements
     */
    public int heapsize() {
        return n;
    }


    /**
     * Return true if pos is a leaf position, false otherwise
     * 
     * Pre-condition: this is a proper MinHeap.
     * Post-condition: true or false has been returned.
     * 
     * @param pos
     *            index to be checked.
     * @return true if pos is a leaf position, false otherwise
     */
    private boolean isLeaf(int pos) {
        return (pos >= n / 2) && (pos < n);
    }


    /**
     * Return position for left child of pos
     * 
     * Pre-condition: this is a proper MinHeap.
     * Post-condition: the position has been returned.
     * 
     * @param pos
     *            index to be checked
     * @return the position for the left child
     */
    private int leftchild(int pos) {
        if (pos >= n / 2)
            return -1;
        return 2 * pos + 1;
    }


    /**
     * Return position for right child of pos
     * 
     * Pre-condition: this is a proper MinHeap.
     * Post-condition: the position has been returned.
     * 
     * @param pos
     *            index to be checked
     * @return the position for the right child
     */
    private int rightchild(int pos) {
        if (pos >= (n - 1) / 2)
            return -1;
        return 2 * pos + 2;
    }


    /**
     * Return position for parent
     * 
     * Pre-condition: this is a proper MinHeap.
     * Post-condition: the position has been returned.
     * 
     * @param pos
     *            index to be checked
     * @return the position for the parent
     */
    private int parent(int pos) {
        if (pos <= 0)
            return -1;
        return (pos - 1) / 2;
    }


    /**
     * Insert key into the heap and sift it up appropriately.
     * 
     * Pre-condition: key is valid.
     * Post-condition: key has been inserted and sifted up.
     * 
     * @param key
     *            to insert
     */
    public void insert(T key) {
        if (n >= size) {
            System.out.println("Heap is full");
            return;
        }
        int curr = n++;
        Heap[curr] = key; // Start at end of heap
        // Now sift up until curr's parent's key > curr's key
        while ((curr != 0) && (Heap[curr].compareTo(Heap[parent(curr)]) < 0)) {
            swap(curr, parent(curr));
            curr = parent(curr);
        }
    }


    /**
     * Heapify contents of Heap
     * 
     * Pre-condition: this is a proper MinHeap.
     * Post-condition: the heap has been heapified.
     */
    public void buildheap() {
        for (int i = n / 2 - 1; i >= 0; i--)
            siftdown(i);
    }


    /**
     * Put element in its correct place
     * 
     * Pre-condition: this is a proper MinHeap.
     * Post-condition: the element at position pos is in the correct place in
     * the MinHeap now.
     * 
     * @param pos
     *            position of element to siftdown.
     */
    public void siftdown(int pos) {
        if ((pos < 0) || (pos >= n))
            return; // Illegal position
        while (!isLeaf(pos)) {
            int j = leftchild(pos);
            if ((j < (n - 1)) && (Heap[j].compareTo(Heap[j + 1]) > 0))
                j++; // j is now index of child with greater value
            if (Heap[pos].compareTo(Heap[j]) <= 0)
                return;
            swap(pos, j);
            pos = j; // Move down
        }
    }


    /**
     * Remove and return minimum value
     * 
     * Pre-condition: this is a proper MinHeap.
     * Post-condition: the min value has been returned.
     * 
     * @return the minimum value from the heap.
     */
    public T removemin() {
        if (n == 0)
            return null; // Removing from empty heap
        swap(0, --n); // Swap maximum with last value
        siftdown(0); // Put new heap root val in correct place
        return Heap[n];
    }


    /**
     * Remove and return element at specified position
     * 
     * Pre-condition: this is a proper MinHeap.
     * Post-condition: the element has been returned.
     * 
     * @param pos
     *            specified position of element
     * @return the element at pos
     */
    public T remove(int pos) {
        if ((pos < 0) || (pos >= n))
            return null; // Illegal heap position
        if (pos == (n - 1))
            n--; // Last element, no work to be done
        else {
            swap(pos, --n); // Swap with last value
            update(pos);
        }
        return Heap[n];
    }


    /**
     * Modify the value at the given position
     * 
     * Pre-condition: newVal is valid.
     * Post-condition: newVal has been added at pos and sifted appropriately.
     * 
     * @param pos
     *            position to modify
     * @param newVal
     *            new value for the position
     */
    public void modify(int pos, T newVal) {
        if ((pos < 0) || (pos >= n))
            return; // Illegal heap position
        Heap[pos] = newVal;
        update(pos);
    }


    /**
     * Set the value of the last element in the heap by removing the min.
     * 
     * Pre-condition: newVal is valid.
     * Post-condition: last and n have been decremented.
     * 
     * @param newVal
     *            to set last to.
     */
    public void setLast(T newVal) {
        Heap[0] = Heap[last];
        Heap[last] = newVal;
        last -= 1;
        n -= 1;
    }


    /**
     * Returns whether the heap is empty.
     * 
     * Pre-condition: this is a proper MinHeap.
     * Post-condition: whether the heap is empty has been returned.
     * 
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return n == 0;
    }


    /**
     * Return the min value in the heap without removing it.
     * 
     * Pre-condition: the heap has at least one element.
     * Post-condition: the min value has been returned.
     * 
     * @return the min value
     */
    public T getMin() {
        return Heap[0];
    }


    /**
     * The value at pos has been changed, restore the heap property
     * 
     * Pre-condition: this is a proper MinHeap.
     * Post-condition: the value at pos is at the correct position
     * 
     * @param pos
     *            of element that has been changed.
     */
    private void update(int pos) {
        // If it is a big value, push it up
        while ((pos > 0) && (Heap[pos].compareTo(Heap[parent(pos)]) < 0)) {
            swap(pos, parent(pos));
            pos = parent(pos);
        }
        siftdown(pos); // If it is little, push down
    }


    /**
     * Swap the values in the heap at pos1 and pos2.
     * 
     * Pre-condition: pos1 and pos2 are valid heap positions
     * Post-condition: the values have been swapped.
     * 
     * @param pos1
     *            position of first value to swap
     * @param pos2
     *            position of second value to swap
     */
    private void swap(int pos1, int pos2) {
        T temp = Heap[pos1];
        Heap[pos1] = Heap[pos2];
        Heap[pos2] = temp;
    }


    /**
     * Get the current position of the last element in the heap.
     * 
     * Pre-condition: this is a proper MinHeap.
     * Post-condition: the position has been returned.
     * 
     * @return the position of the last element in the heap.
     */
    public int getLast() {
        return last;
    }


    /**
     * Get the value at pos in the heap.
     * 
     * Pre-condition: pos is a valid position
     * Post-condition: the value has been returned.
     * 
     * @param pos
     *            position to get
     * @return the element at position pos
     */
    public T get(int pos) {
        return Heap[pos];
    }


    /**
     * Set the value of an element at a position in the heap.
     * 
     * Pre-condition: pos is a valid position.
     * Post-condition: the value at pos has been set to newVal
     * 
     * @param pos
     *            position to set.
     * @param newVal
     *            new value to set pos to
     */
    public void set(int pos, T newVal) {
        Heap[pos] = newVal;
    }


    /**
     * Set the number of elements currently in the heap
     * 
     * Pre-condition: this is a proper MinHeap.
     * Post-condition: n has been set to newN.
     * 
     * @param newN
     *            new number of elements in the heap
     */
    public void setN(int newN) {
        this.n = newN;
    }


    /**
     * Get the capacity of the heap
     * 
     * Pre-condition: this is a proper MinHeap.
     * Post-condition: the capacity has been returned.
     * 
     * @return the capacity of the heap.
     */
    public int getHeapCapacity() {
        return this.size;
    }


    /**
     * Shift the "hidden" values of the heap to the beginning of the heap array
     * and reinitialize the fields.
     * 
     * Pre-condition: this is a proper MinHeap.
     * Post-condition: the values have been shifted and n and last have been set
     * appropriately.
     */
    public void shiftHeapArray() {
        int last = this.getLast() + 1;
        int heapSize = this.getHeapCapacity();
        int numElements = heapSize - last;
        this.setN(numElements);
        this.last = this.size - 1;
        if (last != 0) {
            if (last > heapSize) {
                // take top and move to front
            }
            else {
                // shift everything left certain amount
                for (int i = 0; i < numElements; i++) {
                    this.set(i, this.get(last + i));
                }
            }

        }

    }
}
