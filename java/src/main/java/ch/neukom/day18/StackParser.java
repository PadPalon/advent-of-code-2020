package ch.neukom.day18;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ch.neukom.day18.SubexpressionHelper.getSubexpression;

public class StackParser {
    private static final Pattern NUMBER_PATTERN = Pattern.compile("([0-9]+)");
    private static final Pattern OPERATOR_MATCHER = Pattern.compile("([+*])");

    private final String line;

    private int currentIndex = 0;

    public StackParser(String line) {
        this.line = line.replace(" ", "");
    }

    public Expression run() {
        List<Symbol> stack = new ArrayList<>();
        Matcher numberMatcher = NUMBER_PATTERN.matcher(line);
        Matcher operatorMatcher = OPERATOR_MATCHER.matcher(line);
        while (currentIndex < line.length()) {
            if (line.charAt(currentIndex) == '(') {
                String subexpression = getSubexpression(line, currentIndex + 1);
                stack.add(new StackParser(subexpression).run());
                currentIndex += subexpression.length() + 2;
            } else if (numberMatcher.find(currentIndex) && numberMatcher.start() == currentIndex) {
                String number = numberMatcher.group(1);
                stack.add(new Number(Long.parseLong(number)));
                currentIndex += number.length();
            } else if (operatorMatcher.find(currentIndex) && operatorMatcher.start() == currentIndex) {
                stack.add(Operator.findOperatorBySymbol(operatorMatcher.group(1).charAt(0)));
                currentIndex++;
            } else {
                currentIndex++;
            }
        }

        buildCalculations(stack, Operator.PLUS);
        buildCalculations(stack, Operator.MULT);

        assert stack.size() == 1;

        return (Expression) stack.get(0);
    }

    private void buildCalculations(List<Symbol> stack, Operator operator) {
        int operatorIndex = stack.indexOf(operator);
        while (operatorIndex > 0) {
            Expression right = (Expression) stack.remove(operatorIndex + 1);
            stack.remove(operatorIndex);
            Expression left = (Expression) stack.remove(operatorIndex - 1);

            stack.add(operatorIndex - 1, new Calculation(left, right, operator));
            operatorIndex = stack.indexOf(operator);
        }
    }
}
