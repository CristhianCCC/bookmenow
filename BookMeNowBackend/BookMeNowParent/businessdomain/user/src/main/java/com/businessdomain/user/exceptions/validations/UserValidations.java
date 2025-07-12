package com.businessdomain.user.exceptions.validations;

import com.businessdomain.user.dto.AuthRequest;
import com.businessdomain.user.dto.UserDTO;
import com.businessdomain.user.exceptions.exceptions.BusinessRuleException;
import com.businessdomain.user.repository.UserRepository;
import org.springframework.http.HttpStatus;

public class UserValidations {

    public static void validate(UserDTO dto, boolean isNew, UserRepository repo) throws BusinessRuleException {
        if (dto == null) {
            throw new BusinessRuleException("2001", "User can't be empty", HttpStatus.BAD_REQUEST);
        }

        if (dto.getName() == null || dto.getLastName() == null || dto.getEmail() == null ||
                dto.getPassword() == null || dto.getAddress() == null || dto.getRole() == null) {
            throw new BusinessRuleException("2002", "All fields are required", HttpStatus.PARTIAL_CONTENT);
        }

        if (isNew) {
            if (repo.existsByEmail(dto.getEmail())) {
                throw new BusinessRuleException("2003", "The email " + dto.getEmail() + " is already in use", HttpStatus.CONFLICT);
            }
        } else {
            // Validar que el ID no sea nulo SOLO al editar
            if (dto.getId() == null) {
                throw new BusinessRuleException("2000", "User ID is required for update", HttpStatus.BAD_REQUEST);
            }

            repo.findByEmail(dto.getEmail()).ifPresent(existingUser -> {
                if (!existingUser.getId().equals(dto.getId())) {
                    throw new BusinessRuleException("2003", "The email " + dto.getEmail() + " is already in use by another user", HttpStatus.CONFLICT);
                }
            });
        }

        // Si tu enum UserRole solo permite CLIENT/PROVIDER, puedes validar así (opcional)
        /*
        try {
            dto.getRole().name(); // asegúrate que es un enum válido
        } catch (Exception e) {
            throw new BusinessRuleException("2004", "Invalid role", HttpStatus.BAD_REQUEST);
        }
        */
    }

    public static void validateCredentials(AuthRequest dto) throws BusinessRuleException {
        if (dto == null) {
            throw new BusinessRuleException("2005", "Credentials cannot be empty", HttpStatus.BAD_REQUEST);
        }
    }
}
