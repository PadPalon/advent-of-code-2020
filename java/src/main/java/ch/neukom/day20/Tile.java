package ch.neukom.day20;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.Collectors;

import static ch.neukom.day20.StringHelper.*;

public class Tile {
    private final int id;
    private final String data;
    private final Multimap<Direction, Tile> neighbours = HashMultimap.create();

    private Map<String, Direction> borders = null;
    private Map<Direction, String> inverseBorders = null;

    public Tile(int id, String data) {
        this.id = id;
        this.data = data;
    }

    public Map<String, Direction> getBorders() {
        if (borders == null) {
            borders = buildBorders();
            inverseBorders = HashBiMap.create(borders).inverse();
            borders.entrySet()
                    .stream()
                    .map(entry -> Pair.with(reverseString(entry.getKey()), entry.getValue()))
                    .collect(Collectors.toList())
                    .forEach(pair -> borders.put(pair.getValue0(), pair.getValue1()));
        }
        return borders;
    }

    public Map<Direction, String> getInverseBorders() {
        if (inverseBorders == null) {
            inverseBorders = HashBiMap.create(buildBorders()).inverse();
        }
        return inverseBorders;
    }

    private Map<String, Direction> buildBorders() {
        Map<String, Direction> newBorders = new HashMap<>();
        String[] parts = data.split("\n");
        newBorders.put(parts[0], Direction.UP);
        newBorders.put(parts[parts.length - 1], Direction.DOWN);
        String leftBorder = Arrays.stream(parts)
                .map(part -> part.charAt(0))
                .reduce("", (s, character) -> s + character, String::concat);
        newBorders.put(leftBorder, Direction.LEFT);
        String rightBorder = Arrays.stream(parts)
                .map(part -> part.charAt(part.length() - 1))
                .reduce("", (s, character) -> s + character, String::concat);
        newBorders.put(rightBorder, Direction.RIGHT);
        return newBorders;
    }

    public Optional<Direction> findAnyMatchingDirection(Tile other) {
        return Arrays.stream(Direction.values()).filter(dir -> matchesAny(dir, other)).findFirst();
    }

    public Multimap<Direction, Tile> getPossibleNeighbours() {
        return neighbours;
    }

    public Set<String> getMatchingBorders(Set<Tile> tiles) {
        Set<String> tileBorders = tiles.stream()
                .filter(tile -> !tile.equals(this))
                .map(Tile::getBorders)
                .map(Map::keySet)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        return getBorders().keySet().stream().filter(tileBorders::contains).collect(Collectors.toSet());
    }

    public String getBorder(Direction direction) {
        return getInverseBorders().get(direction);
    }

    public boolean matches(Direction direction, Tile other) {
        String border = getInverseBorders().get(direction);
        String otherBorder = other.getBorder(Direction.flip(direction));
        return border.equals(otherBorder);
    }

    public boolean matchesAny(Direction direction, Tile other) {
        String border = getInverseBorders().get(direction);
        return other.getBorders().containsKey(border);
    }

    public Tile flipHorizontal() {
        return new Tile(id, flipStringHorizontally(data));
    }

    public Tile turnLeft() {
        return new Tile(id, turnStringLeft(data));
    }

    public int getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public void setNeighbour(Direction direction, Tile tile) {
        neighbours.put(direction, tile);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tile tile = (Tile) o;

        return id == tile.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
