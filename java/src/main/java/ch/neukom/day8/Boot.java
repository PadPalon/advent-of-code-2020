package ch.neukom.day8;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class Boot {
    private static final Pattern COMMAND_PATTERN = Pattern.compile("([a-z]{3}) ([-+][0-9]+)");

    private final List<Command> commands;
    private final Set<Integer> executedCommands = new HashSet<>();

    private int position = 0;
    private int accumulator = 0;

    public Boot(List<Command> commands) {
        this.commands = List.copyOf(commands);
    }

    public static Boot compileCommands(Stream<String> input) {
        return input
            .map(COMMAND_PATTERN::matcher)
            .filter(Matcher::find)
            .map(matcher -> new Command(
                Operation.valueOf(matcher.group(1).toUpperCase()),
                Integer.parseInt(matcher.group(2))
            ))
            .collect(collectingAndThen(toList(), Boot::new));
    }

    public boolean didCommandRun() {
        return executedCommands.contains(position);
    }

    public boolean hasProgramTerminated() {
        return position >= commands.size();
    }

    public void runCommand() {
        Command command = commands.get(position);
        executedCommands.add(position);
        switch (command.getOperation()) {
            case NOP -> position += 1;
            case JMP -> position += command.getArgument();
            case ACC -> {
                position += 1;
                accumulator += command.getArgument();
            }
            default -> throw new IllegalArgumentException(String.format("Unknown command %s", command.getOperation()));
        }
    }

    public int getAccumulator() {
        return accumulator;
    }

    public static class Command {
        private final Operation operation;
        private final int argument;

        public Command(Operation operation, int argument) {
            this.operation = operation;
            this.argument = argument;
        }

        public Operation getOperation() {
            return operation;
        }

        public int getArgument() {
            return argument;
        }
    }

    public enum Operation {
        NOP("nop"),
        JMP("jmp"),
        ACC("acc");

        private final String commandString;

        Operation(String commandString) {
            this.commandString = commandString;
        }

        public String getCommandString() {
            return commandString;
        }
    }
}
