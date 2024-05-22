package com.project.MgShare.dto.user;

import com.project.MgShare.model.user.UserEntity;
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


}
