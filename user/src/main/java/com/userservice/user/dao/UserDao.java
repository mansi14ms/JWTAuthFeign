package com.userservice.user.dao;

import com.userservice.user.model.DAOUser;
import com.userservice.user.model.UserRole;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface UserDao extends CrudRepository<DAOUser, Long> {

    DAOUser findByUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "update users set role=?1 where username like ?2 ", nativeQuery = true)
    public void updateRole(String role, String username);
}
