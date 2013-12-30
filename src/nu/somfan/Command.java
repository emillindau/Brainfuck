package nu.somfan;

/**
 * Created by Emil on 2013-12-29.
 */
public class Command {

    private static final char PLUS = '+';
    private static final char MINUS = '-';
    private static final char NEXT_CELL = '>';
    private static final char PREV_CELL = '<';
    private static final char READ = ',';
    private static final char WRITE = '.';
    private static final char OPEN_BRACKET = '[';
    private static final char CLOSE_BRACKET = ']';

    private Cells mCells = Cells.valueOf();
    private String mCommand;
    private int mPointer = 0;
    private boolean mExit = false;

    private Command(String input) {
        mCommand = input;
    }

    public void execute() {
        // Rather ugly, but i would like the execute-name to be exposed,
        // whilst readCurrentCommand makes more sense in this context.
        readCurrentCommand();
    }

    private void readCurrentCommand() {
        boolean next;
        char curr;

        while(!mExit) {

            curr = getCurrentCommand();
            next = true;

            switch(curr) {
                case PLUS:
                    mCells.addToCurrentCell();
                    break;
                case MINUS:
                    mCells.substractFromCurrentCell();
                    break;
                case NEXT_CELL:
                    mCells.incrementPointer();
                    break;
                case PREV_CELL:
                    mCells.decrementPointer();
                    break;
                case READ:
                    mCells.writeToCurrentCell('A');
                    break;
                case WRITE:
                    System.out.println("print: " + mCells.getValueOfCurrentCell());
                    break;
                case OPEN_BRACKET:
                    if(mCells.getValueOfCurrentCell() == 0) {
                        continueToNextClosingBracket();
                        next = false;
                    }
                    break;
                case CLOSE_BRACKET:
                    if(mCells.getValueOfCurrentCell() != 0) {
                        stepBackToFirstOpeningBracket();
                        next = false;
                    }
                    break;
            }

            if(next) {
                nextCommand();
            }
        }
    }

    private Character getCurrentCommand() {
        return mCommand.charAt(mPointer);
    }

    private void nextCommand() {
        mPointer++;

        if(mPointer >= mCommand.length()) {
            mExit = true;
            mPointer--;
        }
    }

    private void continueToNextClosingBracket() {
        for(int i = mPointer; i < mCommand.length(); i++) {
            if(mCommand.charAt(i) == CLOSE_BRACKET) {
                mPointer = i;
                return;
            }
        }
    }

    private void stepBackToFirstOpeningBracket() {
        for(int i = mPointer; i > 0; i--) {
            if(mCommand.charAt(i) == OPEN_BRACKET) {
                mPointer = i;
                return;
            }
        }
    }

    public static Command valueOf(String value) {
        return new Command(value);
    }
}