package ch.neukom.day18;

import ch.neukom.helper.InputResourceReader;

public class Second {
    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(Second.class)) {
            long sumOfResults = reader.readDefaultInput()
                    .map(StackParser::new)
                    .map(StackParser::run)
                    .mapToLong(Expression::evaluate)
                    .sum();
            System.out.printf("The sum of all the results is %d%n", sumOfResults);
        }
    }
}
