package ch.neukom.day11;

import java.util.List;
import java.util.function.Predicate;

public enum PositionStateChange {
    EMPTY_TO_FULL(PositionState.EMPTY, neighbours -> neighbours.stream().allMatch(p -> p == PositionState.EMPTY || p == PositionState.FLOOR), PositionState.FULL),
    FOUR_FULL_TO_EMPTY(PositionState.FULL, neighbours -> neighbours.stream().filter(p -> p == PositionState.FULL).count() >= 4, PositionState.EMPTY),
    FIVE_FULL_TO_EMPTY(PositionState.FULL, neighbours -> neighbours.stream().filter(p -> p == PositionState.FULL).count() >= 5, PositionState.EMPTY);

    private final PositionState startState;
    private final Predicate<List<PositionState>> predicate;
    private final PositionState nextState;

    PositionStateChange(PositionState startState, Predicate<List<PositionState>> predicate, PositionState nextState) {
        this.startState = startState;
        this.predicate = predicate;
        this.nextState = nextState;
    }

    public PositionState getStartState() {
        return startState;
    }

    public Predicate<List<PositionState>> getPredicate() {
        return predicate;
    }

    public PositionState getNextState() {
        return nextState;
    }
}
