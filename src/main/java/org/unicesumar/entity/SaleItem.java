package org.unicesumar.entity;

import java.util.List;

public class SaleItem extends Entity {

    private User user;
    private List<Product> products;
    private Double totalValue;

    public SaleItem(List<Product> products) {
        this.products = products;
    }

    public SaleItem(User user, List<Product> products, double totalValue) {
        super();
        this.user = user;
        this.products = products;
        this.totalValue = totalValue;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===Sales date ===\n");
        sb.append("User name: ").append(user.getName()).append("\n");
        sb.append("Products:\n");
        products.forEach(p -> sb.append("- ").append(p.getName()).append("\n"));
        sb.append("Total value: R$ ").append(totalValue).append("\n");
        sb.append("Payment type: ").append("PIX").append("\n");
        return sb.toString();
    }
}
