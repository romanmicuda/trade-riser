package com.app.trade_riser.watchlistItem.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

import com.app.trade_riser.watchlist.data.Watchlist;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class WatchlistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "watchlist_id", nullable = false)
    private Watchlist watchlist;

    @Column(nullable = false)
    private String symbol;

    private BigDecimal targetPrice;
}
