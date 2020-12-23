package ch.neukom.day16;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Helper {
    private Helper() {
    }

    public static List<Rule> parseSeparateRules(String ruleStrings) {
        return Arrays.stream(ruleStrings.split("\n"))
                .map(Rule::parseRules)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public static List<Rule> parseCombinedRules(String ruleStrings) {
        return Arrays.stream(ruleStrings.split("\n"))
                .map(Rule::parseRules)
                .map(rules -> rules.stream().reduce(Rule::combine).orElseThrow())
                .collect(Collectors.toList());
    }

    public static List<Ticket> parseTickets(String ticketStrings) {
        return Arrays.stream(ticketStrings.split("\n"))
                .skip(1)
                .map(Ticket::new)
                .collect(Collectors.toList());
    }
}
