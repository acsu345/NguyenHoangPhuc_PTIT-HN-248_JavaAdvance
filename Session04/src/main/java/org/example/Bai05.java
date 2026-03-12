package org.example;

public class Bai05 {

    public enum Role {
        ADMIN, MODERATOR, USER
    }

    public enum Action {
        DELETE_USER, LOCK_USER, VIEW_PROFILE
    }

    public static class User {
        Role role;

        public User(Role role) {
            this.role = role;
        }
    }

    public boolean canPerformAction(User user, Action action) {
        switch (user.role) {
            case ADMIN:
                return true;
            case MODERATOR:
                if (action == Action.DELETE_USER) return false;
                return true;
            case USER:
                return action == Action.VIEW_PROFILE;
            default:
                return false;
        }
    }
}