package com.project.MgShare.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Data
public class UserInfoDTO {

    private String username;

    @Email(message = "must be a well-formed email address")
    private String userEmail;

    private String phoneNumber;

    private String currentPassword;

    private String password;


}
