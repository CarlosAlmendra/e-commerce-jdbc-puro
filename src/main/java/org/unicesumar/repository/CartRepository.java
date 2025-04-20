package org.unicesumar.repository;

import org.unicesumar.entity.Cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CartRepository implements EntityRepository<Cart> {
    private final Connection connection;

    public CartRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Cart entity) {
        String productsQuery = "INSERT INTO cart VALUES (?, ?)";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(productsQuery);
            stmt.setString(1, entity.getUuid().toString());
            stmt.setString(2, entity.getUser().getUuid().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Cart> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Cart> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(UUID id) {

    }
}
