package io.my.springsecurityjwt;

public class AuthenticationRes {

    private String jwt;

    public AuthenticationRes(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
