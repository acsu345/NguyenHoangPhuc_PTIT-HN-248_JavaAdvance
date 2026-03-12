package org.example;

import org.example.Bai01;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Bai01Test {

    @Test
    void TC01_validUsername() {
        String username = "user123";
        boolean result = Bai01.isValidUsername(username);
        assertTrue(result);
    }

    @Test
    void TC02_usernameTooShort() {
        String username = "abc";
        boolean result = Bai01.isValidUsername(username);
        assertFalse(result);
    }

    @Test
    void TC03_usernameContainsSpace() {
        String username = "user name";
        boolean result = Bai01.isValidUsername(username);
        assertFalse(result);
    }
}