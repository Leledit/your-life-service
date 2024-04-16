package com.yourlife.your.life.service.user.impl;

import com.yourlife.your.life.model.dto.user.UserDTO;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.model.vo.user.UserLoginRequestVO;
import com.yourlife.your.life.model.vo.user.UserRegistertRequestVO;
import com.yourlife.your.life.repository.user.UserRepository;
import com.yourlife.your.life.service.user.UserService;
import com.yourlife.your.life.utils.PasswordUtil;
import com.yourlife.your.life.utils.TokenUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public UserDTO createUser(UserRegistertRequestVO userPostRequestVO) {

        Optional<User> userFound = this.userRepository.findFirstByEmail(userPostRequestVO.getEmail());

        if(userFound.isPresent()){
            throw new RuntimeException("Email ja cadastrado no sistema");
        }

        User user = modelMapper.map(userPostRequestVO,User.class);

        user.setPassword(PasswordUtil.encodePassword(user.getPassword()));

        User savedUser =  userRepository.save(user);

        UserDTO userDTO = modelMapper.map(user,UserDTO.class);

        userDTO.setToken(tokenUtils.generateToken(savedUser.getId()));

        return userDTO;
    }

    @Override
    public UserDTO findByLogin(User user) {
        return null;
    }

    @Override
    public UserDTO loginUser(UserLoginRequestVO userLoginRequestVO) {

        Optional<User> userFound = this.userRepository.findFirstByEmail(userLoginRequestVO.getEmail());

        if(userFound.isEmpty()){
            throw new RuntimeException("O email fornecido não foi encontrado em nosso sistema!");
        }
        User user = userFound.get();

        if(!PasswordUtil.matches(userLoginRequestVO.getPassword(),user.getPassword())){
            throw new RuntimeException("A senha fornecida está incorreta!");
        }

        String token = tokenUtils.generateToken(user.getId());

        UserDTO userDTO = modelMapper.map(user,UserDTO.class);
        userDTO.setToken(token);

        return userDTO;
    }
}
