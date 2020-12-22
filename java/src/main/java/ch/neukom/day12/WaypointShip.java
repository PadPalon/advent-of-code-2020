package ch.neukom.day12;

public class WaypointShip implements Ship {
    private int x = 0;
    private int y = 0;
    private int waypointX = 10;
    private int waypointY = 1;

    @Override
    public void executeMove(Character moveOrder, Integer value) {
        if (moveOrder == 'F') {
            move(value);
        } else if (moveOrder == 'L') {
            turnWaypointLeft(value);
        } else if (moveOrder == 'R') {
            turnWaypointRight(value);
        } else {
            moveWaypoint(Direction.getDirection(moveOrder), value);
        }
    }

    public void move(int value) {
        x += value * waypointX;
        y += value * waypointY;
    }

    public void moveWaypoint(Direction direction, int value) {
        switch (direction) {
            case NORTH -> waypointY += value;
            case EAST -> waypointX += value;
            case SOUTH -> waypointY -= value;
            case WEST -> waypointX -= value;
            default -> throw new IllegalStateException("Unknown direction: %s".formatted(direction));
        }
    }

    public void turnWaypointLeft(Integer value) {
        turn(4 - value / 90 % 4);
    }

    public void turnWaypointRight(Integer value) {
        turn(value / 90);
    }

    public void turn(int moves) {
        for (int i = 0; i < moves; i++) {
            int currentX = waypointX;
            int currentY = waypointY;
            waypointY = -currentX;
            waypointX = currentY;
        }
    }

    @Override
    public int calculateDistanceFromStart() {
        return Math.abs(x) + Math.abs(y);
    }
}
