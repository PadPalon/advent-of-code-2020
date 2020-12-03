package ch.neukom.day3;

import java.util.concurrent.atomic.AtomicInteger;

import ch.neukom.helper.InputResourceReader;
import org.javatuples.Pair;

public class First {
    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(First.class)) {
            AtomicInteger index = new AtomicInteger(0);
            long solution = reader.readDefaultInput()
                .map(line -> Pair.with(line, index.getAndIncrement() * 3))
                .map(First::getCharAtPosition)
                .filter(c -> c == '#')
                .count();
            System.out.printf("Hit %d trees%n", solution);
        }
    }

    private static char getCharAtPosition(Pair<String, Integer> pair) {
        String line = pair.getValue0();
        int position = pair.getValue1();
        return line.charAt(position % line.length());
    }
}
