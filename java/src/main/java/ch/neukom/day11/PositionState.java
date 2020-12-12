package ch.neukom.day11;

import java.util.Arrays;
import java.util.List;

public enum PositionState {
    FLOOR('.'),
    EMPTY('L'),
    FULL('#');

    private final char symbol;

    PositionState(char symbol) {
        this.symbol = symbol;
    }

    public static PositionState parsePosition(int c) {
        return Arrays.stream(PositionState.values())
            .filter(p -> p.getSymbol() == c)
            .findAny()
            .orElseThrow();
    }

    public char getSymbol() {
        return symbol;
    }

    public PositionState getNextState(List<PositionState> neighbours) {
        return Arrays.stream(PositionStateChange.values())
            .filter(p -> p.getStartState() == this)
            .filter(p -> p.getPredicate().test(neighbours))
            .findAny()
            .map(PositionStateChange::getNextState)
            .orElse(this);
    }
}
