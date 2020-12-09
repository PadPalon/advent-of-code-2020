package ch.neukom.day7;

import java.util.Optional;

import ch.neukom.helper.InputResourceReader;
import com.google.common.collect.Multimap;

import static ch.neukom.day7.Helper.*;

public class Second {
    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(Second.class)) {
            Multimap<String, ContentRule> rules = getBagRules(reader);
            int solution = getContainingBagCount(rules, "shiny gold");
            System.out.printf("The shiny gold bag contains %d other bags%n", solution);
        }
    }

    private static int getContainingBagCount(Multimap<String, ContentRule> rules, ContentRule rule) {
        return Optional.ofNullable(rule.getTargetType())
            .filter(rules::containsKey)
            .map(type -> getContainingBagCount(rules, type) + 1) // add 1 since we need to count the container bag as well
            .orElse(0);
    }

    private static int getContainingBagCount(Multimap<String, ContentRule> rules, String targetType) {
        return rules.get(targetType).stream().mapToInt(r -> r.getCount() * getContainingBagCount(rules, r)).sum();
    }
}
