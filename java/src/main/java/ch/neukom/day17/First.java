package ch.neukom.day17;

import ch.neukom.helper.InputResourceReader;
import com.google.common.collect.Streams;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.stream.Stream;

public class First {
    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(First.class)) {
            ConwayCubes cubes = new ConwayCubes();
            Streams.mapWithIndex(getInitializationStream(reader), Pair::with)
                    .flatMap(p -> Streams.mapWithIndex(p.getValue0(), (c, index) -> Triplet.with(c, p.getValue1(), index)))
                    .map(t -> Pair.with(t.getValue0(), new ThreeDimensionalCoordinate(t.getValue1(), t.getValue2(), 0L)))
                    .forEach(pair -> cubes.setCube(pair.getValue1(), pair.getValue0()));
            while (cubes.getTurn() < 6) {
                cubes.simulateTurn();
            }
            System.out.printf("After 6 turns %d cubes are active%n", cubes.getActiveCubeCount());
        }
    }

    private static Stream<Stream<Boolean>> getInitializationStream(InputResourceReader reader) {
        return reader.readDefaultInput()
                .map(String::chars)
                .map(c -> c.mapToObj(d -> (char) d))
                .map(c -> c.map(d -> d == '#'));
    }
}
