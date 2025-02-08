package com.yourlife.your.life.controller.user;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.user.UserDTO;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.model.dto.user.UserLoginDTO;
import com.yourlife.your.life.model.dto.user.UserRegistertVO;
import com.yourlife.your.life.service.user.UserService;
import com.yourlife.your.life.utils.TokenUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DisplayName("User")
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private TokenUtils tokenUtils;

    @InjectMocks
    private UserController userController;

    private User userMock;

    private UserDTO userDTOMock;
    @BeforeEach
    public void setUp() {
        userMock = new User();
        userMock.setName("leandro");
        userMock.setPassword("1234");
        userMock.setEmail("test@teste.com.br");

        userDTOMock = new UserDTO();
        userDTOMock.setName("leandro");
        userDTOMock.setId("6621b1c02c3dbe50ac7d6319");
    }

    @Test
    @DisplayName("create - Creating new record successfully!")
    void testCreateAnAccount() throws Exception{
        UserRegistertVO userRegistertVOMock = new UserRegistertVO();
        userRegistertVOMock.setName("leandro");
        userRegistertVOMock.setPassword("1234");
        userRegistertVOMock.setEmail("test@teste.com.br");

        when(modelMapper.map(any(UserRegistertVO.class),eq(User.class))).thenReturn(userMock);
        when(userService.createUser(userMock)).thenReturn(userMock);
        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(userDTOMock);
        when(tokenUtils.generateToken(userDTOMock.getId())).thenReturn("testToken");

        ResponseEntity<UserDTO> userDTOResponseEntity = userController.createAnAccount(userRegistertVOMock);

        assertEquals(HttpStatus.CREATED, userDTOResponseEntity.getStatusCode());
        assertEquals(userDTOMock,userDTOResponseEntity.getBody());
    }

    @Test
    @DisplayName("create - Causing an exception when creating a record with invalid data!")
    void testCreateAnAccount_InvalidInput() throws Exception{
        UserRegistertVO userRegistertVOMock = new UserRegistertVO();

        assertThrows(RuntimeException.class, () -> userController.createAnAccount(userRegistertVOMock), ExceptionMessages.INVALID_REQUEST_COMPONENT);
    }

    @Test
    @DisplayName("login - logging into the application")
    void testLogin() {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail("test@teste.com.br");
        userLoginDTO.setPassword("1234");

        when(modelMapper.map(any(UserLoginDTO.class),eq(User.class))).thenReturn(userMock);
        when(userService.loginUser(userMock)).thenReturn(userMock);
        when(modelMapper.map(any(User.class),eq(UserDTO.class))).thenReturn(userDTOMock);
        when(tokenUtils.generateToken(userDTOMock.getId())).thenReturn("testToken");

        ResponseEntity<UserDTO> userDTOResponseEntity = userController.login(userLoginDTO);

        assertEquals(HttpStatus.OK, userDTOResponseEntity.getStatusCode());
        assertEquals(userDTOMock,userDTOResponseEntity.getBody());
    }
}