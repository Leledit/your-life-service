package com.yourlife.your.life.service.user.impl;

import com.yourlife.your.life.model.dto.user.UserDTO;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.model.entity.user.UserAuth;
import com.yourlife.your.life.model.vo.user.UserLoginRequestVO;
import com.yourlife.your.life.model.vo.user.UserRegistertRequestVO;
import com.yourlife.your.life.repository.user.UserRepository;
import com.yourlife.your.life.service.user.UserService;
import com.yourlife.your.life.utils.PasswordUtil;
import com.yourlife.your.life.utils.TokenUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired TokenUtils tokenUtils;

    @Override
    public UserDTO createUser(User user) {

        Optional<User> userFound = this.userRepository.findFirstByEmail(user.getEmail());

        if(userFound.isPresent()){
            throw new RuntimeException("Email ja cadastrado no sistema");
        }

        user.setPassword(PasswordUtil.encodePassword(user.getPassword()));

        User savedUser =  userRepository.save(user);
        savedUser.setCreatedAt(LocalDateTime.now());

        UserDTO userDTO = modelMapper.map(user,UserDTO.class);

        userDTO.setToken(tokenUtils.generateToken(savedUser.getId()));

        return userDTO;
    }

    @Override
    public UserDTO loginUser(User user) {

        Optional<User> userFound = this.userRepository.findFirstByEmail(user.getEmail());

        if(userFound.isEmpty()){
            throw new RuntimeException("O email fornecido não foi encontrado em nosso sistema!");
        }
        User userData = userFound.get();

        if(!PasswordUtil.matches(user.getPassword(),userData.getPassword())){
            throw new RuntimeException("A senha fornecida está incorreta!");
        }

        String token = tokenUtils.generateToken(userData.getId());

        UserDTO userDTO = modelMapper.map(userData,UserDTO.class);
        userDTO.setToken(token);

        return userDTO;
    }
}
