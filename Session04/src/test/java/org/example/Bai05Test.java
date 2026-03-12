package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Bai05Test {

    Bai05 service = new Bai05();
    Bai05.User user;

    @AfterEach
    void cleanUp() {
        user = null;
    }

    @Test
    void adminPermissions() {
        user = new Bai05.User(Bai05.Role.ADMIN);

        assertAll(
                () -> assertTrue(service.canPerformAction(user, Bai05.Action.DELETE_USER)),
                () -> assertTrue(service.canPerformAction(user, Bai05.Action.LOCK_USER)),
                () -> assertTrue(service.canPerformAction(user, Bai05.Action.VIEW_PROFILE))
        );
    }

    @Test
    void moderatorPermissions() {
        user = new Bai05.User(Bai05.Role.MODERATOR);

        assertAll(
                () -> assertFalse(service.canPerformAction(user, Bai05.Action.DELETE_USER)),
                () -> assertTrue(service.canPerformAction(user, Bai05.Action.LOCK_USER)),
                () -> assertTrue(service.canPerformAction(user, Bai05.Action.VIEW_PROFILE))
        );
    }

    @Test
    void userPermissions() {
        user = new Bai05.User(Bai05.Role.USER);

        assertAll(
                () -> assertFalse(service.canPerformAction(user, Bai05.Action.DELETE_USER)),
                () -> assertFalse(service.canPerformAction(user, Bai05.Action.LOCK_USER)),
                () -> assertTrue(service.canPerformAction(user, Bai05.Action.VIEW_PROFILE))
        );
    }
}