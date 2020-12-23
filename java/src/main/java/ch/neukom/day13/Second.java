package ch.neukom.day13;

import ch.neukom.helper.InputResourceReader;
import com.google.common.base.CharMatcher;
import com.google.common.collect.Streams;
import org.javatuples.Pair;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Second {
    private static final CharMatcher DIGIT_MATCHER = CharMatcher.inRange('0', '9');

    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(First.class)) {
            String[] busses = parseBusses(reader);
            List<Pair<BigInteger, BigInteger>> constraints = buildConstraints(busses);
            BigInteger currentTimestamp = calculateTimestamp(constraints);
            boolean isValid = constraints.stream().allMatch(constraint -> fitsConstraint(currentTimestamp, constraint));
            if (isValid) {
                System.out.printf("The first timestamp where all busses depart according to their offsets is %d%n", currentTimestamp);
            } else {
                System.out.println("No fitting timestamp could be found");
            }
        }
    }

    private static String[] parseBusses(InputResourceReader reader) {
        String busLine = reader.getLastLine();
        return busLine.split(",");
    }

    /**
     * @param busses the ids of the busses
     * @return a list of pairs containing the id and the offset of the busses
     */
    private static List<Pair<BigInteger, BigInteger>> buildConstraints(String[] busses) {
        return Streams.mapWithIndex(Arrays.stream(busses), Pair::with)
                .filter(pair -> DIGIT_MATCHER.matchesAllOf(pair.getValue0()))
                .map(pair -> pair.setAt0(Long.parseLong(pair.getValue0())))
                .map(pair -> pair.setAt0(BigInteger.valueOf((pair.getValue0()))))
                .map(pair -> pair.setAt1(BigInteger.valueOf(pair.getValue1())))
                .sorted(Comparator.comparing(Pair::getValue0))
                .collect(Collectors.toList());
    }

    private static BigInteger calculateTimestamp(List<Pair<BigInteger, BigInteger>> constraints) {
        BigInteger currentTimestamp = BigInteger.ONE;
        BigInteger stepSize = BigInteger.ONE;
        for (Pair<BigInteger, BigInteger> constraint : constraints) {
            while (!fitsConstraint(currentTimestamp, constraint)) {
                currentTimestamp = currentTimestamp.add(stepSize);
            }
            stepSize = lcm(stepSize, constraint.getValue0());
        }
        return currentTimestamp;
    }

    private static boolean fitsConstraint(BigInteger timestamp, Pair<BigInteger, BigInteger> constraint) {
        return timestamp.add(constraint.getValue1()).mod(constraint.getValue0()).compareTo(BigInteger.ZERO) == 0;
    }

    // not necessary with this input, since these are all prime numbers, left here for completeness sake
    private static BigInteger lcm(BigInteger number1, BigInteger number2) {
        BigInteger gcd = number1.gcd(number2);
        BigInteger absProduct = number1.multiply(number2).abs();
        return absProduct.divide(gcd);
    }
}
