package org.example;

import java.time.LocalDate;
import java.util.List;

public class Bai06 {

    public static class User {
        public String email;
        public LocalDate birthDate;

        public User(String email, LocalDate birthDate) {
            this.email = email;
            this.birthDate = birthDate;
        }
    }

    public static class UserProfile {
        public String email;
        public LocalDate birthDate;

        public UserProfile(String email, LocalDate birthDate) {
            this.email = email;
            this.birthDate = birthDate;
        }
    }

    public User updateProfile(User existingUser, UserProfile newProfile, List<User> allUsers) {

        if (newProfile.birthDate.isAfter(LocalDate.now())) {
            return null;
        }

        if (!newProfile.email.equals(existingUser.email)) {
            for (User u : allUsers) {
                if (u.email.equals(newProfile.email)) {
                    return null;
                }
            }
        }

        existingUser.email = newProfile.email;
        existingUser.birthDate = newProfile.birthDate;

        return existingUser;
    }
}