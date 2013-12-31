package nu.somfan;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Class for handling input
 */
public class BrainFuckReader {

    private static final String PATTERN = "^(\\-*\\+*\\<*\\>*\\[*\\]*\\.*,*)+$";

    private static Scanner mIn = new Scanner(System.in);

    /**
     * Just plain read from console.
     * @return String in uppercase
     */
    static String read() {
        System.out.println("Please enter brainFuck-command..\nType 'help' for options");
        return mIn.nextLine().toUpperCase();
    }

    /**
     * Not sure yet how this method should work
     * as of now, it either get the char value
     * or integer value
     * @return char
     * @throws Exception format
     */
    static char readToCell() throws Exception {

        System.out.print("input: ");

        String input = mIn.nextLine();
        int val;
        if(input.length() == 1) {
            val = Integer.valueOf(input.charAt(0));
        } else {
            val = Integer.valueOf(input);
        }

        // 32-126
        if(val > 31 && val < 127) {
            return ((char)val);
        } else {
            throw new Exception("Error - value should be either a character or an integer");
        }
    }

    /**
     * Parsing command from the user
     * @param cmd
     * @return valid command
     * @throws Exception
     */
    static String readCommand(String cmd) throws Exception {
        boolean error = false;
        cmd = cmd.replace(" ", "");

        // Check if only allowed chars
        if(Pattern.matches(PATTERN, cmd)) {

            // This chunk here checks balancing brackets
            LinkedList<Character> stack = new LinkedList<>();
            for(int i = 0; i < cmd.length(); i++) {
                if(cmd.charAt(i) == '[') {
                    stack.addLast(cmd.charAt(i));
                }
                if(cmd.charAt(i) == ']') {
                    if(stack.isEmpty()) {
                        error = true;
                    } else {
                        if(stack.removeLast() != '[') {
                            error = true;
                        }
                    }
                }
            }

            if(!stack.isEmpty()) {
                error = true;
            }

            if(error) {
                throw new Exception("Error - Illegal balancing character ie not a corresponding closing-bracket ']'");
            } else {
                return cmd;
            }

        } else {
            throw new Exception("Error - Illegal characters. Type 'help' for a list of commands");
        }
    }
}
