package com.yourlife.your.life.service.user.impl;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.user.UserDTO;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.repository.user.UserRepository;
import com.yourlife.your.life.utils.TokenUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DisplayName("User")
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Mock
    private TokenUtils tokenUtils;

    @InjectMocks
    private UserServiceImpl userService;

    private User userMock;

    private UserDTO userDTO;

    @BeforeEach
    public void setUp(){
        userMock = new User();
        userMock.setId("6621b1c02c3dbe50ac7d6319");
        userMock.setEmail("test@teste.com.br");
        userMock.setName("leandro");
        userMock.setPassword("$2a$10$QTwffyaudYllyk9kD54Z3Oy.jbzDHPFCWl0pCswXBRUeWHmYzeQXS");

        userDTO = new UserDTO();
        userDTO.setToken("testToken");
        userDTO.setName("leandro");
        userDTO.setId("6621b1c02c3dbe50ac7d6319");
    }

    @Test
    @DisplayName("Login - whether the login is successful!")
    public void testLoginUser_Success() {
        when(userRepository.findFirstByEmail("test@teste.com.br")).thenReturn(Optional.of(userMock));
        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(userDTO);

        User userMockRequest = new User();
        userMockRequest.setEmail("test@teste.com.br");
        userMockRequest.setName("leandro");
        userMockRequest.setPassword("12345");

        User loginUser = userService.loginUser(userMockRequest);

        assertNotNull(loginUser);

    }

    @Test
    @DisplayName("Login - password fails when credentials are invalid")
    void testLoginUser_InvalidCredentials() {
        when(userRepository.findFirstByEmail("test@teste.com.br")).thenReturn(Optional.of(userMock));
        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(userDTO);

        User userMockRequest = new User();
        userMockRequest.setEmail("test@teste.com.br");
        userMockRequest.setName("leandro");
        userMockRequest.setPassword("1234");

        assertThrows(RuntimeException.class, () -> userService.loginUser(userMockRequest), ExceptionMessages.INVALID_CREDENTIALS);
    }

    @Test
    @DisplayName("create - creation of a new user")
    void testCreateUser_Success() {
        User userMockRequest = new User();
        userMockRequest.setEmail("test@teste.com.br");
        userMockRequest.setName("leandro");
        userMockRequest.setPassword("1234");

        when(userRepository.findFirstByEmail("test@teste.com.br")).thenReturn(Optional.empty());
        when(userRepository.save(userMockRequest)).thenReturn(userMock);
        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(userDTO);

        User createdUser = userService.createUser(userMockRequest);

        assertNotNull(createdUser);
    }

    @Test
    @DisplayName("create - Checking when the email has already been registered in the system")
    void testCreateUser_EmailAlreadyRegistered() {
        User userMockRequest = new User();
        userMockRequest.setEmail("test@teste.com.br");
        userMockRequest.setName("leandro");
        userMockRequest.setPassword("12345");

        when(userRepository.findFirstByEmail("test@teste.com.br")).thenReturn(Optional.of(userMock));

        assertThrows(RuntimeException.class, () -> userService.createUser(userMockRequest),ExceptionMessages.EMAIL_ALREADY_REGISTERED);
    }

}