package com.hanasign.project.service;

import com.hanasign.project.dto.UserDTO;
import com.hanasign.project.entity.User;
import com.hanasign.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        updateUserFields(existingUser, userDTO);
        User savedUser = userRepository.save(existingUser);
        return convertToDTO(savedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private User convertToEntity(UserDTO dto) {
        return User.builder()
                .userId(dto.getUserId())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .phoneNumber(dto.getPhoneNumber())
                .registrationNumber(dto.getRegistrationNumber())
                .companyName(dto.getCompanyName())
                .representativeName(dto.getRepresentativeName())
                .zipCode(dto.getZipCode())
                .address(dto.getAddress())
                .detailAddress(dto.getDetailAddress())
                .isBusiness(dto.isBusiness())
                .termsAgreed(dto.isTermsAgreed())
                .build();
    }

    private UserDTO convertToDTO(User entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .registrationNumber(entity.getRegistrationNumber())
                .companyName(entity.getCompanyName())
                .representativeName(entity.getRepresentativeName())
                .zipCode(entity.getZipCode())
                .address(entity.getAddress())
                .detailAddress(entity.getDetailAddress())
                .isBusiness(entity.isBusiness())
                .termsAgreed(entity.isTermsAgreed())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private void updateUserFields(User user, UserDTO dto) {
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setCompanyName(dto.getCompanyName());
        user.setRepresentativeName(dto.getRepresentativeName());
        user.setZipCode(dto.getZipCode());
        user.setAddress(dto.getAddress());
        user.setDetailAddress(dto.getDetailAddress());
        user.setBusiness(dto.isBusiness());
        user.setTermsAgreed(dto.isTermsAgreed());
    }
}
