package com.yourlife.your.life.service.user;

import com.yourlife.your.life.model.dto.user.UserDTO;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.model.vo.user.UserPostRequestVO;

public interface UserService {

    UserDTO createUser(UserPostRequestVO userPostRequestVO);

    UserDTO findByLogin(User user);

}
