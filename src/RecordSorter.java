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
 * Sort manager for input records
 * Sets up buffers and other objects
 * @author Ross Manfred
 * @author Ryan Buxton
 */
public class RecordSorter {
	
	/**
	 * Creates a new RecordSorter object
	 * manages sort and merge operations on data
	 * pre: files are all valid
	 * post: outputFile is written with proper data in order
	 * @param inputFile	file containing input values
	 * @param outputFile ultimate file for output of data
	 * @param runFile temporary output file storing runs for merge
	 * @throws IOException
	 */
    public RecordSorter(
        RandomAccessFile inputFile,
        RandomAccessFile outputFile,
        RandomAccessFile runFile)
        throws IOException {

        Buffer inputBuffer = new Buffer(inputFile);
        Buffer outputBuffer = new Buffer(runFile);
        Record[] heapArray = null;

        ReplacementSelectionDriver replacementSelection =
            new ReplacementSelectionDriver(inputBuffer, outputBuffer, inputFile,
                runFile);

        ArrayList<Run> runs = replacementSelection.createRuns();
        heapArray = replacementSelection.getHeapMemory();

        MultiwayMergeDriver multiwayMerge = new MultiwayMergeDriver(inputBuffer,
            outputBuffer, inputFile, outputFile, runFile, heapArray, runs);
        multiwayMerge.multiwayMerge();

    }
    
    /**
     * Prints contents of sorter to a provided output file
     * pre: file and writer are valid
     * post: contents are printed properly using FileWriter
     * @param readFile	input file to get records from
     * @param writeFile output file to write records
     * @throws IOException
     */
    public void printFileContents(
        RandomAccessFile readFile,
        FileWriter writeFile)
        throws IOException {
        readFile.seek(0);
        byte[] data = new byte[16];
        int num = 0;
        while (num != -1) {
            try {
                num = readFile.read(data);
                if (num != -1) {
                    Record record = new Record(data);
                    writeFile.write(record.toString());
                }
            }
            catch (EOFException e) {
                return;
            }
        }
    }

}
