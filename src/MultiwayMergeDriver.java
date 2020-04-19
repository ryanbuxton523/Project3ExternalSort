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
 * Manages merge operations on run file to ultimately create fully sorted
 * output for output file
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
     * pre: buffers are not null, inputFile is valid, runFile contains data
     * post: fields of object are filled with parameter input
     * @param inputBuffer input buffer between file and sort
     * @param outputBuffer input buffer between sort and output
     * @param inputFile input file
     * @param outputFile output file
     * @param runFile file containing runs through the data for merge
     * @param heapArray heap of data
     * @param runs list of runs in run file
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
     * pre: outputBuffer is not null
     * post: sorted data is printed to outputFile
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
     * Merges list of runs within runFile
     * pre: outputFile and runFile are valid
     * post: data is merged in runFile or final contents are printed to outputFile
     * @param isLast if last merge is occurring
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
     * Conditional print breaks up block if needed
     * pre: parameter values are correct, there are things to 
     * print in the output buffer
     * post: first record of output buffer is printed if bool is true
     * @param shouldPrint boolean condition determining if print occurs
     * @param blocksPrinted number of blocks printed so far
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
     * TODO ??
     * load first block of first 8 runs into memory
     * pre: first run points to valid location
     * post: first block of first 8 runs are now in memory
     * @param firstRun position of first run
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
     * TODO I have no idea
     * pre: run is not null, arrayStart will give valid locations in memory
     * post:???
     * @param run run object we modify for the method
     * @param arrayStart start location for array to place in run
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
     * Returns the minimum run within the run file
     * pre: fields are valid in object, first run points to an actual run
     * post: the minimum record has been returned
     * @param firstRun	position of the first run
     * @return	minimum record
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
