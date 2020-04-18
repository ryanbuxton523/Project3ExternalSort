import java.io.*;

public class Externalsort {
    
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
