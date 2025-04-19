package org.unicesumar.entity;

public class SaleItem extends Entity {
    private final Product product;
    private final int quantity;

    public SaleItem(Product product, int quantity) {
        super();
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return quantity + " x " + product.getName() + " - $" + getTotalPrice();
    }
}
