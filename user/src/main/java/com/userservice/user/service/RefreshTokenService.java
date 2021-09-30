package com.userservice.user.service;

import com.userservice.user.dao.RefreshTokenRepository;
import com.userservice.user.dao.UserDao;
import com.userservice.user.exception.TokenRefreshException;
import com.userservice.user.model.RefreshTokenEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${jwt.tokenValidity}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserDao userDao;

    public Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshTokenEntity createRefreshToken(Long id) {
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();

        refreshTokenEntity.setUser(userDao.findById(id).get());
        refreshTokenEntity.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshTokenEntity.setToken(UUID.randomUUID().toString());

        refreshTokenEntity = refreshTokenRepository.save(refreshTokenEntity);
        return refreshTokenEntity;
    }

    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new  TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(userDao.findById(userId).get());
    }
}
