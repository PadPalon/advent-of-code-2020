package ch.neukom.day20;

import java.util.List;
import java.util.function.Function;

public class TileTransformer {
    private final List<Function<Tile, Tile>> transformations = List.of(
            Tile::flipHorizontal,
            Tile::turnLeft,
            Tile::turnLeft,
            Tile::turnLeft,
            Tile::flipHorizontal,
            Tile::turnLeft,
            Tile::turnLeft,
            Tile::turnLeft
    );

    private int currentTransformation = 0;

    public Tile transform(Tile tile) {
        currentTransformation = (currentTransformation + 1) % transformations.size();
        return transformations.get(currentTransformation).apply(tile);
    }
}
