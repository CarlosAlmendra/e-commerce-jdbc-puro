package org.unicesumar.repository;

import org.unicesumar.entity.Cart;
import org.unicesumar.entity.CartItems;
import org.unicesumar.entity.Product;
import org.unicesumar.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public List<CartItems> findAllProductsByCart(UUID idCart) {
        UserRepository userRepository = new UserRepository(connection);
        CartRepository cartRepository = new CartRepository(connection);
        ProductRepository productRepository = new ProductRepository(connection);

        String query = "SELECT * FROM cart_items WHERE cart_id = ?";
        List<CartItems> cartItems = new ArrayList<>();

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1, idCart.toString());
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                UUID userId = UUID.fromString(resultSet.getString("user_id"));
                UUID productId = UUID.fromString(resultSet.getString("product_id"));
                UUID cartId = UUID.fromString(resultSet.getString("cart_id"));

                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found: " + userId));

                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

                Cart cart = cartRepository.findById(cartId)
                        .orElseThrow(() -> new RuntimeException("Cart not found: " + cartId));


                CartItems cartItem = new CartItems(
                        UUID.fromString(resultSet.getString("uuid")),
                        user,
                        product,
                        cart,
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("unit_price")
                );
                cartItems.add(cartItem);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cartItems;
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
