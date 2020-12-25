package ch.neukom.day18;

public class Calculation implements Expression {
    private final Expression left;
    private final Expression right;
    private final Operator operator;

    public Calculation(Expression left, Expression right, Operator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public long evaluate() {
        return operator.operate(left.evaluate(), right.evaluate());
    }
}
