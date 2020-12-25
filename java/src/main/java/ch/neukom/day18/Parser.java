package ch.neukom.day18;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ch.neukom.day18.SubexpressionHelper.getSubexpression;

public class Parser {
    private static final Pattern NUMBER_PATTERN = Pattern.compile("([0-9]+)");
    private static final Pattern OPERATOR_MATCHER = Pattern.compile("([+*])");

    private final String line;

    private int currentIndex = 0;

    public Parser(String line) {
        this.line = line.replace(" ", "");
    }

    public Expression run() {
        Expression lastExpression = null;
        Operator lastOperator = null;
        Matcher numberMatcher = NUMBER_PATTERN.matcher(line);
        Matcher operatorMatcher = OPERATOR_MATCHER.matcher(line);
        while (currentIndex < line.length()) {
            Expression nextExpression = null;
            if (line.charAt(currentIndex) == '(') {
                String subexpression = getSubexpression(line, currentIndex + 1);
                nextExpression = new Parser(subexpression).run();
                currentIndex += subexpression.length() + 2;
            } else if (numberMatcher.find(currentIndex) && numberMatcher.start() == currentIndex) {
                String number = numberMatcher.group(1);
                nextExpression = new Number(Long.parseLong(number));
                currentIndex += number.length();
            } else if (operatorMatcher.find(currentIndex) && operatorMatcher.start() == currentIndex) {
                lastOperator = Operator.findOperatorBySymbol(operatorMatcher.group(1).charAt(0));
                currentIndex++;
            } else {
                currentIndex++;
            }
            if (lastExpression == null && nextExpression != null) {
                lastExpression = nextExpression;
            } else if (lastExpression != null && lastOperator != null && nextExpression != null) {
                lastExpression = new Calculation(lastExpression, nextExpression, lastOperator);
                lastOperator = null;
            }
        }
        return lastExpression;
    }
}
