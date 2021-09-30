package com.userservice.user.dao;

import com.userservice.user.model.DAOUser;
import com.userservice.user.model.RefreshTokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);

    int deleteByUser(DAOUser daoUser);
}
