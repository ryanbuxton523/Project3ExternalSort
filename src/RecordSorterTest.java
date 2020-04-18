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

import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import student.TestCase;

/**
 * 
 * @author Ross Manfred
 * @author Ryan Buxton
 */
public class RecordSorterTest extends TestCase {
    private RecordSorter sorter;
    private RandomAccessFile inputFile;
    private RandomAccessFile outputFile;
    private RandomAccessFile runFile;
    private FileWriter textFile;
    private FileWriter otherFile;


    public void setUp() throws IOException {
        inputFile = new RandomAccessFile("src/test200.bin", "r");
        outputFile = new RandomAccessFile("output.bin", "rw");
        outputFile.setLength(0);
        runFile = new RandomAccessFile("run.bin", "rw");
        runFile.setLength(0);
        sorter = new RecordSorter(inputFile, outputFile, runFile);
        textFile = new FileWriter("run.txt");
        otherFile = new FileWriter("output.txt");
    }


    public void test() throws IOException {
        sorter.printFileContents(outputFile, otherFile);
        sorter.printFileContents(runFile, textFile);
        
        inputFile.close();
        outputFile.close();
        runFile.close();
        textFile.close();
        otherFile.close();
    }

// public void test() throws IOException {
// sorter.readInputBufferData(inputFile);
// sorter.writeInputBufferData(runFile);
//
// inputFile.close();
// outputFile.close();
// runFile.close();
// }

// public void test3() throws IOException {
// //sorter.printFileContents(inputFile, outputFile);
// textFile.write("hey");
// inputFile.close();
// outputFile.close();
// runFile.close();
// textFile.close();
// }

}
