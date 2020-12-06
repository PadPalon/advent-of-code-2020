package ch.neukom.day6;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ch.neukom.helper.InputResourceReader;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;

public class Second {
    private final static Splitter GROUP_SPLITTER = Splitter.on("\n\n").trimResults().omitEmptyStrings();
    private final static Splitter PERSON_SPLITTER = Splitter.on("\n").trimResults().omitEmptyStrings();

    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(Second.class)) {
            long solution = reader.readDefaultInput()
                .collect(Collectors.collectingAndThen(Collectors.joining("\n"), GROUP_SPLITTER::splitToStream))
                .map(PERSON_SPLITTER::splitToList)
                .mapToInt(group -> group.stream()
                    .map(String::chars)
                    .map(IntStream::boxed)
                    .map(answers -> answers.collect(Collectors.toSet()))
                    .reduce(Sets::intersection)
                    .orElse(Collections.emptySet())
                    .size()
                )
                .sum();
            System.out.printf("The solution is %d%n", solution);
        }
    }
}
