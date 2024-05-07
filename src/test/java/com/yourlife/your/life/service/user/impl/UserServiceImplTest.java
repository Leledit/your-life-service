package com.yourlife.your.life.service.user.impl;

import com.yourlife.your.life.model.dto.user.UserDTO;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.repository.user.UserRepository;
import com.yourlife.your.life.utils.TokenUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
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
    private User userMockRequest;

    @BeforeEach
    public void setUp(){
        userMock = new User();
        userMock.setId("6621b1c02c3dbe50ac7d6319");
        userMock.setEmail("test@teste.com.br");
        userMock.setName("leandro");
        userMock.setPassword("$2a$10$QTwffyaudYllyk9kD54Z3Oy.jbzDHPFCWl0pCswXBRUeWHmYzeQXS");

        userMockRequest = new User();
        userMockRequest.setEmail("test@teste.com.br");
        userMockRequest.setName("leandro");
        userMockRequest.setPassword("12345");
    }

    @Test
    public void testLoginUser_Success() {
        // Arrange
        when(userRepository.findFirstByEmail("test@teste.com.br")).thenReturn(Optional.of(userMock));
        when(tokenUtils.generateToken(userMock.getId())).thenReturn("testToken");
        when(modelMapper.map(any(UserDTO.class), eq(User.class))).thenReturn(userMock);

        // Act
        UserDTO loginUser = userService.loginUser(userMockRequest);

        // Assert
        assertNotNull(loginUser);
        assertEquals("testToken", loginUser.getToken());
    }


    @Test
   // @DisplayName("Meu test")
    void createUser() {

    }


}