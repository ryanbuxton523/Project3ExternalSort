import java.io.RandomAccessFile;
import student.TestCase;

public class BufferTest extends TestCase {
    private Buffer buffer;
    private RandomAccessFile file;
    
    public void setUp() {
        buffer = new Buffer(file);
    }
}
