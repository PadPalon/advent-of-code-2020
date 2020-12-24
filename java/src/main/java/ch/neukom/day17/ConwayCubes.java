package ch.neukom.day17;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ConwayCubes {
    private final Map<Coordinate, Boolean> map = new HashMap<>();

    private int turn = 0;

    public void setCube(Coordinate coordinate, boolean value) {
        if (turn == 0) {
            map.put(coordinate, value);
            expandAroundCoordinate(coordinate);
        } else {
            throw new IllegalStateException("Simulation has started, do not interfere with The Cubes");
        }
    }

    public void simulateTurn() {
        Map<Coordinate, Boolean> newMap = new HashMap<>();
        Set<Coordinate> knownCoordinates = Set.copyOf(map.keySet());
        for (Coordinate coordinate : knownCoordinates) {
            Boolean cubeState = map.get(coordinate);
            long activeNeighbours = getNeighbourValues(coordinate).stream().filter(v -> v).count();
            if (cubeState != null && cubeState) {
                boolean wrongNumberOfActiveNeighbours = activeNeighbours < 2 || activeNeighbours > 3;
                newMap.put(coordinate, !wrongNumberOfActiveNeighbours);
            } else {
                boolean threeNeighboursActive = activeNeighbours == 3;
                newMap.put(coordinate, threeNeighboursActive);
            }
        }
        map.putAll(newMap);
        turn++;
    }

    public int getTurn() {
        return turn;
    }

    public long getActiveCubeCount() {
        return map.values().stream().filter(v -> v).count();
    }

    private void expandAroundCoordinate(Coordinate coordinate) {
        coordinate.getNeighbours()
                .stream()
                .filter(c -> !map.containsKey(c))
                .forEach(c -> map.put(c, false));
    }

    private List<Boolean> getNeighbourValues(Coordinate coordinate) {
        return coordinate.getNeighbours()
                .stream()
                .map(neighbour -> map.computeIfAbsent(neighbour, c -> false))
                .collect(Collectors.toList());
    }
}
