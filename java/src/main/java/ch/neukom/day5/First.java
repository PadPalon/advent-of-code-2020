package ch.neukom.day5;

import ch.neukom.helper.InputResourceReader;

import static ch.neukom.day5.SeatPosition.*;

public class First {
    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(First.class)) {
            int solution = reader.readDefaultInput()
                .map(line -> line.chars().mapToObj(c -> PositionMove.valueOf(String.valueOf((char) c))))
                .map(SeatPosition::getSeatPosition)
                .mapToInt(SeatPosition::getSeatId)
                .max()
                .orElseThrow();
            System.out.printf("The highest seat id is %d%n", solution);
        }
    }

}
