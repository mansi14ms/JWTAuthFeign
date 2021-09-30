package com.userservice.user.model;
import javax.validation.constraints.*;

public class TokenRefreshRequest {

    @NotBlank
    private String refreshtoken;

    public String getRefreshtoken() { return refreshtoken; }

    public void setRefreshtoken(String refreshtoken) { this.refreshtoken = refreshtoken; }


}
