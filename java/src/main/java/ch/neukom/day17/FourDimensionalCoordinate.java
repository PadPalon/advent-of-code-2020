package ch.neukom.day17;

import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class FourDimensionalCoordinate implements Coordinate {
    private final int x;
    private final int y;
    private final int z;
    private final int w;

    public FourDimensionalCoordinate(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public FourDimensionalCoordinate(Long x, Long y, Long z, Long w) {
        this.x = x.intValue();
        this.y = y.intValue();
        this.z = z.intValue();
        this.w = w.intValue();
    }

    public List<Coordinate> getNeighbours() {
        Set<List<Supplier<Integer>>> positionChanges = Sets.cartesianProduct(
                Set.of(() -> x, () -> x + 1, () -> x - 1),
                Set.of(() -> y, () -> y + 1, () -> y - 1),
                Set.of(() -> z, () -> z + 1, () -> z - 1),
                Set.of(() -> w, () -> w + 1, () -> w - 1)
        );
        return positionChanges
                .stream()
                .map(Collection::stream)
                .map(suppliers -> suppliers.map(Supplier::get))
                .map(coords -> coords.toArray(Integer[]::new))
                .map(coords -> new FourDimensionalCoordinate(coords[0], coords[1], coords[2], coords[3]))
                .filter(coord -> !coord.equals(this))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "[%d,%d,%d,%d]".formatted(x, y, z, w);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FourDimensionalCoordinate that = (FourDimensionalCoordinate) o;

        if (x != that.x) return false;
        if (y != that.y) return false;
        if (z != that.z) return false;
        return w == that.w;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        result = 31 * result + w;
        return result;
    }
}
