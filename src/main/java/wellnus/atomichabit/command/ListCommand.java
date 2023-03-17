package wellnus.atomichabit.command;

import wellnus.atomichabit.feature.AtomicHabit;
import wellnus.atomichabit.feature.AtomicHabitList;
import wellnus.atomichabit.feature.AtomicHabitManager;
import wellnus.command.Command;
import wellnus.exception.BadCommandException;
import wellnus.ui.TextUi;

import java.util.HashMap;

public class ListCommand extends Command {
    private static final String COMMAND_KEYWORD = "list";
    private static final int COMMAND_MAX_NUM_OF_ARGUMENTS = 1;
    private static final String COMMAND_DETAILED_DESCRIPTION = "";
    private static final String COMMAND_INVALID_ARGUMENTS_MESSAGE = "Invalid command, expected 'hb list'";
    private static final String COMMAND_SUPPORTED_ARGUMENTS = "";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String DOT = ".";
    private static final String FIRST_STRING = "Here is the current accumulation of your atomic habits!"
            + LINE_SEPARATOR + "Keep up the good work and you will develop a helpful habit in no time";
    private final AtomicHabitList atomicHabits;
    private final TextUi textUi;

    public ListCommand(HashMap<String, String> arguments, AtomicHabitList atomicHabits) {
        super(arguments);
        this.atomicHabits = atomicHabits;
        this.textUi = new TextUi();
    }

    private TextUi getTextUi() {
        return textUi;
    }

    /**
     * Identifies this Command's keyword. Override this in subclasses so
     * toString() returns the correct String representation.
     *
     * @return String Keyword of this Command
     */
    @Override
    protected String getCommandKeyword() {
        return COMMAND_KEYWORD;
    }

    /**
     * Returns a detailed user-friendly description of what this specific command does.
     *
     * @return String Detailed explanation of this command
     */
    @Override
    protected String getDetailedDescription() {
        return COMMAND_DETAILED_DESCRIPTION;
    }

    /**
     * Identifies the feature that this Command is associated with. Override
     * this in subclasses so toString() returns the correct String representation.
     *
     * @return String Keyword for the feature associated with this Command
     */
    @Override
    protected String getFeatureKeyword() {
        return AtomicHabitManager.FEATURE_NAME;
    }

    /**
     * Returns all the supported arguments for this Command.
     *
     * @return String All supported arguments for this Command
     */
    @Override
    protected String getSupportedCommandArguments() {
        return COMMAND_SUPPORTED_ARGUMENTS;
    }

    /**
     * Executes the list command for atomic habits, which prints all atomic habits
     * added by the user so far.
     */
    @Override
    public void execute() {
        try {
            validateCommand(super.getArguments());
        } catch (BadCommandException badCommandException) {
            String NO_ADDITIONAL_MESSAGE = "";
            this.getTextUi().printErrorFor(badCommandException, NO_ADDITIONAL_MESSAGE);
            return;
        }
        int taskNo = 1;
        StringBuilder stringOfHabitsBuilder = new StringBuilder(FIRST_STRING + LINE_SEPARATOR);
        for (AtomicHabit habit : atomicHabits.getAllHabits()) {
            String currentHabitString = String.format("%d.%s [%d]",
                    taskNo, habit.toString(), habit.getCount());
            stringOfHabitsBuilder.append(currentHabitString).append(LINE_SEPARATOR);
            taskNo += 1;
        }
        int FIRST_CHAR = 0;
        String messageToUser = stringOfHabitsBuilder.substring(FIRST_CHAR,
                stringOfHabitsBuilder.length() - 1);
        getTextUi().printOutputMessage(messageToUser);
    }

    /**
     * Validate the arguments and payloads from a commandMap generated by CommandParser.<br>
     * <p>
     * If no exceptions are thrown, arguments are valid.
     *
     * @param arguments Argument-Payload map generated by CommandParser
     * @throws BadCommandException If the commandMap has any issues
     */
    @Override
    public void validateCommand(HashMap<String, String> arguments) throws BadCommandException {
        if (!arguments.containsKey(ListCommand.COMMAND_KEYWORD)) {
            throw new BadCommandException(ListCommand.COMMAND_INVALID_ARGUMENTS_MESSAGE);
        }
        if (arguments.get(COMMAND_KEYWORD) != "") {
            throw new BadCommandException(ListCommand.COMMAND_INVALID_ARGUMENTS_MESSAGE);
        }
        if (arguments.size() > ListCommand.COMMAND_MAX_NUM_OF_ARGUMENTS) {
            throw new BadCommandException(ListCommand.COMMAND_INVALID_ARGUMENTS_MESSAGE);
        }
    }
}

