package com.project.MgShare.dto.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;


@Setter
@Getter
public class RegisterDTO {

    @NotBlank(message = "Name is mandatory")
    private String username;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String userEmail;

    @NumberFormat
    private String phoneNumber;

    @NotBlank(message = "Password is mandatory")
    //@Size(min = 6, message = "Password should be at least 6 characters")
    private String password;

}
