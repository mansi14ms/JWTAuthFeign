package com.userservice.user.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private final String refreshtoken;

    public JwtResponse(String jwttoken, String refreshtoken) {
        this.refreshtoken = refreshtoken;
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }

    public String getRefreshtoken() { return this.refreshtoken; }
}
