package ch.neukom.day18;

public class SubexpressionHelper {
    private SubexpressionHelper() {
    }

    public static String getSubexpression(String line, int startIndex) {
        int nextBrace = line.indexOf('(', startIndex);
        int endBrace = line.indexOf(')', startIndex);
        while (nextBrace >= 0 && endBrace > nextBrace) {
            endBrace = line.indexOf(')', endBrace + 1);
            nextBrace = line.indexOf('(', nextBrace + 1);
        }
        return line.substring(startIndex, endBrace);
    }
}
