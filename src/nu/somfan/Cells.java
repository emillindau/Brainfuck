package nu.somfan;

/**
 * Created by Emil on 2013-12-29.
 */
public class Cells {

    private static final int BRAINFUCK_LIMIT = 30000;

    private final int[] mCells = new int[BRAINFUCK_LIMIT];
    private int mPointer = 0;

    private Cells() {}

    public void incrementPointer() {
        mPointer++;

        if(mPointer >= BRAINFUCK_LIMIT) {
            mPointer = 0;
        }
    }

    public void decrementPointer() {
        mPointer--;

        if(mPointer <= 0) {
            mPointer = 0;
        }
    }

    public void addToCurrentCell() {
        mCells[mPointer]++;
    }

    public void substractFromCurrentCell() {
        mCells[mPointer]--;
    }

    public void writeToCurrentCell(int value) {
        mCells[mPointer] = value;
    }

    public int getValueOfCurrentCell() {
        return mCells[mPointer];
    }

    public static Cells valueOf() {
        return new Cells();
    }
}
