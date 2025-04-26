package com.hanasign.project.service;



import com.hanasign.project.dto.UserDTO;
import com.hanasign.project.domain.User;
import com.hanasign.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private UserDTO toDTO(User user) {
        return new UserDTO(
                user.getUserID(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getUserPermission()
        );
    }

    private User toEntity(UserDTO dto) {
        return User.builder()
                .userID(dto.getUserID())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .userPermission(dto.getUserPermission())
                .build();
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        return toDTO(userRepository.save(toEntity(userDTO)));
    }

    @Override
    public UserDTO getUser(Long userID) {
        return userRepository.findById(userID).map(this::toDTO).orElse(null);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(Long userID, UserDTO dto) {
        User user = userRepository.findById(userID).orElseThrow();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setUserPermission(dto.getUserPermission());
        return toDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long userID) {
        userRepository.deleteById(userID);
    }
}
