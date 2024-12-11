package com.mdd.back.stubs;

import com.mdd.back.entities.User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class UserStub {

    public static User getDefaultUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("defaultUser");
        user.setEmail("default@example.com");
        user.setPassword("defaultPassword123");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    public static User getAdminUser() {
        User user = new User();
        user.setId(2L);
        user.setUsername("adminUser");
        user.setEmail("admin@example.com");
        user.setPassword("adminPassword123");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    public static User getUserWithInvalidEmail() {
        User user = new User();
        user.setId(3L);
        user.setUsername("invalidEmailUser");
        user.setEmail("invalid-email");
        user.setPassword("password123");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    public static User getUserWithShortPassword() {
        User user = new User();
        user.setId(4L);
        user.setUsername("shortPasswordUser");
        user.setEmail("shortpass@example.com");
        user.setPassword("123");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    public static User getUserWithNoUsername() {
        User user = new User();
        user.setId(5L);
        user.setUsername(null);
        user.setEmail("nousername@example.com");
        user.setPassword("password123");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    public static List<User> getUserList() {
        return Arrays.asList(
                getDefaultUser(),
                getAdminUser(),
                getUserWithInvalidEmail(),
                getUserWithShortPassword(),
                getUserWithNoUsername()
        );
    }
}
