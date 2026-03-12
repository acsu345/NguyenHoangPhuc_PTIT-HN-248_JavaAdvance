package org.example;

public class Bai02 {

    public static boolean checkRegistrationAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException();
        }
        return age >= 18;
    }
}