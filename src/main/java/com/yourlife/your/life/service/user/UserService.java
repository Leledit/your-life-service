package com.yourlife.your.life.service.user;

import com.yourlife.your.life.model.dto.user.UserDTO;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.model.vo.user.UserLoginRequestVO;
import com.yourlife.your.life.model.vo.user.UserRegistertRequestVO;

public interface UserService {

    UserDTO createUser(UserRegistertRequestVO userPostRequestVO);

    UserDTO findByLogin(User user);

    UserDTO loginUser(UserLoginRequestVO userLoginRequestVO);

}
