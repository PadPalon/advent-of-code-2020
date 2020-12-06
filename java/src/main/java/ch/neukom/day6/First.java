package ch.neukom.day6;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ch.neukom.helper.InputResourceReader;
import com.google.common.base.Splitter;

public class First {
    private final static Splitter GROUP_SPLITTER = Splitter.on("\n\n").trimResults().omitEmptyStrings();

    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(First.class)) {
            long solution = reader.readDefaultInput()
                .collect(Collectors.collectingAndThen(Collectors.joining("\n"), GROUP_SPLITTER::splitToStream))
                .map(group -> group.replaceAll("\\s+", ""))
                .map(String::chars)
                .map(IntStream::distinct)
                .mapToLong(IntStream::count)
                .sum();
            System.out.printf("The solution is %d%n", solution);
        }
    }
}
