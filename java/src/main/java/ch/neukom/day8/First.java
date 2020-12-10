package ch.neukom.day8;

import ch.neukom.helper.InputResourceReader;

public class First {
    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(First.class)) {
            Boot boot = Boot.compileCommands(reader.readDefaultInput());

            while (!boot.didCommandRun()) {
                boot.runCommand();
            }

            int state = boot.getAccumulator();
            System.out.printf("The final state before repeating commands was %d%n", state);
        }
    }
}
