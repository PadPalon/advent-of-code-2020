package ch.neukom.day12;

import ch.neukom.helper.InputResourceReader;
import org.javatuples.Pair;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Puzzle {
    private static final Pattern LINE_PATTERN = Pattern.compile("([NSWELRF])([0-9]+)");

    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(Puzzle.class)) {
            Ship ship = new OrderShip();
            Ship waypointShip = new WaypointShip();
            reader.readDefaultInput()
                    .map(LINE_PATTERN::matcher)
                    .filter(Matcher::find)
                    .map(matcher -> Pair.with(matcher.group(1).charAt(0), Integer.parseInt(matcher.group(2))))
                    .forEach(move -> {
                        ship.executeMove(move.getValue0(), move.getValue1());
                        waypointShip.executeMove(move.getValue0(), move.getValue1());
                    });
            System.out.printf("The distance of the direct order ship after moving is %d%n", ship.calculateDistanceFromStart());
            System.out.printf("The distance of the waypoint ship after moving is %d%n", waypointShip.calculateDistanceFromStart());
        }
    }
}
