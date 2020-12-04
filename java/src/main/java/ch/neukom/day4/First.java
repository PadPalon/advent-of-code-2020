package ch.neukom.day4;

import java.util.List;
import java.util.stream.Collectors;

import ch.neukom.helper.InputResourceReader;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

public class First {

    public static final Splitter PART_SPLITTER = Splitter.on("\n\n");
    public static final Splitter VALUE_SPLITTER = Splitter.on(CharMatcher.whitespace()).trimResults().omitEmptyStrings();

    public static final List<String> REQUIRED_KEYS = List.of(
        "byr",
        "iyr",
        "eyr",
        "hgt",
        "hcl",
        "ecl",
        "pid"
    );

    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(First.class)) {
            String input = reader.readDefaultInput()
                .collect(Collectors.joining("\n"));
            long validPassports = PART_SPLITTER.splitToStream(input)
                .map(VALUE_SPLITTER::splitToStream)
                .map(parts -> parts.map(value -> value.split(":")).filter(split -> split.length == 2))
                .map(part -> part.collect(Collectors.toMap(split -> split[0], split -> split[1])))
                .filter(passport -> passport.keySet().containsAll(REQUIRED_KEYS))
                .count();
            System.out.printf("There are %d valid passports %n", validPassports);
        }
    }
}
