package ch.neukom.day14;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Command {
    private static final Pattern MASK_PATTERN = Pattern.compile("mask = ([01X]+)");
    private static final Pattern MEMORY_PATTERN = Pattern.compile("mem\\[([0-9]+)] = ([0-9]+)");

    private final Bitmask bitmask;
    private final Long address;
    private final Long value;

    public Command(Bitmask bitmask) {
        this.bitmask = bitmask;
        this.address = null;
        this.value = null;
    }

    public Command(String address, String value) {
        this.bitmask = null;
        this.address = Long.parseLong(address);
        this.value = Long.parseLong(value);
    }

    public static Command parseWithValueMask(String line) {
        return parse(line, ValueBitmask::new);
    }

    public static Command parseWithAddressMask(String line) {
        return parse(line, AddressBitmask::buildAddressBitmask);
    }

    private static Command parse(String line, Function<String, Bitmask> bitmaskCreator) {
        if (line.startsWith("mask")) {
            Matcher matcher = MASK_PATTERN.matcher(line);
            if (matcher.find()) {
                return new Command(bitmaskCreator.apply(matcher.group(1)));
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

    public Long getAddress() {
        return address;
    }

    public Long getValue() {
        return value;
    }
}
