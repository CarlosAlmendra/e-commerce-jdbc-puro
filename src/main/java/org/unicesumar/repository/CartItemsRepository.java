package org.unicesumar.repository;

import org.unicesumar.entity.CartItems;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CartItemsRepository implements EntityRepository<CartItems>{
    private final Connection connection;

    public CartItemsRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(CartItems entity) {
        String productsQuery = "INSERT INTO cart_items VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(productsQuery);
            stmt.setString(1, entity.getUuid().toString());
            stmt.setString(2, entity.getUser().getUuid().toString());
            stmt.setString(3, entity.getProduct().getUuid().toString());
            stmt.setString(4, entity.getCart().getUuid().toString());
            stmt.setInt(5, entity.getQuantity());
            stmt.setDouble(6, entity.getPrice());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<CartItems> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<CartItems> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(UUID id) {

    }
}
