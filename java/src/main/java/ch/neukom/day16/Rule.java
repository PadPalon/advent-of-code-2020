package ch.neukom.day16;

import com.google.common.collect.Range;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Rule {
    private static final Pattern NAME_PATTERN = Pattern.compile("([a-z ]+): ");
    private static final Pattern RANGE_PATTERN = Pattern.compile("([0-9]+-[0-9]+)");

    private final String name;
    private final Predicate<Integer> range;

    private Rule(String name, int lower, int upper) {
        this.name = name;
        this.range = Range.closed(lower, upper);
    }

    private Rule(String name, Predicate<Integer> predicate) {
        this.name = name;
        this.range = predicate;
    }

    public static List<Rule> parseRules(String ruleString) {
        Matcher nameMatcher = NAME_PATTERN.matcher(ruleString);
        if (nameMatcher.find()) {
            String name = nameMatcher.group(1);
            return findRanges(ruleString)
                    .map(range -> parseRange(name, range))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    private static Stream<String> findRanges(String ruleString) {
        Matcher rangeMatcher = RANGE_PATTERN.matcher(ruleString);
        Stream.Builder<String> ruleStreamBuilder = Stream.builder();
        while (rangeMatcher.find()) {
            ruleStreamBuilder.accept(rangeMatcher.group(1));
        }
        return ruleStreamBuilder.build();
    }

    private static Rule parseRange(String name, String range) {
        String[] split = range.split("-");
        if (split.length != 2) {
            throw new IllegalArgumentException("Range not given in the format <lower>-<upper>");
        }
        return new Rule(name, Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    public String getName() {
        return name;
    }

    public Predicate<Integer> getRange() {
        return range;
    }

    public Rule combine(Rule other) {
        if (getName().equals(other.getName())) {
            return new Rule(getName(), getRange().or(other.getRange()));
        } else {
            throw new IllegalStateException("Trying to combine rules that do not share a name");
        }
    }

    public boolean isValidForValues(Collection<Integer> values) {
        return values.stream().allMatch(getRange());
    }
}
