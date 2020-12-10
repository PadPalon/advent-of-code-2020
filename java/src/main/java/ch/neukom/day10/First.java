package ch.neukom.day10;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import ch.neukom.helper.InputResourceReader;

import static java.util.stream.Collectors.*;

public class First {
    private static final Set<Integer> DIFFERENCES_TO_MULTIPLY = Set.of(1, 3);

    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(First.class)) {
            int[] adapterChain = reader.readDefaultInput()
                .mapToInt(Integer::parseInt)
                .sorted()
                .toArray();
            int solution = IntStream.range(-1, adapterChain.length)
                .map(i -> calculateDifference(adapterChain, i))
                .boxed()
                .collect(collectingAndThen(groupingBy(i -> i), Map::entrySet))
                .stream()
                .filter(entry -> DIFFERENCES_TO_MULTIPLY.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .mapToInt(Collection::size)
                .reduce((left, right) -> left * right)
                .orElseThrow();
            System.out.printf("The solution is %d%n", solution);
        }
    }

    private static int calculateDifference(int[] adapterChain, int i) {
        if (i + 1 >= adapterChain.length) {
            return 3;
        } else if (i < 0) {
            return adapterChain[0];
        } else {
            return adapterChain[i + 1] - adapterChain[i];
        }
    }
}
