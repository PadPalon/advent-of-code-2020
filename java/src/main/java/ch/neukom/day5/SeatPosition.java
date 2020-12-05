package ch.neukom.day5;

import java.util.stream.Stream;

class SeatPosition {
    private final int maxRow;
    private final int minRow;
    private final int maxCol;
    private final int minCol;

    private SeatPosition() {
        this.maxRow = 127;
        this.minRow = 0;
        this.maxCol = 7;
        this.minCol = 0;
    }

    public SeatPosition(int maxRow, int minRow, int maxCol, int minCol) {
        this.maxRow = maxRow;
        this.minRow = minRow;
        this.maxCol = maxCol;
        this.minCol = minCol;
    }

    public static SeatPosition getSeatPosition(Stream<PositionMove> moves) {
        return moves.reduce(new SeatPosition(), SeatPosition::move, (pos1, pos2) -> {
            throw new IllegalStateException("Can't combine seat positions");
        });
    }

    private SeatPosition move(PositionMove positionMove) {
        final int rowDistance = (maxRow - minRow) / 2;
        final int colDistance = (maxCol - minCol) / 2;
        switch (positionMove) {
            case F -> {
                return new SeatPosition(minRow + rowDistance, minRow, maxCol, minCol);
            }
            case B -> {
                return new SeatPosition(maxRow, maxRow - rowDistance, maxCol, minCol);
            }
            case L -> {
                return new SeatPosition(maxRow, minRow, minCol + colDistance, minCol);
            }
            case R -> {
                return new SeatPosition(maxRow, minRow, maxCol, maxCol - colDistance);
            }
            default -> throw new IllegalArgumentException("Do not extend PositionMove enum without adjusting this switch");
        }
    }

    public int getSeatId() {
        return minRow * 8 + minCol;
    }

    enum PositionMove {
        F,
        B,
        L,
        R
    }
}
