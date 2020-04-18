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
/**
 * 
 * @author Ross Manfred
 * @author Ryan Buxton
 */
public class Run {

    private long currOffset;
    private long endOffset;
    private int arrayCurr;
    private int arrayEnd;


    public Run(long startOffset, long endOffset) {
        this.currOffset = startOffset;
        this.endOffset = endOffset;
        arrayCurr = 0;
        arrayEnd = 0;
    }


    public long getCurrOffset() {
        return this.currOffset;
    }


    public long getEndOffset() {
        return this.endOffset;
    }


    public int getArrayCurr() {
        return this.arrayCurr;
    }


    public int getArrayEnd() {
        return this.arrayEnd;
    }


    public void setArrayCurr(int position) {
        this.arrayCurr = position;
    }


    public void setArrayEnd(int end) {
        this.arrayEnd = end;
    }
    
    public void setCurrOffset(long offset) {
        this.currOffset = offset;
    }



    public boolean isDone() {
        return this.arrayCurr == this.arrayEnd
            && this.currOffset == this.endOffset;
    }
    
    public boolean isEmpty() {
        return this.arrayCurr == this.arrayEnd;
    }
}
