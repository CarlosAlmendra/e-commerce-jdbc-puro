package org.unicesumar.repository;

import org.unicesumar.entity.Sale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SaleRepository implements EntityRepository<Sale> {
    private final Connection connection;

    public SaleRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(Sale entity) {
        String saleQuery = "INSERT INTO sale VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(saleQuery);
            stmt.setString(1, entity.getUuid().toString());
            stmt.setString(2, entity.getUser().getUuid().toString());
            stmt.setString(3, entity.getCart().getUuid().toString());
            stmt.setDouble(4, entity.getTotalValue());
            stmt.setString(5, entity.getPaymentEnum().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Sale> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Sale> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(UUID id) {

    }
}
