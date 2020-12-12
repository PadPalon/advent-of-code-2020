package ch.neukom.day11;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.neukom.helper.InputResourceReader;
import org.javatuples.Pair;

import static ch.neukom.day11.Helper.*;
import static ch.neukom.day11.PositionStateChange.*;

public class First {
    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(First.class)) {
            int height = (int) reader.getLineCount();
            int width = reader.getFirstLine().length();
            PositionState[][] map = loadMap(reader, width, height);
            PositionState[][] finalState = runSimulation(map, width, height);
            long filledSeatCount = countFilledSeats(finalState);
            System.out.printf("%d seats are filled%n", filledSeatCount);
        }
    }

    private static PositionState[][] runSimulation(PositionState[][] map, int width, int height) {
        StateMachine stateMachine = new StateMachine(EMPTY_TO_FULL, FOUR_FULL_TO_EMPTY);
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
        Stream<Pair<Integer, Integer>> neighbourPositions = Stream.of(
            Pair.with(x, y + 1),
            Pair.with(x, y - 1),
            Pair.with(x + 1, y),
            Pair.with(x - 1, y),
            Pair.with(x + 1, y + 1),
            Pair.with(x + 1, y - 1),
            Pair.with(x - 1, y + 1),
            Pair.with(x - 1, y - 1)
        );
        return neighbourPositions
            .filter(pair -> pair.getValue0() > 0)
            .filter(pair -> pair.getValue0() < width)
            .filter(pair -> pair.getValue1() > 0)
            .filter(pair -> pair.getValue1() < height)
            .map(pair -> map[pair.getValue0()][pair.getValue1()])
            .collect(Collectors.toList());
    }
}
