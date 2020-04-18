//import java.io.FileNotFoundException;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.RandomAccessFile;
//import java.util.ArrayList;
//import student.TestCase;
//
//public class ReplacementSelectionDriverTest extends TestCase {
//    private ReplacementSelectionDriver driver;
//    private RandomAccessFile inputFile;
//    private RandomAccessFile outputFile;
//    private RandomAccessFile runFile;
//    private Buffer inputBuffer;
//    private Buffer outputBuffer;
//    private FileWriter textFile;
//    private FileWriter text2File;
//
//
//    public void setUp() throws IOException {
//        inputFile = new RandomAccessFile("src/test200.bin", "r");
//        outputFile = new RandomAccessFile("output.bin", "rw");
//        outputFile.setLength(0);
//        runFile = new RandomAccessFile("run.bin", "rw");
//        runFile.setLength(0);
//        inputBuffer = new Buffer(inputFile);
//        outputBuffer = new Buffer(runFile);
//        driver = new ReplacementSelectionDriver(inputBuffer, outputBuffer,
//            inputFile, runFile);
//        textFile = new FileWriter("run.txt");
//        text2File = new FileWriter("sampleTxt.txt");
//    }
//    
//    public void test() throws IOException {
//        ArrayList<Run> runs = driver.createRuns();
//        assertEquals(14, runs.size());
//        System.out.println(runs.get(0).getEndOffset());
//        System.out.println(runs.get(1).getEndOffset());
//        driver.printFileContents(runFile, textFile);
//        driver.printFileContents(inputFile, text2File);
//        
//        inputFile.close();
//        outputFile.close();
//        runFile.close();
//        textFile.close();
//        text2File.close();
//        
//    }
//}
