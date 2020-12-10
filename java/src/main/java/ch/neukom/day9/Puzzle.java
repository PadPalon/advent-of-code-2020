package ch.neukom.day9;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.neukom.helper.InputResourceReader;
import com.google.common.collect.Iterables;

public class Puzzle {
    private static final int WORKING_SET_SIZE = 25;

    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(Puzzle.class)) {
            List<Long> values = reader.readDefaultInput()
                .map(Long::parseLong)
                .collect(Collectors.toList());
            LinkedList<Long> workingSet = new LinkedList<>(values.subList(0, WORKING_SET_SIZE));
            Long nonSumValue = values.stream()
                .skip(WORKING_SET_SIZE)
                .dropWhile(value -> isSumOf(value, workingSet))
                .findFirst()
                .orElseThrow();

            System.out.printf("The first value that is not a sum of previous numbers is %d%n", nonSumValue);

            long weakness = findWeakness(values, nonSumValue);

            System.out.printf("The weakness of the system is %d%n", weakness);
        }
    }

    private static boolean isSumOf(Long value, LinkedList<Long> workingSet) {
        boolean isSum = generateCombinations(workingSet)
            .mapToLong(i -> i)
            .anyMatch(value::equals);
        if (isSum) {
            workingSet.removeFirst();
            workingSet.addLast(value);
            return true;
        } else {
            return false;
        }
    }

    public static Stream<Long> generateCombinations(LinkedList<Long> workingSet) {
        return Stream.generate(createCombinationSupplier(workingSet))
            .limit(workingSet.size() * (workingSet.size() - 1) / 2);
    }

    public static Supplier<Long> createCombinationSupplier(LinkedList<Long> workingSet) {
        return new Supplier<>() {
            private int i = 0;
            private int j = 1;

            @Override
            public Long get() {
                if (i > workingSet.size() || j > workingSet.size()) {
                    throw new IllegalStateException("Reached end of the working set");
                }

                Long first = workingSet.get(i);
                Long second = workingSet.get(j);
                j++;
                if (j >= workingSet.size()) {
                    i++;
                    j = i + 1;
                }
                return first + second;
            }
        };
    }

    private static long findWeakness(List<Long> values, Long nonSumValue) {
        for (int i = 0; i < values.size(); i++) {
            Set<Long> handledValues = new TreeSet<>();
            Long value = values.get(i);
            handledValues.add(value);

            Long sum = value;
            int j = 1;
            while (sum < nonSumValue) {
                Long nextValue = values.get(i + j);
                handledValues.add(nextValue);
                sum += nextValue;
                j++;
            }

            if (sum.equals(nonSumValue)) {
                Long min = Iterables.getFirst(handledValues, 0L);
                Long max = Iterables.getLast(handledValues);
                return min + max;
            }
        }
        throw new IllegalStateException("Did not find weakness");
    }
}
