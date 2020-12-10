package ch.neukom.day10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.neukom.helper.InputResourceReader;

import static java.util.stream.Collectors.*;

public class Second {
    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(Second.class)) {
            int[] adapters = reader.readDefaultInput()
                .mapToInt(Integer::parseInt)
                .sorted()
                .toArray();
            int[] chain = buildChain(adapters);
            long variationCount = countVariations(chain);
            System.out.printf("There exist %d different combinations of adapters%n", variationCount);
        }
    }

    private static int[] buildChain(int[] adapters) {
        int totalLength = adapters.length + 2;
        int[] chain = new int[totalLength];
        chain[0] = 0;
        System.arraycopy(adapters, 0, chain, 1, adapters.length);
        chain[totalLength - 1] = adapters[adapters.length - 1] + 3;
        return chain;
    }

    private static long countVariations(int[] adapterChain) {
        List<int[]> subsequences = splitIntoSubsequences(adapterChain);
        return subsequences.stream()
            .map(Second::generateVariations)
            .map(Set::size)
            .mapToLong(i -> i)
            .reduce((left, right) -> left * right)
            .orElseThrow();
    }

    private static List<int[]> splitIntoSubsequences(int[] adapterChain) {
        List<int[]> subsequences = new ArrayList<>();
        int lastSubsequenceIndex = 0;
        for (int i = 1; i < adapterChain.length; i++) {
            int left = adapterChain[i - 1];
            int right = adapterChain[i];

            if (right - left == 3) {
                int[] subsequence = createSubsequence(adapterChain, lastSubsequenceIndex, i);
                subsequences.add(subsequence);
                lastSubsequenceIndex = i;
            }
        }
        return subsequences;
    }

    private static int[] createSubsequence(int[] adapterChain, int lastSubsequenceIndex, int i) {
        int subsequenceLength = calculateSubsequenceLength(adapterChain, lastSubsequenceIndex, i);
        int[] subsequence = new int[subsequenceLength];
        System.arraycopy(adapterChain, lastSubsequenceIndex, subsequence, 0, subsequenceLength);
        return subsequence;
    }

    private static int calculateSubsequenceLength(int[] adapterChain, int lastSubsequenceIndex, int i) {
        if (i == adapterChain.length - 1) {
            return i - lastSubsequenceIndex + 1;
        } else {
            return i - lastSubsequenceIndex;
        }
    }

    private static Set<String> generateVariations(int[] adapterChain) {
        if (adapterChain.length <= 2) {
            return Set.of(stringifySequence(adapterChain));
        }

        Set<String> variations = new HashSet<>();
        variations.add(stringifySequence(adapterChain));
        for (int i = 1; i < adapterChain.length - 1; i++) {
            int left = adapterChain[i - 1];
            int right = adapterChain[i + 1];

            if (right - left <= 3) {
                int[] variation = createVariation(adapterChain, i);
                variations.add(stringifySequence(variation));
                variations.addAll(generateVariations(variation));
            }
        }
        return variations;
    }

    private static int[] createVariation(int[] adapterChain, int i) {
        int[] variation = new int[adapterChain.length - 1];
        System.arraycopy(adapterChain, 0, variation, 0, i + 1);
        System.arraycopy(adapterChain, i + 1, variation, i, adapterChain.length - i - 1);
        return variation;
    }

    private static String stringifySequence(int[] sequence) {
        return Arrays.stream(sequence).mapToObj(String::valueOf).collect(joining("-"));
    }
}
