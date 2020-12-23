package ch.neukom.day14;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

public class AddressBitmask implements Bitmask {
    private final String bitmask;
    private final List<Long> zeroMasks;
    private final List<Long> oneMasks;
    private final long mask;
    private final int maskCount;

    public AddressBitmask(String bitmask, List<Long> zeroMasks, List<Long> oneMasks, int maskCount) {
        this.bitmask = bitmask;
        this.mask = Long.parseLong(bitmask.replace("X", "0"), 2);
        this.zeroMasks = zeroMasks;
        this.oneMasks = oneMasks;
        this.maskCount = maskCount;
    }

    public static AddressBitmask buildAddressBitmask(String bitmask) {
        List<Integer> floatingIndexes = findFloatingBits(bitmask);

        List<Long> zeroMasks = new ArrayList<>();
        List<Long> oneMasks = new ArrayList<>();
        for (Integer floatingIndex : floatingIndexes) {
            String zeroMask = new StringBuilder(Strings.repeat("1", bitmask.length()))
                    .replace(floatingIndex, floatingIndex + 1, "0")
                    .toString();
            zeroMasks.add(Long.parseLong(zeroMask, 2));
            String oneMask = new StringBuilder(Strings.repeat("0", bitmask.length()))
                    .replace(floatingIndex, floatingIndex + 1, "1")
                    .toString();
            oneMasks.add(Long.parseLong(oneMask, 2));
        }

        int maskCount = calcAndValidateMaskCount(bitmask, zeroMasks, oneMasks);

        return new AddressBitmask(bitmask, zeroMasks, oneMasks, maskCount);
    }

    private static List<Integer> findFloatingBits(String bitmask) {
        List<Integer> floatingIndexes = new ArrayList<>();
        int currentIndex = bitmask.indexOf("X");
        while (currentIndex >= 0) {
            floatingIndexes.add(currentIndex);
            currentIndex = bitmask.indexOf("X", currentIndex + 1);
        }
        return floatingIndexes;
    }

    private static int calcAndValidateMaskCount(String bitmask, List<Long> zeroMasks, List<Long> oneMasks) {
        assert zeroMasks.size() == oneMasks.size();
        int maskCount = zeroMasks.size();
        if (maskCount != bitmask.chars().filter(c -> c == 'X').count()) {
            throw new IllegalStateException("We fucked up");
        }
        return maskCount;
    }

    @Override
    public List<Long> apply(Long value) {
        List<Long> values = new ArrayList<>();
        values.add(value | mask);
        for (int i = 0; i < maskCount; i++) {
            List<Long> nextValues = new ArrayList<>();
            for (Long source : values) {
                nextValues.add(oneMasks.get(i) | source);
                nextValues.add(zeroMasks.get(i) & source);
            }
            values = nextValues;
        }
        return values;
    }
}
