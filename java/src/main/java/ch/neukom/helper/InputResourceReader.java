package ch.neukom.helper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class InputResourceReader implements AutoCloseable {
    private final Class<?> clazz;

    private BufferedReader reader;

    public InputResourceReader(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Stream<String> readDefaultInput() {
        return readInput("input");
    }

    public Stream<String> readInput(String name) {
        InputStream is = clazz.getResourceAsStream(name);
        InputStreamReader isr = new InputStreamReader(is);
        reader = new BufferedReader(isr);
        return reader.lines();
    }

    public String getFirstLine() {
        return readDefaultInput().findFirst().orElseThrow();
    }

    public String getLastLine() {
        return readDefaultInput().skip(getLineCount() - 1).findFirst().orElseThrow();
    }

    public long getLineCount() {
        return readDefaultInput().count();
    }

    @Override
    public void close() throws Exception {
        if (reader != null) {
            reader.close();
        }
    }
}
