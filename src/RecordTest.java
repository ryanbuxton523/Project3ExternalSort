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

import student.TestCase;

/**
 * 
 * @author Ross Manfred
 * @author Ryan Buxton
 */
public class RecordTest extends TestCase {
    private Record record;
    private Record record2;



    public void setUp() {
        byte[] data = {0x12, 0x34, 0x56, 0x78, 0x12, 0x12, 0x34, 0x45,
            0x12, 0x34, 0x56, 0x78, 0x12, 0x12, 0x34, 0x45};
        record = new Record(data);
        byte[] data2 = {0x12, 0x34, 0x56, 0x78, 0x12, 0x12, 0x34, 0x45,
            0x22, 0x34, 0x56, 0x78, 0x12, 0x12, 0x34, 0x45};
        record2 = new Record(data2);
    }
    
    public void testToString() {
       System.out.println(record.toString());
       System.out.println(record.toString());
       System.out.println(record.toString());
       System.out.println(record.toString());
       System.out.println(record2.toString());

       assertNotNull(record.toString());
       assertEquals(-1, record.compareTo(record2));
    }
    
//    public void testCompare() {
//        Record other = new Record(3520943, 6e9);
//        assertTrue(record.compareTo(other) < 0);
//    }
}
