package com.app.trade_riser.user.data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.app.trade_riser.portfolio.data.Portfolio;
import com.app.trade_riser.tradingRule.data.TradingRule;
import com.app.trade_riser.watchlist.data.Watchlist;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users", 
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "username"),
      @UniqueConstraint(columnNames = "email") 
    })
@Getter
@Setter
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(  name = "user_roles", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

  // Password reset fields
  private String resetToken;
  
  private LocalDateTime resetTokenExpiration;


    @OneToMany(mappedBy = "user")
    private List<Portfolio> portfolios;

    @OneToMany(mappedBy = "user")
    private List<Watchlist> watchlists;

    @OneToMany(mappedBy = "user")
    private List<TradingRule> tradingRules;



  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.roles = new HashSet<>();
  }
}
