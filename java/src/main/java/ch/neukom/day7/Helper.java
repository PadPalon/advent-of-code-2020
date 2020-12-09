package ch.neukom.day7;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import ch.neukom.helper.InputResourceReader;
import com.google.common.base.Splitter;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class Helper {
    private static final Pattern LINE_PATTERN = Pattern.compile("([a-z]+ [a-z]+) bags contain ([a-z0-9 ,]+)");
    private static final Pattern CONTENT_PATTERN = Pattern.compile("([0-9]+|no) ([a-z]+ [a-z]+)");
    private static final Splitter CONTENT_SPLITTER = Splitter.on(',').trimResults().omitEmptyStrings();

    public static Multimap<String, ContentRule> getBagRules(InputResourceReader reader) {
        return reader.readDefaultInput()
            .map(LINE_PATTERN::matcher)
            .filter(Matcher::find)
            .flatMap(Helper::parseContentRule)
            .collect(ArrayListMultimap::create, (map, rule) -> map.put(rule.getSourceType(), rule), Multimap::putAll);
    }

    private static Stream<ContentRule> parseContentRule(Matcher lineMatcher) {
        return CONTENT_SPLITTER.splitToStream(lineMatcher.group(2))
            .map(CONTENT_PATTERN::matcher)
            .filter(Matcher::find)
            .map(content -> new ContentRule(
                lineMatcher.group(1),
                parseBagCount(content),
                content.group(2)
            ));
    }

    private static int parseBagCount(Matcher content) {
        String countString = content.group(1);
        if ("no".equals(countString)) {
            return 0;
        } else {
            return Integer.parseInt(countString);
        }
    }

    public static class ContentRule {
        private final String sourceType;
        private final int count;
        private final String targetType;

        private ContentRule(String sourceType,
                            int count,
                            String bagType) {
            this.sourceType = sourceType;
            this.count = count;
            this.targetType = bagType;
        }

        public String getSourceType() {
            return sourceType;
        }

        public int getCount() {
            return count;
        }

        public String getTargetType() {
            return targetType;
        }
    }
}
