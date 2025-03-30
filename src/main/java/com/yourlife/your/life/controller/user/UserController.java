package com.yourlife.your.life.controller.user;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.user.UserDTO;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.model.dto.user.UserLoginDTO;
import com.yourlife.your.life.model.dto.user.UserRegistertVO;
import com.yourlife.your.life.service.user.UserService;
import com.yourlife.your.life.utils.TokenUtils;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    TokenUtils tokenUtils;

    @ResponseBody
    @PostMapping(value = "/user/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createAnAccount(@RequestBody @Valid UserRegistertVO userPostRequestVO){
        if(userPostRequestVO.getName() == null || userPostRequestVO.getEmail() == null || userPostRequestVO.getPassword() == null){
             throw new RuntimeException(ExceptionMessages.INVALID_REQUEST_COMPONENT);
        }

        User user = modelMapper.map(userPostRequestVO,User.class);

        UserDTO dataUser = modelMapper.map(this.userService.createUser(user),UserDTO.class);
        dataUser.setToken(tokenUtils.generateToken(dataUser.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(dataUser);
    }

    @ResponseBody
    @PostMapping(value = "/user/login",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> login(@RequestBody @Valid UserLoginDTO userLoginDTO){
        User user = modelMapper.map(userLoginDTO,User.class);

        UserDTO dataUser = modelMapper.map(this.userService.loginUser(user),UserDTO.class);
        dataUser.setToken(tokenUtils.generateToken(dataUser.getId()));

        return ResponseEntity.ok(dataUser);
    }
}
