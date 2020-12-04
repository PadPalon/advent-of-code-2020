package ch.neukom.day4;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import ch.neukom.helper.InputResourceReader;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

public class Second {

    public static final Splitter PART_SPLITTER = Splitter.on("\n\n");
    public static final Splitter VALUE_SPLITTER = Splitter.on(CharMatcher.whitespace()).trimResults().omitEmptyStrings();

    public static final List<String> REQUIRED_KEYS = List.of(
        "byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"
    );

    public static final Pattern HEIGHT_PATTERN = Pattern.compile("^([0-9]+)(cm|in)$");
    public static final Pattern HAIR_COLOR_PATTERN = Pattern.compile("^#[0-9a-f]{6}$");
    public static final Pattern PID_PATTERN = Pattern.compile("^[0-9]{9}$");

    public static final Set<String> ALLOWED_EYE_COLORS = Set.of(
        "amb", "blu", "brn", "gry", "grn", "hzl", "oth"
    );

    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(Second.class)) {
            String input = reader.readDefaultInput()
                .collect(Collectors.joining("\n"));
            long validPassports = PART_SPLITTER.splitToStream(input)
                .map(VALUE_SPLITTER::splitToStream)
                .map(parts -> parts.map(value -> value.split(":")).filter(split -> split.length == 2))
                .map(part -> part.collect(Collectors.toMap(split -> split[0], split -> split[1])))
                .filter(passport -> passport.keySet().containsAll(REQUIRED_KEYS))
                .filter(Second::validateByr)
                .filter(Second::validateIyr)
                .filter(Second::validateEyr)
                .filter(Second::validateHgt)
                .filter(Second::validateHcl)
                .filter(Second::validateEcl)
                .filter(Second::validatePid)
                .count();
            System.out.printf("The solution is %d%n", validPassports);
        }
    }

    private static boolean validateByr(Map<String, String> passport) {
        return parseIntFromPassport(passport, "byr")
            .map(birthYear -> birthYear >= 1920 && birthYear <= 2002)
            .orElse(false);
    }

    private static boolean validateIyr(Map<String, String> passport) {
        return parseIntFromPassport(passport, "iyr")
            .map(issueYear -> issueYear >= 2010 && issueYear <= 2020)
            .orElse(false);
    }

    private static boolean validateEyr(Map<String, String> passport) {
        return parseIntFromPassport(passport, "eyr")
            .map(expirationYear -> expirationYear >= 2020 && expirationYear <= 2030)
            .orElse(false);
    }

    private static boolean validateHgt(Map<String, String> passport) {
        String heightString = passport.get("hgt");
        Matcher matcher = HEIGHT_PATTERN.matcher(heightString);
        if (matcher.find()) {
            int height = Integer.parseInt(matcher.group(1));
            if (matcher.group(2).equals("cm")) {
                return height >= 150 && height <= 193;
            } else {
                return height >= 59 && height <= 76;
            }
        } else {
            return false;
        }
    }

    private static boolean validateHcl(Map<String, String> passport) {
        String hairColorString = passport.get("hcl");
        Matcher matcher = HAIR_COLOR_PATTERN.matcher(hairColorString);
        return matcher.find();
    }

    private static boolean validateEcl(Map<String, String> passport) {
        return ALLOWED_EYE_COLORS.contains(passport.get("ecl"));
    }

    private static boolean validatePid(Map<String, String> passport) {
        String pidString = passport.get("pid");
        Matcher matcher = PID_PATTERN.matcher(pidString);
        return matcher.matches();
    }

    private static Optional<Integer> parseIntFromPassport(Map<String, String> passport, String key) {
        try {
            return Optional.of(Integer.parseInt(passport.get(key)));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
