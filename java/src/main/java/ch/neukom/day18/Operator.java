package ch.neukom.day18;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public enum Operator implements Symbol {
    PLUS(Math::addExact, '+'),
    MULT(Math::multiplyExact, '*');

    private final BiFunction<Long, Long, Long> operation;
    private final char symbol;

    Operator(BinaryOperator<Long> operation, char symbol) {
        this.operation = operation;
        this.symbol = symbol;
    }

    public static Operator findOperatorBySymbol(char symbol) {
        return Arrays.stream(Operator.values()).filter(op -> op.symbol == symbol).findAny().orElseThrow();
    }

    public long operate(long left, long right) {
        return operation.apply(left, right);
    }
}
