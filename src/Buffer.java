import java.io.IOException;
import java.io.RandomAccessFile;

public class Buffer {
    private static final int CAPACITY = 8192; // capacity
    private static final int RECORD_SIZE = 16; // record size
    private int num; // number of bytes
    private byte[] buffer; // array to hold data
    private int curr; // current position in array
    private RandomAccessFile file; // file used with buffer


    public Buffer(RandomAccessFile file) {
        num = 0;
        this.buffer = new byte[CAPACITY];
        curr = 0;
        this.file = file;
    }


    public boolean isEmpty() {
        return curr == num;
    }


    public boolean isFull() {
        return num == CAPACITY;
    }


    /**
     * Read data from file
     * 
     * @return
     */
    public boolean readData() {
        try {
            this.num = file.read(buffer);
            this.curr = 0;
            if (this.num == -1) {
                this.curr = -1;
                return false;
            }
            return true;
        }
        catch (IOException e) {
            return false;
        }

    }


    /**
     * Read data from file
     * 
     * @return
     */
    public boolean readData(int len) {
        try {
            this.num = file.read(buffer, 0, len);
            this.curr = 0;
            if (this.num == -1) {
                this.curr = -1;
                return false;
            }
            return true;
        }
        catch (IOException e) {
            return false;
        }

    }


    public void writeData() throws IOException {
        file.write(buffer, 0, this.num);
        clear();
    }


    public void addData(byte[] data) {
        for (int i = 0; i < data.length; i++) {
            buffer[num++] = data[i];
        }
    }


    /**
     * Return the next 16 bytes
     * 
     * @return
     */
    public byte[] getNext() {
        byte[] toReturn = new byte[RECORD_SIZE];
        for (int i = 0; i < RECORD_SIZE; i++) {
            toReturn[i] = buffer[curr++];
        }
        return toReturn;
    }


    public byte[] get(int start) {
        int index = start;
        byte[] toReturn = new byte[RECORD_SIZE];
        for (int i = 0; i < RECORD_SIZE; i++) {
            toReturn[i] = buffer[index++];
        }
        return toReturn;
    }


    public void clear() {
        num = 0;
        curr = 0;
    }


    public int getNum() {
        return this.num;
    }


    public int getCurr() {
        return this.curr;
    }


    public int getCapacity() {
        return CAPACITY;
    }


    public void setFile(RandomAccessFile file) {
        this.file = file;
    }

}
