package ch.neukom.day15;

import ch.neukom.helper.InputResourceReader;
import com.google.common.collect.Streams;
import org.javatuples.Pair;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class Puzzle {
    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(Puzzle.class)) {
            Map<Long, Long> history = loadHistory(reader);
            Map.Entry<Long, Long> startingValues = getLatestValueFromHistory(history);
            int targetTurn = 30000000; // 30000000 used for second part, change to 2020 for first part
            Long lastNumber = runGame(history, startingValues, targetTurn);
            System.out.printf("The %d number spoken was %d%n", targetTurn, lastNumber);
        }
    }

    private static Long runGame(Map<Long, Long> history, Map.Entry<Long, Long> startingValues, int endTurn) {
        Long lastNumber = startingValues.getKey();
        history.remove(lastNumber);
        Long turnNumber = startingValues.getValue();
        while (turnNumber < endTurn - 1) {
            Long lastTurnMentioned = history.get(lastNumber);
            history.put(lastNumber, turnNumber);
            if (lastTurnMentioned == null) {
                lastNumber = 0L;
            } else {
                lastNumber = turnNumber - lastTurnMentioned;
            }
            turnNumber++;
        }
        return lastNumber;
    }

    private static Map<Long, Long> loadHistory(InputResourceReader reader) {
        return reader.readDefaultInput()
                .map(line -> line.split(","))
                .map(Arrays::stream)
                .map(stream -> stream.mapToLong(Long::parseLong))
                .flatMap(stream -> Streams.mapWithIndex(stream, Pair::with))
                .collect(Collectors.toMap(Pair::getValue0, Pair::getValue1));
    }

    private static Map.Entry<Long, Long> getLatestValueFromHistory(Map<Long, Long> history) {
        return history.entrySet()
                .stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .orElseThrow();
    }
}
