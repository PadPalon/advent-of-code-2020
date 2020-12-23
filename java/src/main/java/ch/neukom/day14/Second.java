package ch.neukom.day14;

import ch.neukom.helper.InputResourceReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Second {
    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(Second.class)) {
            List<Command> commands = reader.readDefaultInput()
                    .map(Command::parseWithAddressMask)
                    .collect(Collectors.toList());

            Map<Long, Long> memory = new HashMap<>();
            Bitmask currentBitmask = null;
            for (Command command : commands) {
                if (command.isBitmask()) {
                    currentBitmask = command.getBitmask();
                } else if (currentBitmask != null) {
                    currentBitmask.apply(command.getAddress())
                            .forEach(address -> memory.put(address, command.getValue()));

                }
            }

            long sum = memory.values().stream().mapToLong(i -> i).sum();
            System.out.printf("The solution is %d%n", sum);
        }
    }
}
