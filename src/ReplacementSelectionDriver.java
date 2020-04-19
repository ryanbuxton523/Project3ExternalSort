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

import java.io.EOFException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * Manages sort operations, heap and buffer operations
 * @author Ross Manfred
 * @author Ryan Buxton
 */
public class ReplacementSelectionDriver {
    private static final int HEAP_SIZE = 4096;
    private static final int BUFFER_SIZE = 512;

    private MinHeap<Record> heap;
    private Record[] heapArray;
    private Buffer inputBuffer;
    private Buffer outputBuffer;
    private RandomAccessFile inputFile;
    private RandomAccessFile runFile; // make the method return the list

    /**
     * Creates a new ReplacementSelectionDriver object
     * pre: buffers, files are valid and not null
     * post: fields are filled, MinHeap sorted data from heapArray
     * @param inputBuffer input buffer for data
     * @param outputBuffer output buffer before output file
     * @param input input file
     * @param run file to store runs for later merge
     * @throws IOException
     */
    public ReplacementSelectionDriver(
        Buffer inputBuffer,
        Buffer outputBuffer,
        RandomAccessFile input,
        RandomAccessFile run)
        throws IOException {
        this.inputBuffer = inputBuffer;
        this.outputBuffer = outputBuffer;
        runFile = run;
        inputFile = input;
        heapArray = this.getHeapArray();
        heap = new MinHeap<Record>(heapArray, HEAP_SIZE, HEAP_SIZE);
    }

    /**
     * Builds and fills a heap array with data from input file
     * provided by input buffer
     * Later turned into a min heap for sorting purposes
     * pre: inputFile, buffer is valid, buffer_size is not 0
     * post: array of records read in from buffer is produced
     * @return heap array of records
     * @throws IOException
     */
    private Record[] getHeapArray() throws IOException {
        Record[] heapArray = new Record[HEAP_SIZE];
        for (int i = 0; i < (HEAP_SIZE / BUFFER_SIZE); i++) { // maybe make this
                                                              // generic
            inputBuffer.readData();
            for (int j = 0; j < BUFFER_SIZE; j++) {
                Record record = new Record(inputBuffer.getNext());
                heapArray[BUFFER_SIZE * i + j] = record;
            }
        }
        return heapArray;
    }

    /**
     * Manages a run of data
     * pre: inputFile is valid, buffers are not null
     * post: a run is created from input data and heap
     * @return the run created
     * @throws IOException
     */
    public Run createRun() throws IOException {
        long offset = runFile.getFilePointer();
        Record min = null;
        Record next = null;
        boolean moreInputData = true;
        int numWrites = 0;
        while (!heap.isEmpty()) {
            // System.out.printf("curr input: %d\n", inputBuffer.getCurr());
            // System.out.printf("num input: %d\n", inputBuffer.getNum());
            if (outputBuffer.isFull()) {
                // this.writeOutputBufferData(runFile);
                outputBuffer.writeData();
                numWrites++;
            }
            if (inputBuffer.isEmpty()) {
                // moreInputData = this.readInputBufferData(inputFile);
                moreInputData = inputBuffer.readData();
            }

            if (moreInputData) {
                min = heap.getMin();
                next = new Record(inputBuffer.getNext());
                if (next.compareTo(min) >= 0) {
                    heap.set(0, next);
                }
                else {
                    heap.setLast(next);
                }
                heap.siftdown(0);
            }
            else {
                min = heap.removemin();
            }
            outputBuffer.addData(min.getBytes());
        }
        outputBuffer.writeData();
        numWrites++;
        return new Run(offset, runFile.getFilePointer());
    }

    /**
     * Manages creating all runs
     * pre: inputFile is valid
     * post: a list of runs is returned
     * @return	a list of the runs created in this process
     * @throws IOException
     */
    public ArrayList<Run> createRuns() throws IOException {
        ArrayList<Run> runs = new ArrayList<Run>();
        long length = inputFile.length();
        while (inputFile.getFilePointer() < length - 1) {
            runs.add(this.createRun());
            heap.shiftHeapArray();
            heap.buildheap();
        }
        runs.add(this.createRun());
        return runs;
    }

    /**
     * Getter for heapArray object
     * pre: heapArray is valid
     * post: record array heapArray returned
     * @return heapArray
     */
    public Record[] getHeapMemory() {
        return heapArray;
    }

}
