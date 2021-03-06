package ch.neukom.day14;

import ch.neukom.helper.InputResourceReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class First {
    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(First.class)) {
            List<Command> commands = reader.readDefaultInput()
                    .map(Command::parseWithValueMask)
                    .collect(Collectors.toList());

            Map<Long, Long> memory = new HashMap<>();
            Bitmask currentBitmask = null;
            for (Command command : commands) {
                if (command.isBitmask()) {
                    currentBitmask = command.getBitmask();
                } else if (currentBitmask != null) {
                    List<Long> applied = currentBitmask.apply(command.getValue());
                    memory.put(command.getAddress(), applied.get(0));
                }
            }

            long sum = memory.values().stream().mapToLong(i -> i).sum();
            System.out.printf("The solution is %d%n", sum);
        }
    }
}
