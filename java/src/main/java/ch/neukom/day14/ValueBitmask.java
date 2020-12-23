package ch.neukom.day14;

import java.util.List;

public class ValueBitmask implements Bitmask {
    private final String bitmask;
    private final long oneMask;
    private final long zeroMask;

    public ValueBitmask(String bitmask) {
        this.bitmask = bitmask;
        this.oneMask = Long.parseLong(bitmask.replace("X", "0"), 2);
        this.zeroMask = Long.parseLong(bitmask.replace("X", "1"), 2);
    }

    @Override
    public List<Long> apply(Long value) {
        return List.of(value & zeroMask | oneMask);
    }
}
