package ch.neukom.day12;

public interface Ship {
    void executeMove(Character moveOrder, Integer value);

    int calculateDistanceFromStart();
}
