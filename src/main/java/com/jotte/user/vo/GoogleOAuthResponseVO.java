package com.jotte.user.vo;

import lombok.Data;

@Data
public class GoogleOAuthResponseVO {
    private String accessToken;
    private String expiresIn;
    private String refreshToken;
    private String scope;
    private String tokenType;
    private String idToken;
}
