package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Calculates current CAP based on NUS modules the user has taken.
 */
public class CapCommand extends Command {

    public static final String COMMAND_WORD = "cap";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Calculates your current CAP based on the modules";

    public static final String MESSAGE_SUCCESS = "CAP calculated";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        double result = model.getCap();
        String message = MESSAGE_SUCCESS + "\n Current CAP: " + result;
        return new CommandResult(message);
    }
}
