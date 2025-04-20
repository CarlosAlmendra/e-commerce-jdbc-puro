package org.unicesumar.entity;

import java.util.UUID;

public class Product extends Entity {
    private String name;
    private double price;

    public Product(UUID uuid, String name, double price) {
        super(uuid);
        this.name = name;
        this.price = price;
    }

    public Product(String name, double price) {
        super(UUID.randomUUID());
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public String toString() {
        return "ID: " + getUuid().toString()
                + "\nName: " + name
                + "\nPrice: " + price;
    }
}
