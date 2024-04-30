package com.yourlife.your.life.controller.user;

import com.yourlife.your.life.model.dto.user.UserDTO;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.model.vo.user.UserLoginVO;
import com.yourlife.your.life.model.vo.user.UserRegistertVO;
import com.yourlife.your.life.service.user.UserService;
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

    @PostMapping(value = "/user/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<UserDTO> createAnAccount(@RequestBody @Valid UserRegistertVO userPostRequestVO){

        User user = modelMapper.map(userPostRequestVO,User.class);

        UserDTO dataUser = this.userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(dataUser);
    }

    @PostMapping(value = "/user/login",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<UserDTO> login(@RequestBody @Valid UserLoginVO userLoginVO){
        User user = modelMapper.map(userLoginVO,User.class);

        UserDTO dataUser = this.userService.loginUser(user);

        return ResponseEntity.ok(dataUser);
    }
}
