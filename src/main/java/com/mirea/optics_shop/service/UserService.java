package com.mirea.optics_shop.service;

import com.mirea.optics_shop.dto.UserDto;
import com.mirea.optics_shop.exception.UserAlreadyExistsException;
import com.mirea.optics_shop.model.User;
import com.mirea.optics_shop.model.Group;
import com.mirea.optics_shop.repository.GroupRepository;
import com.mirea.optics_shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        if ((groupRepository.findByCode("user") == null) && (groupRepository.findByCode("admin") == null)) {
            groupRepository.save(new Group("user", "user"));
            groupRepository.save(new Group("admin", "admin"));
        }
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void register(UserDto user) throws UserAlreadyExistsException {
        if(checkIfUserExist(user.getEmail())){
            throw new UserAlreadyExistsException("User already exists for this email");
        }
        User userEntity = new User();
        BeanUtils.copyProperties(user, userEntity);
        encodePassword(userEntity, user);
        updateUserGroup(userEntity);
        userRepository.save(userEntity);
    }

    private void updateUserGroup(User user){
        Group group = groupRepository.findByCode("user");
        user.addUserGroups(group);
    }

    public boolean checkIfUserExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    private void encodePassword(User userEntity, UserDto user){
        userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    }
}
