package ch.neukom.day2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

class PasswordLine {
    private static final Pattern LINE_PATTERN = Pattern.compile("([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)");

    private final int firstNumber;
    private final int secondNumber;
    private final char character;
    private final String password;

    public PasswordLine(int firstNumber, int secondNumber, char character, String password) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.character = character;
        this.password = password;
    }

    @Nullable
    public static PasswordLine parse(String line) {
        Matcher matcher = LINE_PATTERN.matcher(line);
        if (matcher.find()) {
            int firstNumber = Integer.parseInt(matcher.group(1));
            int secondNumber = Integer.parseInt(matcher.group(2));
            char character = matcher.group(3).charAt(0);
            return new PasswordLine(firstNumber, secondNumber, character, matcher.group(4));
        } else {
            return null;
        }
    }

    public boolean isValidForOldPolicy() {
        long count = password.chars()
            .filter(c -> c == character)
            .count();
        return count >= firstNumber && count <= secondNumber;
    }

    public boolean isValidForNewPolicy() {
        boolean firstPositionMatches = password.charAt(firstNumber - 1) == character;
        boolean secondPositionMatches = password.charAt(secondNumber - 1) == character;
        return firstPositionMatches ^ secondPositionMatches;
    }
}
