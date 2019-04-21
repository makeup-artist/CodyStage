package com.cody.codystage.security;

import com.cody.codystage.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * @Classname JwtUser
 * @Description TODO
 * @Date 2019/4/18 22:14
 * @Created by ZQ
 */
public class JwtUser implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUser() {
    }

    public JwtUser(User user){
        id = user.getId();
        username = user.getUsername();
        password = user.getPassword();
        authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
    }

    // 获取权限信息
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public Long getId(){
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // 账号是否未过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 账号是否未锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 账号凭证是否未过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "JwtUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';

    }
}
