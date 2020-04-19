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

import java.io.*;

/**
 * Project Runner object run from the command line
 * Provides sorting on provided input and output filenames
 * TODO input machine specs and such 
 * @author Ross Manfred
 * @author Ryan Buxton
 */
public class Externalsort {
    
	/**
	 * Main method automatically called on execution of program
	 * Expects filenames for input and output files for use by program
	 * Calls appropriate objects to accomplish goals of project
	 * pre: input filename is a valid file to use
	 * post: outputFile has records sorted as desired
	 * @param args contains names of input and output files
	 */
    public static void main(String[] args) {
        try {
            RandomAccessFile inputFile = new RandomAccessFile(args[0], "r");
            RandomAccessFile outputFile = new RandomAccessFile("output.bin", "rw");
            outputFile.setLength(0);
            RandomAccessFile runFile = new RandomAccessFile("run.bin", "rw");
            runFile.setLength(0);
            RecordSorter sorter = new RecordSorter(inputFile, outputFile, runFile);
            inputFile.close();
            outputFile.close();
            runFile.close();
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Wrong number of arguments: " + e);
        }
        catch (FileNotFoundException e) {
            System.err.println("Could not find file: " + args[0]);
        }
        catch(IOException e) {
            System.err.println("Writing error: " + e);
        }
    }
    

}
