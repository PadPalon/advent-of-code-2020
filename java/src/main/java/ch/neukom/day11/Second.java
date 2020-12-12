package ch.neukom.day11;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.neukom.helper.InputResourceReader;
import org.javatuples.Pair;

import static ch.neukom.day11.Helper.*;

public class Second {
    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(Second.class)) {
            int height = (int) reader.getLineCount();
            int width = reader.getFirstLine().length();
            PositionState[][] map = loadMap(reader, width, height);
            PositionState[][] finalState = runSimulation(map, width, height);
            long filledSeatCount = countFilledSeats(finalState);
            System.out.printf("%d seats are filled%n", filledSeatCount);
        }
    }

    private static PositionState[][] runSimulation(PositionState[][] map, int width, int height) {
        StateMachine stateMachine = new StateMachine(PositionStateChange.EMPTY_TO_FULL, PositionStateChange.FIVE_FULL_TO_EMPTY);
        PositionState[][] currentState = deepCopy(map);
        PositionState[][] nextState = deepCopy(map);
        do {
            PositionState[][] temp = currentState;
            currentState = nextState;
            nextState = temp;
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    PositionState positionState = currentState[x][y];
                    List<PositionState> neighbours = getNeighbours(currentState, x, y, width, height);
                    nextState[x][y] = stateMachine.getNextState(positionState, neighbours);
                }
            }
        } while (Arrays.compare(currentState, nextState, Arrays::compare) != 0);
        return nextState;
    }

    private static List<PositionState> getNeighbours(PositionState[][] map, int x, int y, int width, int height) {
        Stream<Pair<Function<Integer, Integer>, Function<Integer, Integer>>> neighbourPositions = Stream.of(
            Pair.with(i -> i, i -> i + 1),
            Pair.with(i -> i, i -> i - 1),
            Pair.with(i -> i + 1, i -> i),
            Pair.with(i -> i - 1, i -> i),
            Pair.with(i -> i + 1, i -> i + 1),
            Pair.with(i -> i + 1, i -> i - 1),
            Pair.with(i -> i - 1, i -> i + 1),
            Pair.with(i -> i - 1, i -> i - 1)
        );
        return neighbourPositions
            .map(pair -> {
                PositionState position = PositionState.FLOOR;
                int newX = pair.getValue0().apply(x);
                int newY = pair.getValue1().apply(y);
                while (position == PositionState.FLOOR) {
                    if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
                        position = map[newX][newY];
                        newX = pair.getValue0().apply(newX);
                        newY = pair.getValue1().apply(newY);
                    } else {
                        return null;
                    }
                }
                return position;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }
}
