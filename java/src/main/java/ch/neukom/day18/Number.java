package ch.neukom.day18;

public class Number implements Expression {
    private final long value;

    public Number(long value) {
        this.value = value;
    }

    @Override
    public long evaluate() {
        return value;
    }
}
