package ch.neukom.day13;

import ch.neukom.helper.InputResourceReader;
import com.google.common.base.CharMatcher;
import org.javatuples.Pair;

import java.util.Arrays;
import java.util.Comparator;

public class First {
    private static final CharMatcher DIGIT_MATCHER = CharMatcher.inRange('0', '9');

    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(First.class)) {
            String[] lines = reader.readDefaultInput().toArray(String[]::new);
            int earliestDeparture = Integer.parseInt(lines[0]);
            Pair<Integer, Integer> earliestBus = Arrays.stream(lines[1].split(","))
                    .filter(DIGIT_MATCHER::matchesAllOf)
                    .map(Integer::parseInt)
                    .map(i -> Pair.with(i, (earliestDeparture / i + 1) * i))
                    .min(Comparator.comparingInt(Pair::getValue1))
                    .orElseThrow();
            int solution = (earliestBus.getValue1() - earliestDeparture) * earliestBus.getValue0();
            System.out.printf("The solution is %d%n", solution);
        }
    }
}
