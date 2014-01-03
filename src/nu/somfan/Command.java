package nu.somfan;

import java.util.LinkedList;

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
    private LinkedList<Integer> mBrackets = new LinkedList<>();

    private Command(String input) {
        mCommand = input;
    }

    public void execute() {
        // Rather ugly, but i would like the execute-name to be exposed,
        // whilst readCurrentCommand makes more sense in this context.
        readCurrentCommand();
    }

    /**
     * Steps through the current command
     * and executes the chars in order
     */
    private void readCurrentCommand() {
        boolean next;
        char curr;

        while(!mExit) {

            curr = getCurrentCommand();
            next = true;

            if(mPointer == 94) {
                System.out.print("");
            }

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
                    try {
                        char input = BrainFuckReader.readToCell();
                        mCells.writeToCurrentCell(input);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case WRITE:
                    System.out.println("" + mCells.getValueOfCurrentCell() + " : " + ((char)mCells.getValueOfCurrentCell()));
                    break;
                case OPEN_BRACKET:
                    if(mCells.getValueOfCurrentCell() == 0) {
                        continueToNextClosingBracket();
                        // next = false;
                    } else {
                        if(!mBrackets.contains(mPointer)) {
                            mBrackets.addLast(mPointer);
                        }
                    }
                    break;
                case CLOSE_BRACKET:
                    if(mCells.getValueOfCurrentCell() != 0) {
                        stepBackToFirstOpeningBracket();
                        next = false;
                    } else {
                        // If we continue, remove latest, granted that the opening bracket was added
                        if(!mBrackets.isEmpty()) {
                            mBrackets.removeLast();
                        }
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
        int counter = 1;

        for(int i = mPointer+1; i < mCommand.length(); i++) {

            if(counter == 0) {
                return;
            }
            // We can encounter more opening-brackets on our way
            // and therefore there should be corresponding closing ones
            if(mCommand.charAt(i) == OPEN_BRACKET) {
                counter++;
            }

            if(mCommand.charAt(i) == CLOSE_BRACKET) {
                mPointer = i;
                counter--;
            }
        }
    }

    private void stepBackToFirstOpeningBracket() {
        mPointer = mBrackets.removeLast();
    }

    public static Command valueOf(String value) {
        return new Command(value);
    }
}
