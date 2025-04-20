package org.unicesumar.entity;

import java.util.UUID;

public class User extends Entity {
    private String name;
    private String email;

    public User() {
    }

    public User(String name, String email) {
        super(UUID.randomUUID());
        this.name = name;
        this.email = email;
    }

    public User(UUID uuid, String name, String email) {
        super(uuid);
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return String.format("%s - %s", this.name, this.email);
    }
}
