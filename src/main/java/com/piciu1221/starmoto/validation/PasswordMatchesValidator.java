package com.piciu1221.starmoto.validation;

import com.piciu1221.starmoto.dto.RegistrationRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        RegistrationRequestDTO registrationRequestDTO = (RegistrationRequestDTO) value;
        return registrationRequestDTO.getPassword().equals(registrationRequestDTO.getMatchingPassword());
    }
}
