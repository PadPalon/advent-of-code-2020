package ch.neukom.day16;

import ch.neukom.helper.InputResourceReader;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ch.neukom.day16.Helper.parseSeparateRules;
import static ch.neukom.day16.Helper.parseTickets;

public class First {
    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(First.class)) {
            String[] parts = reader.readDefaultInput()
                    .collect(Collectors.joining("\n"))
                    .split("\n\n");

            List<Rule> rules = parseSeparateRules(parts[0]);
            List<Ticket> tickets = parseTickets(parts[2]);

            Predicate<Integer> combinedPredicate = rules.stream()
                    .map(Rule::getRange)
                    .reduce((Predicate<Integer>) i -> false, Predicate::or, Predicate::or)
                    .negate();
            int errorRate = tickets.stream()
                    .map(Ticket::getValues)
                    .flatMap(Collection::stream)
                    .filter(combinedPredicate)
                    .mapToInt(i -> i)
                    .sum();

            System.out.printf("The ticket scanning error rate is %d%n", errorRate);
        }
    }
}
