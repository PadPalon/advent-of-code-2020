package ch.neukom.day2;

import java.util.Objects;

import ch.neukom.helper.InputResourceReader;

public class Second {
    public static void main(String[] args) throws Exception {
        try (InputResourceReader reader = new InputResourceReader(Second.class)) {
            long validPasswords = reader.readDefaultInput()
                .map(PasswordLine::parse)
                .filter(Objects::nonNull)
                .filter(PasswordLine::isValidForNewPolicy)
                .count();
            System.out.printf("%d passwords are valid%n", validPasswords);
        }
    }
}
