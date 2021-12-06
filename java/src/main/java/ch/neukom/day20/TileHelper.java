package ch.neukom.day20;

import ch.neukom.helper.InputResourceReader;
import com.google.common.base.Splitter;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TileHelper {
    private static final Pattern TILE_PATTERN = Pattern.compile("Tile ([0-9]+):([#.\\n]+)");

    private static final Splitter TILE_SPLITTER = Splitter.on("\n\n").trimResults().omitEmptyStrings();

    private TileHelper() {
    }

    public static Set<Tile> loadTiles(InputResourceReader reader) {
        return reader.readDefaultInput()
                .collect(Collectors.collectingAndThen(Collectors.joining("\n"), TILE_SPLITTER::splitToStream))
                .map(TILE_PATTERN::matcher)
                .filter(Matcher::find)
                .map(matcher -> new Tile(Integer.parseInt(matcher.group(1)), matcher.group(2).trim()))
                .collect(Collectors.toSet());
    }
}
