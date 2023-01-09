package by.itacademy.profiler.usecasses.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {
    public static String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
