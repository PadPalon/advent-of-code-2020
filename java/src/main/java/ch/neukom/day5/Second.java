package ch.neukom.day5;

import java.util.stream.IntStream;

import ch.neukom.helper.InputResourceReader;
import org.javatuples.Pair;

import static ch.neukom.day5.SeatPosition.*;

public class Second {
    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(Second.class)) {
            int[] seatIds = reader.readDefaultInput()
                .map(line -> line.chars().mapToObj(c -> PositionMove.valueOf(String.valueOf((char) c))))
                .map(SeatPosition::getSeatPosition)
                .mapToInt(SeatPosition::getSeatId)
                .sorted()
                .toArray();
            int solution = IntStream.range(0, seatIds.length - 1)
                .mapToObj(i -> Pair.with(seatIds[i], seatIds[i + 1]))
                .filter(pair -> pair.getValue1() - pair.getValue0() == 2)
                .mapToInt(pair -> pair.getValue0() + 1)
                .findAny()
                .orElseThrow();
            System.out.printf("Our seat id is %d%n", solution);
        }
    }

}
