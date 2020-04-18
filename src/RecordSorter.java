import java.io.EOFException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class RecordSorter {

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
