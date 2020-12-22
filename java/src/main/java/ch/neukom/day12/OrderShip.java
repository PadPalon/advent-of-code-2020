package ch.neukom.day12;

public class OrderShip implements Ship {
    private Direction direction = Direction.EAST;
    private int x = 0;
    private int y = 0;

    @Override
    public void executeMove(Character moveOrder, Integer value) {
        if (moveOrder == 'F') {
            move(value);
        } else if (moveOrder == 'L') {
            turnLeft(value);
        } else if (moveOrder == 'R') {
            turnRight(value);
        } else {
            move(Direction.getDirection(moveOrder), value);
        }
    }

    public void move(int value) {
        move(direction, value);
    }

    public void move(Direction direction, int value) {
        switch (direction) {
            case NORTH -> y += value;
            case EAST -> x += value;
            case SOUTH -> y -= value;
            case WEST -> x -= value;
            default -> throw new IllegalStateException("Unknown direction: %s".formatted(direction));
        }
    }

    public void turnLeft(Integer value) {
        direction = direction.turnLeft(value);
    }

    public void turnRight(Integer value) {
        direction = direction.turnRight(value);
    }

    @Override
    public int calculateDistanceFromStart() {
        return Math.abs(x) + Math.abs(y);
    }
}
