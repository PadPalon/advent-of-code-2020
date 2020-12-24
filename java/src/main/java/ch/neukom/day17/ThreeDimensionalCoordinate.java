package ch.neukom.day17;

import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ThreeDimensionalCoordinate implements Coordinate {
    private final int x;
    private final int y;
    private final int z;

    public ThreeDimensionalCoordinate(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ThreeDimensionalCoordinate(Long x, Long y, Long z) {
        this.x = x.intValue();
        this.y = y.intValue();
        this.z = z.intValue();
    }

    public List<Coordinate> getNeighbours() {
        Set<List<Supplier<Integer>>> positionChanges = Sets.cartesianProduct(
                Set.of(() -> x, () -> x + 1, () -> x - 1),
                Set.of(() -> y, () -> y + 1, () -> y - 1),
                Set.of(() -> z, () -> z + 1, () -> z - 1)
        );
        return positionChanges
                .stream()
                .map(Collection::stream)
                .map(suppliers -> suppliers.map(Supplier::get))
                .map(coords -> coords.toArray(Integer[]::new))
                .map(coords -> new ThreeDimensionalCoordinate(coords[0], coords[1], coords[2]))
                .filter(coord -> !coord.equals(this))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "[%d,%d,%d]".formatted(x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ThreeDimensionalCoordinate that = (ThreeDimensionalCoordinate) o;

        if (x != that.x) return false;
        if (y != that.y) return false;
        return z == that.z;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }
}
