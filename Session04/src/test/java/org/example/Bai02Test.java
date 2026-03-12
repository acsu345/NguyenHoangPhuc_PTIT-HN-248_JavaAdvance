package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Bai02Test {

    @Test
    void TC01() {
        int age = 18;
        boolean result = Bai02.checkRegistrationAge(age);
        assertEquals(true, result);
    }

    @Test
    void TC02() {
        int age = 17;
        boolean result = Bai02.checkRegistrationAge(age);
        assertEquals(false, result);
    }

    @Test
    void TC03() {
        int age = -1;
        assertThrows(IllegalArgumentException.class, () -> {
            Bai02.checkRegistrationAge(age);
        });
    }
}