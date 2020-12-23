package ch.neukom.day16;

import ch.neukom.helper.InputResourceReader;
import com.google.common.collect.*;
import org.javatuples.Pair;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static ch.neukom.day16.Helper.parseCombinedRules;
import static ch.neukom.day16.Helper.parseTickets;

public class Second {
    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(Second.class)) {
            String[] parts = reader.readDefaultInput()
                    .collect(Collectors.joining("\n"))
                    .split("\n\n");

            List<Rule> rules = parseCombinedRules(parts[0]);
            List<Ticket> tickets = parseTickets(parts[2]);

            Predicate<Integer> combinedPredicate = rules.stream()
                    .map(Rule::getRange)
                    .reduce(i -> false, Predicate::or, Predicate::or);
            Multimap<Long, Integer> fieldValues = getValidFieldValues(tickets, combinedPredicate);
            Map<Integer, Rule> rulesForFields = solveRulesForFields(rules, fieldValues);

            Ticket ownTicket = parseOwnTicket(parts[1]);
            List<Integer> ticketValues = ownTicket.getValues();
            List<Integer> departureFields = findDepartureFields(rulesForFields);
            long solution = departureFields.stream()
                    .mapToInt(ticketValues::get)
                    .mapToLong(Long::valueOf)
                    .reduce(Math::multiplyExact)
                    .orElseThrow();

            System.out.printf("The solution is %d%n", solution);
        }
    }

    private static Multimap<Long, Integer> getValidFieldValues(List<Ticket> tickets, Predicate<Integer> predicate) {
        return tickets.stream()
                .map(Ticket::getValues)
                .filter(values -> values.stream().allMatch(predicate))
                .flatMap(values -> Streams.mapWithIndex(values.stream(), Pair::with))
                .collect(Multimaps.toMultimap(Pair::getValue1, Pair::getValue0, ArrayListMultimap::create));
    }

    private static Map<Integer, Rule> solveRulesForFields(List<Rule> rules, Multimap<Long, Integer> fieldValues) {
        Map<Integer, Rule> finalRules = new HashMap<>();
        int ruleCount = rules.size();
        while (finalRules.size() != ruleCount) {
            LongStream.range(0, ruleCount)
                    .mapToObj(i -> Pair.with(i, fieldValues.get(i)))
                    .map(pair -> pair.setAt1(getMatchingRules(rules, finalRules, pair.getValue1())))
                    .filter(pair -> pair.getValue1().size() == 1)
                    .forEach(pair -> finalRules.put(pair.getValue0().intValue(), Iterables.getLast(pair.getValue1())));
        }
        return finalRules;
    }

    private static Set<Rule> getMatchingRules(List<Rule> rules,
                                              Map<Integer, Rule> finalRules,
                                              Collection<Integer> values) {
        return rules.stream()
                .filter(rule -> !finalRules.containsValue(rule))
                .filter(rule -> rule.isValidForValues(values))
                .collect(Collectors.toSet());
    }

    private static Ticket parseOwnTicket(String ticketLine) {
        return new Ticket(ticketLine.split("\n")[1]);
    }

    private static List<Integer> findDepartureFields(Map<Integer, Rule> rulesForFields) {
        return rulesForFields.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getName().startsWith("departure"))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
