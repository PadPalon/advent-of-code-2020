package ch.neukom.day14;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Command {
    private static final Pattern MASK_PATTERN = Pattern.compile("mask = ([01X]+)");
    private static final Pattern MEMORY_PATTERN = Pattern.compile("mem\\[([0-9]+)] = ([0-9]+)");

    private final Bitmask bitmask;
    private final Integer address;
    private final Integer value;

    public Command(String mask) {
        this.bitmask = new Bitmask(mask);
        this.address = null;
        this.value = null;
    }

    public Command(String address, String value) {
        this.bitmask = null;
        this.address = Integer.parseInt(address);
        this.value = Integer.parseInt(value);
    }

    public static Command parse(String line) {
        if (line.startsWith("mask")) {
            Matcher matcher = MASK_PATTERN.matcher(line);
            if (matcher.find()) {
                return new Command(matcher.group(1));
            }
        } else {
            Matcher matcher = MEMORY_PATTERN.matcher(line);
            if (matcher.find()) {
                return new Command(matcher.group(1), matcher.group(2));
            }
        }
        throw new IllegalArgumentException("Could not parse line: %s".formatted(line));
    }

    public boolean isBitmask() {
        return bitmask != null;
    }

    public Bitmask getBitmask() {
        return bitmask;
    }

    public Integer getAddress() {
        return address;
    }

    public Integer getValue() {
        return value;
    }
}
