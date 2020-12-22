package ch.neukom.day12;

import java.util.Arrays;

public enum Direction {
    NORTH('N'), EAST('E'), SOUTH('S'), WEST('W');

    private final char symbol;

    Direction(char symbol) {
        this.symbol = symbol;
    }

    public static Direction getDirection(char symbol) {
        return Arrays.stream(Direction.values())
                .filter(dir -> dir.getSymbol() == symbol)
                .findAny()
                .orElseThrow();
    }

    public char getSymbol() {
        return symbol;
    }

    public Direction turnLeft(int degrees) {
        Direction[] values = Direction.values();
        return values[(this.ordinal() + 4 - degrees / 90) % values.length];
    }

    public Direction turnRight(int degrees) {
        Direction[] values = Direction.values();
        return values[(this.ordinal() + degrees / 90) % values.length];
    }
}
