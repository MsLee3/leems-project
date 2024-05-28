package com.project.MgShare.dto.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UserInfoDTOTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void whenAllFieldsAreValid_thenNoConstraintViolations() {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUsername("Test User");
        userInfoDTO.setUserEmail("testuser@mirineglobal.com");
        userInfoDTO.setPhoneNumber("1234567890");
        userInfoDTO.setCurrentPassword("currentPassword");
        userInfoDTO.setPassword("newPassword");

        Set<ConstraintViolation<UserInfoDTO>> violations = validator.validate(userInfoDTO);

        assertThat(violations).isEmpty();
    }

    @Test
    public void whenUsernameIsBlank_thenNoConstraintViolation() {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUsername("");
        userInfoDTO.setUserEmail("testuser@mirineglobal.com");
        userInfoDTO.setPhoneNumber("1234567890");
        userInfoDTO.setCurrentPassword("currentPassword");
        userInfoDTO.setPassword("newPassword");

        Set<ConstraintViolation<UserInfoDTO>> violations = validator.validate(userInfoDTO);

        assertThat(violations).isEmpty();
    }

    @Test
    public void whenEmailIsInvalid_thenOneConstraintViolation() {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUsername("Test User");
        userInfoDTO.setUserEmail("invalid-email");
        userInfoDTO.setPhoneNumber("1234567890");
        userInfoDTO.setCurrentPassword("currentPassword");
        userInfoDTO.setPassword("newPassword");

        Set<ConstraintViolation<UserInfoDTO>> violations = validator.validate(userInfoDTO);

        assertThat(violations).hasSize(1);
        ConstraintViolation<UserInfoDTO> violation = violations.iterator().next();
        assertThat(violation.getMessage()).isEqualTo("must be a well-formed email address");
    }

    @Test
    public void whenEmailIsBlank_thenNoConstraintViolation() {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUsername("Test User");
        userInfoDTO.setUserEmail("");
        userInfoDTO.setPhoneNumber("1234567890");
        userInfoDTO.setCurrentPassword("currentPassword");
        userInfoDTO.setPassword("newPassword");

        Set<ConstraintViolation<UserInfoDTO>> violations = validator.validate(userInfoDTO);

        assertThat(violations).isEmpty();
    }

    @Test
    public void whenPasswordIsBlank_thenNoConstraintViolation() {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUsername("Test User");
        userInfoDTO.setUserEmail("testuser@mirineglobal.com");
        userInfoDTO.setPhoneNumber("1234567890");
        userInfoDTO.setCurrentPassword("currentPassword");
        userInfoDTO.setPassword("");

        Set<ConstraintViolation<UserInfoDTO>> violations = validator.validate(userInfoDTO);

        assertThat(violations).isEmpty();
    }

    @Test
    public void whenPhoneNumberIsInvalid_thenNoConstraintViolation() {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUsername("Test User");
        userInfoDTO.setUserEmail("testuser@mirineglobal.com");
        userInfoDTO.setPhoneNumber("invalid-phone-number");
        userInfoDTO.setCurrentPassword("currentPassword");
        userInfoDTO.setPassword("newPassword");

        Set<ConstraintViolation<UserInfoDTO>> violations = validator.validate(userInfoDTO);

        assertThat(violations).isEmpty();
    }
}
