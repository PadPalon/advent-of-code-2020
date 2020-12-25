package ch.neukom.day18;

import ch.neukom.helper.InputResourceReader;

public class First {
    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(First.class)) {
            long sumOfResults = reader.readDefaultInput()
                    .map(Parser::new)
                    .map(Parser::run)
                    .mapToLong(Expression::evaluate)
                    .sum();
            System.out.printf("The sum of all the results is %d%n", sumOfResults);
        }
    }
}
