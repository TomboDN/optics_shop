package com.mirea.optics_shop.service;

import com.mirea.optics_shop.dto.CustomUserDto;
import com.mirea.optics_shop.dto.UserDto;
import com.mirea.optics_shop.model.User;
import com.mirea.optics_shop.model.Group;
import com.mirea.optics_shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public CustomUserDto loadUserByUsername(String email) throws UsernameNotFoundException {
        final User userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        return CustomUserDto.CustomUserBuilder.aCustomUser().withUsername(userEntity.getEmail())
                .withPassword(userEntity.getPassword())
                .withAuthorities(getAuthorities(userEntity)).build();
    }

    public UserDto getUserDtoByUsername(String email) {
        return new UserDto(userRepository.findByEmail(email));
    }

    private Collection<GrantedAuthority> getAuthorities(User user){
        Set<Group> groups = user.getGroups();
        Collection<GrantedAuthority> authorities = new ArrayList<>(groups.size());
        for(Group group : groups){
            authorities.add(new SimpleGrantedAuthority(group.getCode().toUpperCase()));
        }

        return authorities;
    }
}
