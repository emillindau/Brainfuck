package nu.somfan;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by Emil on 2013-12-29.
 */
public class BrainFuck {

    private static final String EXIT = "EXIT";
    private static final String HELP = "HELP";

    private static final String PATTERN = "^(\\-*\\+*\\<*\\>*\\[*\\]*\\.*,*)+$";

    private boolean mExit;
    private String mCmd;
    private Command mCommand;

    private BrainFuck() {}

    private void initialize() {
        mExit = false;
    }

    public void start() {

        initialize();

        while(!mExit) {
            System.out.println("Please enter brainFuck-command..\nType 'help' for options");
            mCmd = BrainFuckReader.read();

            if(mCmd.equals(EXIT)) {
                mExit = true;
            } else if(mCmd.equals(HELP)) {
                printHelp();
            } else {
                checkCommand();
            }
        }

    }

    private void printHelp() {
        System.out.println("Commands: ");
        System.out.println();
        System.out.println("+ - Increment current cell");
        System.out.println("- - Decrement current cell");
        System.out.println("> - Advance to next cell");
        System.out.println("< - Step back to previous cell");
        System.out.println(", - Read an Integer into current cell");
        System.out.println(". - Write value of current cell");
        System.out.println("[ - If current value is 0, skip to next ], else move to next instruction");
        System.out.println("] - If current value is 0, move to next instruction, else go back to previous ]");
        System.out.println("exit - Exits the program");
        System.out.println();
    }

    private void checkCommand() {

        boolean mError = false;

        mCmd = mCmd.replace(" ", "");

        if(Pattern.matches(PATTERN, mCmd)) {

            LinkedList<Character> stack = new LinkedList<>();
            for(int i = 0; i < mCmd.length(); i++) {
                if(mCmd.charAt(i) == '[') {
                    stack.addLast(mCmd.charAt(i));
                }
                if(mCmd.charAt(i) == ']') {
                    if(stack.isEmpty()) {
                        mError = true;
                    } else {
                        if(stack.removeLast() != '[') {
                            mError = true;
                        }
                    }
                }
            }

            if(!stack.isEmpty()) {
                mError = true;
            }

            if(mError) {
                System.out.println("Error - Illegal balancing character ie not a corresponding closing-bracket ']'");
            } else {
                mCommand = Command.valueOf(mCmd);
                mCommand.execute();
            }

        } else {
            System.out.println("Error - Illegal characters. Type 'help' for a list of commands");
        }
    }

    public static BrainFuck valueOf() {
        return new BrainFuck();
    }

    public static class BrainFuckReader {

        private static Scanner mIn = new Scanner(System.in);

        private static String read() {
            return mIn.nextLine().toUpperCase();
        }

    }
}
