package com.app.trade_riser.transaction.data;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.app.trade_riser.holding.data.Holding;

import jakarta.persistence.*;


@Entity
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "holding_id", nullable = false)
    private Holding holding;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    private BigDecimal quantity;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDateTime transactionDate = LocalDateTime.now();

    private BigDecimal fee;

    public BigDecimal getTotalCost() {
        BigDecimal baseCost = quantity.multiply(price);
        return fee != null ? baseCost.add(fee) : baseCost;
    }

    public enum TransactionType {
        BUY, SELL
    }
}