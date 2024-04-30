package com.yourlife.your.life.service.user;

import com.yourlife.your.life.model.dto.user.UserDTO;
import com.yourlife.your.life.model.entity.user.User;

public interface UserService {

    UserDTO createUser(User user);

    UserDTO loginUser(User user);

}
