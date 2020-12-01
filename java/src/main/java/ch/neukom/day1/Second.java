package ch.neukom.day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Second {
    public static void main(String[] args) throws IOException {
        try (InputStream is = Second.class.getResourceAsStream("input");
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
        for (int i = 0; i < numbers.length; i++) {
            int firstNumber = numbers[i];
            for (int j = i + 1; j < numbers.length; j++) {
                int secondNumber = numbers[j];
                for (int k = j + 1; k < numbers.length; k++) {
                    int thirdNumber = numbers[k];
                    int sum = firstNumber + secondNumber + thirdNumber;
                    if (sum == 2020) {
                        return firstNumber * secondNumber * thirdNumber;
                    } else if (sum > 2020) {
                        break;
                    }
                }
            }
        }
        throw new IllegalStateException("Could not find solution");
    }
}
