package org.kehrbusch.util;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CachingPasswordEncoder implements PasswordEncoder {
    private final PasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final Cache<CharSequence, String> encodeCache = Caffeine.newBuilder().build();
    private final Cache<PasswordMatchKey, Boolean> matchCache = Caffeine.newBuilder().build();

    @Override
    public String encode(final CharSequence rawPassword) {
        return encodeCache.get(rawPassword, s -> encoder.encode(rawPassword));
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        //noinspection ConstantConditions
        return matchCache.get(new PasswordMatchKey(rawPassword, encodedPassword),
                k -> encoder.matches(rawPassword, encodedPassword));
    }
}
