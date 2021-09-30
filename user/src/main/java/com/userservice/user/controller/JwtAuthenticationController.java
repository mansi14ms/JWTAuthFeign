package com.userservice.user.controller;


import java.util.Objects;

import com.userservice.user.config.JwtTokenUtil;
import com.userservice.user.exception.TokenRefreshException;
import com.userservice.user.model.*;
import com.userservice.user.service.JwtUserDetailsService;
import com.userservice.user.service.RefreshTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@CrossOrigin
public class JwtAuthenticationController {

    Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        Long id = userDetailsService.getId(userDetails.getUsername());
        RefreshTokenEntity refreshTokenEntity = refreshTokenService.createRefreshToken(id);
        return ResponseEntity.ok(new JwtResponse(token, refreshTokenEntity.getToken()));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO users) throws Exception {
        return ResponseEntity.ok(userDetailsService.save(users));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshtoken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshTokenEntity::getUser)
                .map(user -> {
                    String token = jwtTokenUtil.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }


    @GetMapping("/changeRole/{userNameOfChangedRolePerson}")
    public ResponseEntity<?> changeRole(@PathVariable String userNameOfChangedRolePerson) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String activeUserName = auth.getName();
        this.logger.info(activeUserName);
        if(userDetailsService.checkRole(activeUserName)) {
            if(userDetailsService.changeRole(userNameOfChangedRolePerson))
                return new ResponseEntity(HttpStatus.OK);
           return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
        else
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
