package com.mirea.optics_shop.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
public class CustomUserDto implements UserDetails {

    private String username;
    private String password;
    private Collection<GrantedAuthority> authorities;
    private  boolean accountNonExpired;
    private  boolean accountNonLocked;
    private  boolean credentialsNonExpired;
    private  boolean enabled;

    public CustomUserDto(String username, String password, boolean enabled,
                         boolean accountNonExpired, boolean credentialsNonExpired,
                         boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {

        if (((username == null) || "".equals(username)) || (password == null)) {
            throw new IllegalArgumentException(
                    "Cannot pass null or empty values to constructor");
        }

        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean equals(Object rhs) {
        if (rhs instanceof CustomUserDto) {
            return username.equals(((CustomUserDto) rhs).username);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    public static final class CustomUserBuilder {
        private String password;
        private String username;
        private Collection<GrantedAuthority> authorities;
        private boolean accountNonExpired;
        private boolean accountNonLocked;
        private boolean credentialsNonExpired;
        private boolean enabled;

        private CustomUserBuilder() {
        }

        public static CustomUserBuilder aCustomUser() {
            return new CustomUserBuilder();
        }

        public CustomUserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public CustomUserBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public CustomUserBuilder withAuthorities(Collection<GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public CustomUserBuilder withAccountNonExpired(boolean accountNonExpired) {
            this.accountNonExpired = accountNonExpired;
            return this;
        }

        public CustomUserBuilder withAccountNonLocked(boolean accountNonLocked) {
            this.accountNonLocked = accountNonLocked;
            return this;
        }

        public CustomUserBuilder withCredentialsNonExpired(boolean credentialsNonExpired) {
            this.credentialsNonExpired = credentialsNonExpired;
            return this;
        }

        public CustomUserBuilder withEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public CustomUserDto build() {
            CustomUserDto customUserDto = new CustomUserDto(username, password, !enabled, !accountNonExpired, !credentialsNonExpired, !accountNonLocked, authorities);
            customUserDto.authorities = this.authorities;
            return customUserDto;
        }
    }
}
