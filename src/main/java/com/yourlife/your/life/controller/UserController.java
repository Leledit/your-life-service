package com.yourlife.your.life.controller;

import com.yourlife.your.life.model.dto.user.UserDTO;
import com.yourlife.your.life.model.vo.user.UserPostRequestVO;
import com.yourlife.your.life.service.user.UserService;
import jakarta.validation.Valid;
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

    @PostMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<UserDTO> createAnAccount(@RequestBody @Valid UserPostRequestVO userPostRequestVO){

        UserDTO dataUser = this.userService.createUser(userPostRequestVO);

        return ResponseEntity.status(HttpStatus.CREATED).body(dataUser);
    }

    @GetMapping(value = "/user",produces = MediaType.APPLICATION_JSON_VALUE)
    public void login(){
        System.out.println("Entrei na rota de login");
    }
}
