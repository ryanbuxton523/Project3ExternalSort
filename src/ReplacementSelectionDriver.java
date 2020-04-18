import java.io.EOFException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class ReplacementSelectionDriver {
    private static final int HEAP_SIZE = 4096;
    private static final int BUFFER_SIZE = 512;

    private MinHeap<Record> heap;
    private Record[] heapArray;
    private Buffer inputBuffer;
    private Buffer outputBuffer;
    private RandomAccessFile inputFile;
    private RandomAccessFile runFile; // make the method return the list


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

    
    public Record[] getHeapMemory() {
        return heapArray;
    }

}
