package ch.neukom.day20;

import ch.neukom.helper.InputResourceReader;

import java.util.Set;
import java.util.stream.Collectors;

import static ch.neukom.day20.TileHelper.loadTiles;

public class First {
    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(First.class)) {
            Set<Tile> tiles = loadTiles(reader);

            Set<Tile> cornerTiles = tiles.stream()
                    .filter(tile -> tile.getMatchingBorders(tiles).size() == 4)
                    .collect(Collectors.toSet());

            assert cornerTiles.size() == 4;

            long result = cornerTiles.stream()
                    .mapToInt(Tile::getId)
                    .mapToLong(i -> (long) i)
                    .reduce(Math::multiplyExact)
                    .orElseThrow();

            System.out.printf("Multiplying the ids of the corner tiles results in %d%n", result);
        }
    }
}
