package com.app.trade_riser.watchlist.data;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

import com.app.trade_riser.user.data.User;
import com.app.trade_riser.watchlistItem.data.WatchlistItem;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class Watchlist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "watchlist")
    private List<WatchlistItem> items;
}
