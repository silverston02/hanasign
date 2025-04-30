package com.hanasign.project.service;

import com.hanasign.project.dto.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO getUserById(Long userID);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(Long userID, UserDTO userDTO);
    void deleteUser(Long userID);
}
