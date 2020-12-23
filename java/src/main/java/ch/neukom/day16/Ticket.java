package ch.neukom.day16;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Ticket {
    private final List<Integer> values;

    public Ticket(String values) {
        this.values = Arrays.stream(values.split(",")).map(Integer::parseInt).collect(Collectors.toList());
    }

    public List<Integer> getValues() {
        return values;
    }
}
