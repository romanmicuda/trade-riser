package com.app.trade_riser.holding.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.app.trade_riser.portfolio.data.Portfolio;
import com.app.trade_riser.transaction.data.Transaction;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class Holding {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false)
    private BigDecimal quantity;

    @Column(nullable = false)
    private BigDecimal averagePurchasePrice;

    private BigDecimal currentPrice;

    @OneToMany(mappedBy = "holding")
    private List<Transaction> transactions;

    public BigDecimal calculateUnrealizedGain() {
        if (currentPrice == null || quantity == null) return BigDecimal.ZERO;
        return currentPrice.subtract(averagePurchasePrice).multiply(quantity);
    }

    public BigDecimal calculateCurrentValue() {
        if (currentPrice == null || quantity == null) return BigDecimal.ZERO;
        return currentPrice.multiply(quantity);
    }

    public void updateCurrentPrice(BigDecimal newPrice) {
        this.currentPrice = newPrice;
    }
}
