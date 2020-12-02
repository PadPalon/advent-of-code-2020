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
        InputStream is = clazz.getResourceAsStream("input");
        InputStreamReader isr = new InputStreamReader(is);
        reader = new BufferedReader(isr);
        return reader.lines();
    }

    @Override
    public void close() throws Exception {
        if (reader != null) {
            reader.close();
        }
    }
}
