package com.app.trade_riser.portfolio.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.app.trade_riser.holding.data.Holding;
import com.app.trade_riser.user.data.User;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    private BigDecimal totalValue;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "portfolio")
    private List<Holding> holdings;

    public BigDecimal calculateTotalValue() {
        if (holdings == null) return BigDecimal.ZERO;
        return holdings.stream()
                .map(Holding::calculateCurrentValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}