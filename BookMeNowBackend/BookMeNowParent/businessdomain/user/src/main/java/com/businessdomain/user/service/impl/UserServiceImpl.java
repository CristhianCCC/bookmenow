package com.businessdomain.user.service.impl;

import com.businessdomain.user.dto.UserDTO;
import com.businessdomain.user.exceptions.exceptions.BusinessRuleException;
import com.businessdomain.user.exceptions.validations.UserValidations;
import com.businessdomain.user.model.User;
import com.businessdomain.user.repository.UserRepository;
import com.businessdomain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Convert DTO to Entity
    private User toEntity(UserDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        user.setRole(dto.getRole());
        user.setAddress(dto.getAddress());
        return user;
    }

    // Convert Entity to DTO
    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        dto.setAddress(user.getAddress());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        return toDTO(user);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        try {
            // Validate with isNew = true
            UserValidations.validate(userDTO, true, userRepository);
        } catch (BusinessRuleException e) {
            throw e;
        }

        User user = toEntity(userDTO);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        return toDTO(savedUser);
    }

    @Override
    public UserDTO editUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id).map(existingUser -> {
            try {
                // Set the correct ID before validating
                userDTO.setId(id);
                // Validate with isNew = false
                UserValidations.validate(userDTO, false, userRepository);
            } catch (BusinessRuleException e) {
                throw e;
            }

            existingUser.setName(userDTO.getName());
            existingUser.setLastName(userDTO.getLastName());
            existingUser.setEmail(userDTO.getEmail());

            if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }

            existingUser.setAddress(userDTO.getAddress());
            existingUser.setRole(userDTO.getRole());
            existingUser.setUpdatedAt(LocalDateTime.now());

            userRepository.save(existingUser);
            return toDTO(existingUser);
        }).orElseThrow(() -> new RuntimeException("Unable to update user with ID: " + id));
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User with ID " + id + " was not found");
        }
        userRepository.deleteById(id);
    }
}
