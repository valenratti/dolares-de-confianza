package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtResponse {
    private final Long userId;
    private String token;
    private String type = "Bearer";
    private String username;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtResponse(Long userId, String token, String username, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.token = token;
        this.username = username;
        this.authorities = authorities;
    }

    public Long getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
