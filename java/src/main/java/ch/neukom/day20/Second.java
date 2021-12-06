package ch.neukom.day20;

import ch.neukom.helper.InputResourceReader;
import com.google.common.collect.Streams;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static ch.neukom.day20.TileHelper.loadTiles;

public class Second {

    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(Second.class)) {
            Set<Tile> tiles = loadTiles(reader);

            Set<Tile> cornerTiles = tiles.stream()
                    .filter(tile -> tile.getMatchingBorders(tiles).size() == 4)
                    .collect(Collectors.toSet());

            assert cornerTiles.size() == 4;

            Tile someCorner = cornerTiles.stream()
                    .findAny()
                    .orElseThrow();

            Set<Tile> unsetTiles = new HashSet<>(tiles);
            unsetTiles.remove(someCorner);

            findNeighbours(someCorner, unsetTiles);

            String finalMap = createFinalMap(someCorner);

            System.out.println(finalMap);
        }
    }

    private static void findNeighbours(Tile firstCorner, Set<Tile> unsetTiles) {
        TileTransformer transformer = new TileTransformer();

        Deque<Tile> toHandle = new ArrayDeque<>();
        toHandle.push(firstCorner);
        while (!toHandle.isEmpty()) {
            Tile currentTile = toHandle.pop();
            List<Pair<Tile, Direction>> connections = unsetTiles.stream()
                    .map(other -> Pair.with(other, currentTile.findAnyMatchingDirection(other)))
                    .filter(pair -> pair.getValue1().isPresent())
                    .map(pair -> pair.setAt1(pair.getValue1().get()))
                    .collect(Collectors.toList());

            for (Pair<Tile, Direction> connection : connections) {
                Tile connectedTile = connection.getValue0();
                while (!currentTile.matches(connection.getValue1(), connectedTile)) {
                    connectedTile = transformer.transform(connectedTile);
                }
                Direction direction = connection.getValue1();
                currentTile.setNeighbour(direction, connectedTile);
                toHandle.push(connectedTile);
                unsetTiles.remove(connectedTile);
            }
        }
    }

    private static String createFinalMap(Tile firstCorner) {
        return buildGrid(firstCorner)
                .entrySet()
                .stream()
                .map(entry -> entry.getKey().add(cutoffBorder(entry.getValue().getData())))
                .collect(Collectors.groupingBy(Triplet::getValue1))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, List<Triplet<Integer, Integer, String>>>comparingByKey().reversed())
                .map(entries -> entries.getValue()
                        .stream()
                        .sorted(Comparator.comparing(Triplet::getValue0))
                        .map(Triplet::getValue2)
                        .reduce(Second::combineTiles)
                        .orElseThrow())
                .collect(Collectors.joining("\n"));
    }

    private static String cutoffBorder(String data) {
        String[] split = data.split("\n");
        return IntStream.rangeClosed(1, split.length - 2)
                .mapToObj(i -> IntStream.rangeClosed(1, split.length - 2)
                        .mapToObj(j -> split[i].charAt(j))
                        .reduce("", (s, c) -> s + c, String::concat))
                .collect(Collectors.joining("\n"));
    }

    private static Map<Pair<Integer, Integer>, Tile> buildGrid(Tile source) {
        return getNeighbourPositions(source, Pair.with(0, 0));
    }

    private static Map<Pair<Integer, Integer>, Tile> getNeighbourPositions(Tile source, Pair<Integer, Integer> sourcePosition) {
        Map<Pair<Integer, Integer>, Tile> map = new HashMap<>();
        map.put(sourcePosition, source);
        for (Map.Entry<Direction, Tile> entry : source.getPossibleNeighbours().entries()) {
            Pair<Integer, Integer> newPosition = calculateNewPosition(sourcePosition, entry.getKey());
            Tile newTile = entry.getValue();
            map.put(newPosition, newTile);
            map.putAll(getNeighbourPositions(newTile, newPosition));
        }
        return map;
    }

    private static Pair<Integer, Integer> calculateNewPosition(Pair<Integer, Integer> position, Direction direction) {
        int x = position.getValue0();
        int y = position.getValue1();
        int newX = switch (direction) {
            case RIGHT -> x + 1;
            case LEFT -> x - 1;
            default -> x;
        };
        int newY = switch (direction) {
            case UP -> y + 1;
            case DOWN -> y - 1;
            default -> y;
        };
        return Pair.with(newX, newY);
    }

    private static String combineTiles(String left, String right) {
        String[] leftSplit = left.split("\n");
        String[] rightSplit = right.split("\n");
        return Streams.zip(Arrays.stream(leftSplit), Arrays.stream(rightSplit), String::concat)
                .collect(Collectors.joining("\n"));
    }
}
