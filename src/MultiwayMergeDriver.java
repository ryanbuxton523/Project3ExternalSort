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
 * 
 * @author Ross Manfred
 * @author Ryan Buxton
 */
public class MultiwayMergeDriver {
    private static final int BUFFER_SIZE = 512;

    private Buffer inputBuffer; // input buffer
    private Buffer outputBuffer; // output buffer
    private RandomAccessFile outputFile; // output file
    private RandomAccessFile runFile; // run file
    private Record[] memory; // array from the heap
    private ArrayList<Run> runs; // list of runs

    /**
     * Creates a new MultiWayMerger object
     * @param inputBuffer input buffer between file and sort
     * @param outputBuffer input buffer between sort and output
     * @param inputFile input file
     * @param outputFile output file
     * @param runFile //TODO
     * @param heapArray //TODO
     * @param runs //TODO
     */
    public MultiwayMergeDriver(
        Buffer inputBuffer,
        Buffer outputBuffer,
        RandomAccessFile inputFile,
        RandomAccessFile outputFile,
        RandomAccessFile runFile,
        Record[] heapArray,
        ArrayList<Run> runs) {
        this.inputBuffer = inputBuffer;
        this.outputBuffer = outputBuffer;
        this.outputFile = outputFile;
        this.runFile = runFile;
        inputBuffer.setFile(runFile);
        this.memory = heapArray;
        this.runs = runs;
    }

    /**
     * Merge manager for data keeping track of when last merge should occur
     * @throws IOException
     */
    public void multiwayMerge() throws IOException {
        int numRuns;
        boolean isLastMerge = false;
        while ((numRuns = runs.size()) > 0) {
            if (numRuns <= 8) {
                isLastMerge = true;
                outputBuffer.setFile(outputFile);
            }
            mergeRuns(isLastMerge);
        }
    }
    
    /**
     * 
     * @param isLast
     * @throws IOException
     */
    @SuppressWarnings("resource")
    public void mergeRuns(boolean isLast) throws IOException {
        RandomAccessFile output = isLast ? outputFile : runFile;
        int numRuns = Math.min(runs.size(), 8);
        int numBlocks = 0;
        this.loadRuns(0);
        Record min = this.getMinRun(0);
        long offset = isLast ? 0 : output.length();
        output.seek(offset);
        outputBuffer.clear();
        while (min != null) {
            if (outputBuffer.isFull()) {
                output.seek(output.length()); // for when adding to run file
                checkPrint(isLast, numBlocks);
                outputBuffer.writeData();
                numBlocks++;
                // this.writeOutputBufferData();
            }

            outputBuffer.addData(min.getBytes());
            min = this.getMinRun(0);
        }
        output.seek(output.length());
        checkPrint(isLast, numBlocks);
        outputBuffer.writeData();
        numBlocks++;
        // this.writeOutputBufferData(output);
        if (!isLast) {
            runs.add(new Run(offset, output.getFilePointer()));
        }
        // remove runs that we just parsed
        for (int i = 0; i < numRuns; i++) {
            runs.remove(0);
        }
    }

    /**
     * 
     * @param shouldPrint
     * @param blocksPrinted
     */
    private void checkPrint(boolean shouldPrint, int blocksPrinted) {
        if (shouldPrint) {
            if (blocksPrinted % 5 == 0 && blocksPrinted != 0) {
                System.out.println();
            }
            Record record = new Record(outputBuffer.get(0));
            record.print();
        }
    }


    /**
     * load first block of first 8 runs into memory
     * 
     * @throws IOException
     */
    private void loadRuns(int firstRun) throws IOException {
        int upperBound = Math.min(8, runs.size() - firstRun);
        for (int i = 0; i < upperBound; i++) {
            Run run = runs.get(i + firstRun);
            int arrayStart = BUFFER_SIZE * i;
            loadRun(run, arrayStart);
        }
    }

    /**
     * 
     * @param run
     * @param arrayStart
     * @throws IOException
     */
    private void loadRun(Run run, int arrayStart) throws IOException {
        run.setArrayCurr(arrayStart);
        long endOffset = run.getEndOffset();
        long startOffset = run.getCurrOffset();
        long totalBytes = endOffset - startOffset;
        runFile.seek(startOffset);
        int maxBytes = Math.min((int)totalBytes, inputBuffer.getCapacity());
        inputBuffer.readData(maxBytes);
        // set so that we read a certain number of bytes math.min(totalbytes,
        // blocksize)
        // read into inputBuffer and copy to heapArray
        Record record = null;
        int numRecords = 0;
        while (!inputBuffer.isEmpty()) {
            record = new Record(inputBuffer.getNext());
            memory[arrayStart + numRecords] = record;
            numRecords++;
        }
        run.setArrayEnd(numRecords + arrayStart);
        run.setCurrOffset(runFile.getFilePointer());
    }

    /**
     * 
     * @param firstRun
     * @return
     * @throws IOException
     */
    private Record getMinRun(int firstRun) throws IOException {
        Record min = null;
        Run run = null;
        int numMinRun = 0;
        int upperBound = Math.min(8, runs.size() - firstRun);
        for (int i = 0; i < upperBound; i++) {
            run = runs.get(i + firstRun);
            if (run.isDone()) {
                continue;
            }
            if (run.isEmpty()) {
                loadRun(run, run.getArrayEnd() - BUFFER_SIZE);
            }
            int position = run.getArrayCurr();
            Record curr = memory[position];
            if (curr.compareTo(min) < 0) {
                min = curr;
                numMinRun = i + firstRun;
            }
        }
        run = runs.get(numMinRun);
        int position = run.getArrayCurr();
        run.setArrayCurr(position + 1);
        return min;
    }

}
