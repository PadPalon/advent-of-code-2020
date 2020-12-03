package ch.neukom.day3;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ch.neukom.helper.InputResourceReader;
import org.javatuples.Pair;

public class Second {
    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(Second.class)) {
            List<String> lines = reader.readDefaultInput().collect(Collectors.toList());
            int width = lines.get(0).length();
            int height = lines.size();
            char[][] map = convertLinesToArray(lines, width, height);

            long solution = getSlopes()
                .stream()
                .mapToLong(slope -> countTreesOnSlope(width, height, map, slope))
                .reduce(Math::multiplyExact)
                .orElseThrow();

            System.out.printf("%nThe solution is %d%n", solution);
        }
    }

    private static char[][] convertLinesToArray(List<String> lines, int width, int height) {
        char[][] map = new char[width][height];
        for (int y = 0; y < height; y++) {
            String line = lines.get(y);
            for (int x = 0; x < width; x++) {
                map[x][y] = line.charAt(x);
            }
        }
        return map;
    }

    private static List<Pair<Integer, Integer>> getSlopes() {
        return List.of(
            Pair.with(1, 1),
            Pair.with(1, 3),
            Pair.with(1, 5),
            Pair.with(1, 7),
            Pair.with(2, 1)
        );
    }

    private static long countTreesOnSlope(int width,
                                          int height,
                                          char[][] map,
                                          Pair<Integer, Integer> stepSize) {
        Integer xStep = stepSize.getValue1();
        Integer yStep = stepSize.getValue0();
        AtomicInteger xPos = new AtomicInteger(0);
        AtomicInteger yPos = new AtomicInteger(0);
        return IntStream.range(0, (height - 1) / yStep)
            .map(i -> map[xPos.addAndGet(xStep) % width][yPos.addAndGet(yStep)])
            .filter(c -> c == '#')
            .count();
    }
}
