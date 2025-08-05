package com.app.trade_riser.tradingRule.data;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

import com.app.trade_riser.user.data.User;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class TradingRule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String symbol;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Condition condition;

    @Column(nullable = false)
    private BigDecimal thresholdPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Action action;

    @Column(nullable = false)
    private BigDecimal quantity;

    @Column(nullable = false)
    private Boolean isActive = true;

    public boolean evaluateRule(BigDecimal currentPrice) {
        if (!isActive || currentPrice == null) return false;
        return switch (condition) {
            case GREATER_THAN -> currentPrice.compareTo(thresholdPrice) > 0;
            case LESS_THAN -> currentPrice.compareTo(thresholdPrice) < 0;
        };
    }

    public enum Condition {
        GREATER_THAN, LESS_THAN
    }
    public enum Action {
        BUY, SELL
    }
}