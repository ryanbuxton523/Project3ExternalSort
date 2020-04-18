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

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Represents the input and output buffers for temporarily holding data
 * @author Ross Manfred
 * @author Ryan Buxton
 */
public class Buffer {
    private static final int CAPACITY = 8192; // capacity
    private static final int RECORD_SIZE = 16; // record size
    private int num; // number of bytes
    private byte[] buffer; // array to hold data
    private int curr; // current position in array
    private RandomAccessFile file; // file used with buffer

    /**
     * Creates a new buffer object
     * @param file file being used by the buffer
     */
    public Buffer(RandomAccessFile file) {
        num = 0;
        this.buffer = new byte[CAPACITY];
        curr = 0;
        this.file = file;
    }

    /**
     * Determines if the buffer is empty
     * @return True if buffer is empty
     */
    public boolean isEmpty() {
        return curr == num;
    }

    /**
     * Determines if the buffer is at capacity
     * @return True if buffer is full
     */
    public boolean isFull() {
        return num == CAPACITY;
    }


    /**
     * Read data from file
     * 
     * @return True if file read is successful
     * 	false if errors occur
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
     * Read data from file with lenght limit?//TODO see file
     * 
     * @return True if read is successful, false otherwise
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

    /**
     * Writes data from file to buffer
     * @throws IOException
     */
    public void writeData() throws IOException {
        file.write(buffer, 0, this.num);
        clear();
    }

    /**
     * Puts array of data to buffer //TODO??
     * @param data array of data to add
     */
    public void addData(byte[] data) {
        for (int i = 0; i < data.length; i++) {
            buffer[num++] = data[i];
        }
    }


    /**
     * Return the next 16 bytes
     * 
     * @return array of next 16 bytes in buffer from curr
     */
    public byte[] getNext() {
        byte[] toReturn = new byte[RECORD_SIZE];
        for (int i = 0; i < RECORD_SIZE; i++) {
            toReturn[i] = buffer[curr++];
        }
        return toReturn;
    }

    /**
     * Get 16 bytes from index start
     * @param start	start index for byte acquisition
     * @return	array of 16 bytes on buffer
     */
    public byte[] get(int start) {
        int index = start;
        byte[] toReturn = new byte[RECORD_SIZE];
        for (int i = 0; i < RECORD_SIZE; i++) {
            toReturn[i] = buffer[index++];
        }
        return toReturn;
    }

    /**
     * Clears positional fields for buffer
     */
    public void clear() {
        num = 0;
        curr = 0;
    }

    /**
     * Return number of bytes in buffer //TODO???
     * @return num field
     */
    public int getNum() {
        return this.num;
    }

    /**
     * Returns current position in buffer
     * @return	curr field
     */
    public int getCurr() {
        return this.curr;
    }

    /**
     * Returns static capacity for buffer
     * @return	capacity variable
     */
    public int getCapacity() {
        return CAPACITY;
    }

    /**
     * Sets a new value for buffer's file
     * @param file new file for buffer
     */
    public void setFile(RandomAccessFile file) {
        this.file = file;
    }

}
