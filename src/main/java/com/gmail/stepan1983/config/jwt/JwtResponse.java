package com.gmail.stepan1983.config.jwt;

public class JwtResponse {
    private String token;
    private String type = "Bearer";

    public JwtResponse(String access1Token) {
        this.token = access1Token;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

}