package ch.neukom.day11;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ch.neukom.helper.InputResourceReader;
import com.google.common.collect.Streams;
import org.javatuples.Pair;
import org.javatuples.Triplet;

public class Helper {
    public static PositionState[][] loadMap(InputResourceReader reader, int width, int height) {
        PositionState[][] map = new PositionState[width][height];
        Streams.mapWithIndex(reader.readDefaultInput(), (line, i) -> Pair.with((int) i, line.chars()))
            .flatMap(chars -> Streams.mapWithIndex(chars.getValue1(), (c, i) -> buildPosition(chars, c, (int) i)))
            .forEach(triples -> map[triples.getValue0()][triples.getValue1()] = triples.getValue2());
        return map;
    }

    private static Triplet<Integer, Integer, PositionState> buildPosition(Pair<Integer, IntStream> chars, int c, int i) {
        return Triplet.with(i, chars.getValue0(), PositionState.parsePosition(c));
    }

    public static long countFilledSeats(PositionState[][] map) {
        return Arrays.stream(map)
            .flatMap(Arrays::stream)
            .filter(pos -> pos == PositionState.FULL)
            .count();
    }

    public static PositionState[][] deepCopy(PositionState[][] map) {
        return Arrays.stream(map).map(array -> Arrays.copyOf(array, array.length)).toArray(PositionState[][]::new);
    }

    @SuppressWarnings("unused") //used for debugging
    private static void printMap(PositionState[][] map) {
        String mapString = Arrays.stream(map)
            .map(Arrays::stream)
            .map(positions -> positions.map(PositionState::getSymbol))
            .map(positions -> positions.map(String::valueOf))
            .map(positions -> positions.collect(Collectors.joining()))
            .collect(Collectors.joining("\n"));
        System.out.printf("%nMAP:%n%s%n", mapString);
    }
}
