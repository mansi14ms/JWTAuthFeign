package com.userservice.user.service;
import java.util.ArrayList;

import com.userservice.user.dao.UserDao;
import com.userservice.user.model.DAOUser;
import com.userservice.user.model.UserDTO;
import com.userservice.user.model.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class JwtUserDetailsService implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DAOUser user = userDao.findByUsername(username);
        logger.warn(String.valueOf(user));
        if (user == null) {
            logger.warn("heree");
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        logger.warn("user");
        logger.warn(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    public HttpStatus save(UserDTO user) {
        DAOUser newUser = new DAOUser();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setRole(UserRole.GENERAL);
        logger.warn(newUser.getPassword());
        return ResponseEntity.ok(userDao.save(newUser)).getStatusCode();
    }

    public boolean checkRole(String activeUserName) {
     DAOUser user =  userDao.findByUsername(activeUserName);
     if(user == null)
         return false;
     System.out.println("role = " +user.getRole().toString());
     if(user.getRole().toString().compareTo("ADMIN") == 0)
         return true;
        System.out.println("falseee");
     return false;

    }

    public boolean changeRole(String userNameOfChangedRolePerson) {
        DAOUser user = userDao.findByUsername(userNameOfChangedRolePerson);
        if(user == null)
            return false;
        user.setRole(UserRole.ADMIN);
        System.out.println("role = " +user.getRole());
        userDao.updateRole(user.getRole().toString(),user.getUsername());
        return true;



    }

    public Long getId(String username) {
        DAOUser user = userDao.findByUsername(username);
        return user.getId();
    }
}
