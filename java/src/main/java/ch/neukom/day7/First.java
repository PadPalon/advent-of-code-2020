package ch.neukom.day7;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import ch.neukom.helper.InputResourceReader;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import static ch.neukom.day7.Helper.*;

public class First {
    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(First.class)) {
            Multimap<String, ContentRule> rules = getBagRules(reader);
            Set<String> result = getResultBagTypes(rules);
            System.out.printf("%d types of bags can contain the golden bag%n", result.size());
        }
    }

    private static Set<String> getResultBagTypes(Multimap<String, ContentRule> rules) {
        Collection<ContentRule> values = rules.values();
        Set<String> result = new HashSet<>();
        Set<String> unhandledTypes = Sets.newHashSet("shiny gold");

        while (!unhandledTypes.isEmpty()) {
            String unhandledType = Iterables.getLast(unhandledTypes);
            for (ContentRule value : values) {
                if (value.getTargetType().equals(unhandledType)) {
                    result.add(value.getSourceType());
                    unhandledTypes.add(value.getSourceType());
                }
            }
            unhandledTypes.remove(unhandledType);
        }

        return result;
    }
}
