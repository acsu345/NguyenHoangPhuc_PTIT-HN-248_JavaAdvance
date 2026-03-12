package org.example;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    Bai06 service = new Bai06();

    @Test
    void updateProfileWithValidEmailAndBirthDate() {
        Bai06.User existing = new Bai06.User("old@gmail.com", LocalDate.of(2000,1,1));
        Bai06.UserProfile profile = new Bai06.UserProfile("new@gmail.com", LocalDate.of(1999,1,1));

        List<Bai06.User> users = new ArrayList<>();
        users.add(existing);

        Bai06.User result = service.updateProfile(existing, profile, users);

        assertNotNull(result);
    }

    @Test
    void rejectUpdateWhenBirthDateIsFuture() {
        Bai06.User existing = new Bai06.User("user@gmail.com", LocalDate.of(2000,1,1));
        Bai06.UserProfile profile = new Bai06.UserProfile("new@gmail.com", LocalDate.now().plusDays(1));

        List<Bai06.User> users = new ArrayList<>();

        Bai06.User result = service.updateProfile(existing, profile, users);

        assertNull(result);
    }

    @Test
    void rejectUpdateWhenEmailAlreadyExists() {
        Bai06.User existing = new Bai06.User("user1@gmail.com", LocalDate.of(2000,1,1));
        Bai06.User other = new Bai06.User("duplicate@gmail.com", LocalDate.of(1998,1,1));

        Bai06.UserProfile profile = new Bai06.UserProfile("duplicate@gmail.com", LocalDate.of(1999,1,1));

        List<Bai06.User> users = new ArrayList<>();
        users.add(other);

        Bai06.User result = service.updateProfile(existing, profile, users);

        assertNull(result);
    }

    @Test
    void allowUpdateWhenEmailUnchanged() {
        Bai06.User existing = new Bai06.User("same@gmail.com", LocalDate.of(2000,1,1));
        Bai06.UserProfile profile = new Bai06.UserProfile("same@gmail.com", LocalDate.of(1995,1,1));

        List<Bai06.User> users = new ArrayList<>();
        users.add(existing);

        Bai06.User result = service.updateProfile(existing, profile, users);

        assertNotNull(result);
    }

    @Test
    void allowUpdateWhenUserListEmpty() {
        Bai06.User existing = new Bai06.User("user@gmail.com", LocalDate.of(2000,1,1));
        Bai06.UserProfile profile = new Bai06.UserProfile("new@gmail.com", LocalDate.of(1999,1,1));

        List<Bai06.User> users = new ArrayList<>();

        Bai06.User result = service.updateProfile(existing, profile, users);

        assertNotNull(result);
    }

    @Test
    void rejectWhenEmailDuplicateAndBirthDateFuture() {
        Bai06.User existing = new Bai06.User("user1@gmail.com", LocalDate.of(2000,1,1));
        Bai06.User other = new Bai06.User("duplicate@gmail.com", LocalDate.of(1998,1,1));

        Bai06.UserProfile profile = new Bai06.UserProfile("duplicate@gmail.com", LocalDate.now().plusDays(2));

        List<Bai06.User> users = new ArrayList<>();
        users.add(other);

        Bai06.User result = service.updateProfile(existing, profile, users);

        assertNull(result);
    }
}