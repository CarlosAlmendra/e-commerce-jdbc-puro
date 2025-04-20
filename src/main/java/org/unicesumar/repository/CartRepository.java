package org.unicesumar.repository;

import org.unicesumar.entity.Cart;
import org.unicesumar.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        UserRepository userRepository = new UserRepository(connection);

        String query = "SELECT * FROM cart WHERE uuid = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1, id.toString());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                UUID userId = UUID.fromString(resultSet.getString("user_id"));

                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found: " + userId));

                return Optional.of(new Cart(
                        UUID.fromString(resultSet.getString("uuid")),
                        user
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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
