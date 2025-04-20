package org.unicesumar.entity;

import java.util.UUID;

public class Cart extends Entity {
    private User user;

    public Cart(User user) {
        this.user = user;
    }

    public Cart(UUID uuid, User user) {
        super(uuid);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

