package com.userservice.user.model;

import org.apache.catalina.User;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "token_info")
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "users", referencedColumnName = "id")
    private DAOUser user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;


    public DAOUser getUser() {
        return user;
    }

    public void setUser(DAOUser user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }
}
