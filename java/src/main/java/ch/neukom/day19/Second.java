package ch.neukom.day19;

import ch.neukom.helper.InputResourceReader;
import com.google.common.base.Splitter;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * this is unbelievably hacky, but I couldn't be arsed to implement a completely new system so I just forced the regex
 * system from part 1 to work. I just repeatedly apply the regex with increasing counts of rule 11 until it doesn't
 * find any new matches. Would not hack together again.
 */
public class Second {
    private static final Pattern SINGLE_CHARACTER_PATTERN = Pattern.compile("([0-9]+): \"([a-z]+)\"");
    private static final Pattern RELATION_PATTERN = Pattern.compile("([0-9]+): ([0-9 |]+)");

    private static final Splitter SPACE_SPLITTER = Splitter.on(' ').omitEmptyStrings().trimResults();

    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(Second.class)) {
            String[] split = reader.readInput("second_input")
                    .collect(Collectors.joining("\n"))
                    .split("\n\n");

            String rulesString = split[0];
            String messagesString = split[1];

            Map<Integer, String> rules = resolveRules(rulesString);

            long matches = 0;
            long newMatches;
            int i = 1;
            do {
                String count = String.valueOf(i);
                Map<Integer, Pattern> rulePatterns = rules.entrySet()
                        .stream()
                        .map(entry -> Pair.with(entry.getKey(), Pattern.compile(entry.getValue().replace("REPLACE", count))))
                        .collect(Collectors.toMap(Pair::getValue0, Pair::getValue1));

                Pattern pattern = rulePatterns.get(0);
                newMatches = Arrays.stream(messagesString.split("\n"))
                        .map(pattern::matcher)
                        .filter(Matcher::matches)
                        .count();
                matches += newMatches;
                i++;
            } while (newMatches > 0);

            System.out.printf("%d messages match rule 0%n", matches);
        }
    }

    private static Map<Integer, String> resolveRules(String rulesString) {
        Map<Integer, String> rules = Arrays.stream(rulesString.split("\n"))
                .map(SINGLE_CHARACTER_PATTERN::matcher)
                .filter(Matcher::find)
                .collect(Collectors.toMap(matcher -> Integer.parseInt(matcher.group(1)), matcher -> matcher.group(2)));

        Map<Integer, String> unresolvedRules = Arrays.stream(rulesString.split("\n"))
                .map(RELATION_PATTERN::matcher)
                .filter(Matcher::find)
                .collect(Collectors.toMap(matcher -> Integer.parseInt(matcher.group(1)), matcher -> matcher.group(2)));

        while (!unresolvedRules.isEmpty()) {
            List<Integer> newlyResolvedRules = new ArrayList<>();
            for (Map.Entry<Integer, String> unresolvedRule : unresolvedRules.entrySet()) {
                String rule = unresolvedRule.getValue();
                String resolvedRule;
                if (unresolvedRule.getKey() == 8) {
                    resolvedRule = "(%s)+".formatted(rules.get(42));
                } else if (unresolvedRule.getKey() == 11) {
                    resolvedRule = "(%s){REPLACE}(%s){REPLACE}".formatted(rules.get(42), rules.get(31));
                } else {
                    resolvedRule = SPACE_SPLITTER.splitToStream(rule)
                            .map(part -> (part.equals("|")) ? "|" : "(%s)".formatted(rules.get(Integer.parseInt(part))))
                            .collect(Collectors.joining());
                }
                if (!resolvedRule.contains("null")) {
                    rules.put(unresolvedRule.getKey(), resolvedRule);
                    newlyResolvedRules.add(unresolvedRule.getKey());
                }
            }
            newlyResolvedRules.forEach(unresolvedRules::remove);
        }

        return rules;
    }
}
