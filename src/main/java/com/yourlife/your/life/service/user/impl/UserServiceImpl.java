package com.yourlife.your.life.service.user.impl;

import com.yourlife.your.life.model.dto.user.UserDTO;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.model.vo.user.UserPostRequestVO;
import com.yourlife.your.life.repository.user.UserRepository;
import com.yourlife.your.life.service.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO createUser(UserPostRequestVO userPostRequestVO) {

        //verificar se ja existe um usuario cadastrado com o email informado

        User user = modelMapper.map(userPostRequestVO,User.class);

        userRepository.save(user);

        UserDTO userDTO = modelMapper.map(user,UserDTO.class);
        userDTO.setToken("token");

        return userDTO;
    }

    @Override
    public UserDTO findByLogin(User user) {
        return null;
    }
}
