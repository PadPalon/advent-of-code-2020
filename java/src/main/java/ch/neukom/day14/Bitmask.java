package ch.neukom.day14;

public class Bitmask {
    private final String bitmask;
    private final long oneMask;
    private final long zeroMask;

    public Bitmask(String bitmask) {
        this.bitmask = bitmask;
        this.oneMask = Long.parseLong(bitmask.replace("X", "0"), 2);
        this.zeroMask = Long.parseLong(bitmask.replace("X", "1"), 2);
    }

    public long apply(int value) {
        return value & zeroMask | oneMask;
    }
}
