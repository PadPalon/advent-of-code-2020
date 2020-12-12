package ch.neukom.day11;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StateMachine {
    private final Map<PositionState, PositionStateChange> stateChanges;

    public StateMachine(PositionStateChange... stateChanges) {
        this.stateChanges = Arrays.stream(stateChanges)
            .collect(Collectors.toMap(PositionStateChange::getStartState, Function.identity()));
    }

    public PositionState getNextState(PositionState startState, List<PositionState> neighbours) {
        PositionStateChange positionStateChange = stateChanges.get(startState);
        if (positionStateChange != null && positionStateChange.getPredicate().test(neighbours)) {
            return positionStateChange.getNextState();
        } else {
            return startState;
        }
    }
}
