package com.project.MgShare.dto.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class RegisterDTOTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void whenAllFieldsAreValid_thenNoConstraintViolations() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("Test User");
        registerDTO.setUserEmail("testuser@mirineglobal.com");
        registerDTO.setPhoneNumber("1234567890");
        registerDTO.setPassword("password");

        Set<ConstraintViolation<RegisterDTO>> violations = validator.validate(registerDTO);

        assertThat(violations).isEmpty();
    }

    @Test
    public void whenUsernameIsBlank_thenOneConstraintViolation() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("");
        registerDTO.setUserEmail("testuser@mirineglobal.com");
        registerDTO.setPhoneNumber("1234567890");
        registerDTO.setPassword("password");

        Set<ConstraintViolation<RegisterDTO>> violations = validator.validate(registerDTO);

        assertThat(violations).hasSize(1);
        ConstraintViolation<RegisterDTO> violation = violations.iterator().next();
        assertThat(violation.getMessage()).isEqualTo("Name is mandatory");
    }

    @Test
    public void whenEmailIsInvalid_thenOneConstraintViolation() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("Test User");
        registerDTO.setUserEmail("invalid-email");
        registerDTO.setPhoneNumber("1234567890");
        registerDTO.setPassword("password");

        Set<ConstraintViolation<RegisterDTO>> violations = validator.validate(registerDTO);

        assertThat(violations).hasSize(1);
        ConstraintViolation<RegisterDTO> violation = violations.iterator().next();
        assertThat(violation.getMessage()).isEqualTo("Email should be valid");
    }

    @Test
    public void whenEmailIsBlank_thenOneConstraintViolation() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("Test User");
        registerDTO.setUserEmail("");
        registerDTO.setPhoneNumber("1234567890");
        registerDTO.setPassword("password");

        Set<ConstraintViolation<RegisterDTO>> violations = validator.validate(registerDTO);

        assertThat(violations).hasSize(1);
        ConstraintViolation<RegisterDTO> violation = violations.iterator().next();
        assertThat(violation.getMessage()).isEqualTo("Email is mandatory");
    }

    @Test
    public void whenPasswordIsBlank_thenOneConstraintViolation() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("Test User");
        registerDTO.setUserEmail("testuser@mirineglobal.com");
        registerDTO.setPhoneNumber("1234567890");
        registerDTO.setPassword("");

        Set<ConstraintViolation<RegisterDTO>> violations = validator.validate(registerDTO);

        assertThat(violations).hasSize(1);
        ConstraintViolation<RegisterDTO> violation = violations.iterator().next();
        assertThat(violation.getMessage()).isEqualTo("Password is mandatory");
    }

    @Test
    public void whenPhoneNumberIsInvalid_thenNoConstraintViolation() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("Test User");
        registerDTO.setUserEmail("testuser@mirineglobal.com");
        registerDTO.setPhoneNumber("invalid-phone-number");
        registerDTO.setPassword("password");

        Set<ConstraintViolation<RegisterDTO>> violations = validator.validate(registerDTO);

        assertThat(violations).isEmpty();
    }
}
