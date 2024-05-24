package com.project.MgShare.dto.user;

import com.project.MgShare.model.user.UserEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Data
public class UserInfoDTO {

    private String username;

    private String userEmail;

    private String phoneNumber;

    private String currentPassword;

    private String password;


}
