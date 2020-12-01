package ch.neukom.day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class First {
    public static void main(String[] args) throws IOException {
        try (InputStream is = First.class.getResourceAsStream("input");
             InputStreamReader isr = new InputStreamReader(is);
             BufferedReader reader = new BufferedReader(isr)) {
            int[] numbers = reader.lines()
                .mapToInt(Integer::parseInt)
                .sorted()
                .toArray();
            int solution = findSolution(numbers);
            System.out.printf("Solution is: %d%n", solution);
        }
    }

    private static int findSolution(int[] numbers) {
        for (int number : numbers) {
            for (int i = numbers.length - 1; i >= 0; i--) {
                int nextNumber = numbers[i];
                int sum = number + nextNumber;
                if (sum == 2020) {
                    return number * nextNumber;
                } else if (sum < 2020) {
                    break;
                }
            }
        }
        throw new IllegalStateException("Could not find solution");
    }
}
