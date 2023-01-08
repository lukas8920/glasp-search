package org.kehrbusch.util;

import java.util.Objects;

public class PasswordMatchKey {
    private final CharSequence rawPassword;
    private final String encodedPassword;

    public PasswordMatchKey(CharSequence rawPassword, String encodedPassword) {
        this.rawPassword = rawPassword;
        this.encodedPassword = encodedPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordMatchKey that = (PasswordMatchKey) o;
        return Objects.equals(rawPassword, that.rawPassword) &&
                Objects.equals(encodedPassword, that.encodedPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rawPassword, encodedPassword);
    }
}
