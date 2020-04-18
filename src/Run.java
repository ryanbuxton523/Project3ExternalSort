
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
