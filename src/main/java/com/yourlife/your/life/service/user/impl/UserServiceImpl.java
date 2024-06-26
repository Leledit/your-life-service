package com.yourlife.your.life.service.user.impl;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.user.UserDTO;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.repository.user.UserRepository;
import com.yourlife.your.life.service.user.UserService;
import com.yourlife.your.life.utils.Logger;
import com.yourlife.your.life.utils.PasswordUtil;
import com.yourlife.your.life.utils.TokenUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired TokenUtils tokenUtils;

    @Override
    public User createUser(User user) {

        Optional<User> userFound = this.userRepository.findFirstByEmail(user.getEmail());

        if(userFound.isPresent()){
            throw new RuntimeException(ExceptionMessages.EMAIL_ALREADY_REGISTERED);
        }

        user.setPassword(PasswordUtil.encodePassword(user.getPassword()));

        User savedUser =  userRepository.save(user);
        savedUser.setCreatedAt(LocalDateTime.now());

        return savedUser;
    }

    @Override
    public User loginUser(User user) {

        Optional<User> userFound = this.userRepository.findFirstByEmail(user.getEmail());

        if(userFound.isEmpty()){
            throw new RuntimeException(ExceptionMessages.INVALID_CREDENTIALS);
        }
        User userData = userFound.get();

        if(!PasswordUtil.matches(user.getPassword(),userData.getPassword())){
            throw new RuntimeException(ExceptionMessages.INVALID_CREDENTIALS);
        }

        return userData;
    }
}
