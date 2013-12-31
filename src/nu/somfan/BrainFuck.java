package nu.somfan;

/**
 * Created by Emil on 2013-12-29.
 */
public class BrainFuck {

    private static final String EXIT = "EXIT";
    private static final String HELP = "HELP";

    private boolean mExit;
    private String mCmd;
    private Command mCommand;

    private BrainFuck() {}


    private void initialize() {
        mExit = false;
    }

    /**
     * The program-loop
     */
    public void start() {

        initialize();

        // Do until user chooses to exit
        while(!mExit) {

            // Read command input from user
            mCmd = BrainFuckReader.read();

            if(mCmd.equals(EXIT)) {
                mExit = true;
            } else if(mCmd.equals(HELP)) {
                printHelp();
            } else {
                // Handles command
                checkCommand();
            }
        }

    }

    /**
     * Messy helper method for printing help
     */
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

    /**
     * Tries to read, parse and execute the command
     */
    private void checkCommand() {

        String newCmd;

        try {
            newCmd = BrainFuckReader.readCommand(mCmd);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        // Execute
        mCommand = Command.valueOf(newCmd);
        mCommand.execute();
    }

    public static BrainFuck valueOf() {
        return new BrainFuck();
    }
}
