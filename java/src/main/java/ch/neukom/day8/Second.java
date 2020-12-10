package ch.neukom.day8;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import ch.neukom.helper.InputResourceReader;

import static ch.neukom.day8.Boot.Operation.*;
import static java.util.stream.Collectors.*;

public class Second {
    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(Second.class)) {
            Integer finalState = reader.readDefaultInput()
                .collect(collectingAndThen(toList(), Second::permutateCommands))
                .stream()
                .map(Collection::stream)
                .map(Boot::compileCommands)
                .filter(Second::runsToCompletion)
                .findAny()
                .map(Boot::getAccumulator)
                .orElseThrow();
            System.out.printf("The final state before repeating commands was %d%n", finalState);
        }
    }

    private static boolean runsToCompletion(Boot boot) {
        while (!boot.hasProgramTerminated() && !boot.didCommandRun()) {
            boot.runCommand();
        }
        return boot.hasProgramTerminated();
    }

    private static List<List<String>> permutateCommands(List<String> originalCommands) {
        return IntStream.range(0, originalCommands.size())
            .mapToObj(i -> originalCommands.stream()
                .map(Second::adjustCommand)
                .collect(toList())
            )
            .collect(toList());
    }

    private static String adjustCommand(String commandToAdjust) {
        if (commandToAdjust.contains(JMP.getCommandString())) {
            return commandToAdjust.replace(JMP.getCommandString(), NOP.getCommandString());
        } else if (commandToAdjust.contains(NOP.getCommandString())) {
            return commandToAdjust.replace(NOP.getCommandString(), JMP.getCommandString());
        } else {
            return commandToAdjust;
        }
    }
}
