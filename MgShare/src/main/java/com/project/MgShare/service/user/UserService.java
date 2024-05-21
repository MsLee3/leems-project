package com.project.MgShare.service.user;

import com.project.MgShare.dto.user.RegisterDTO;
import com.project.MgShare.model.user.UserEntity;
import com.project.MgShare.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void UserRegisterSave(RegisterDTO registerDTO) {

        boolean isUserExist = userRepository.existsUserEntitiesByUserEmail(registerDTO.getUserEmail());

        if (!registerDTO.getUsername().matches("^[^\\d]*$")) {
            throw new IllegalArgumentException("Invalid name");
        }

        if (!registerDTO.getPhoneNumber().matches("^[0-9]+$")) {
            throw new IllegalArgumentException("Invalid phone number");
        }

        if (!registerDTO.getUserEmail().endsWith("@mirineglobal.com")) {
            throw new IllegalArgumentException("Invalid email domain");
        }

        if(isUserExist) {
            throw new IllegalArgumentException("Email already exists");
        }

        System.out.println(registerDTO.getUsername());
        System.out.println(registerDTO.getPassword());
        System.out.println(registerDTO.getUserEmail());
        UserEntity data = new UserEntity();

        data.setUsername(registerDTO.getUsername());
        data.setPhoneNumber(registerDTO.getPhoneNumber());
        data.setUserEmail(registerDTO.getUserEmail());
        data.setPassword(bCryptPasswordEncoder.encode(registerDTO.getPassword()));
        data.setRole("ROLE_USER"); //権限設定

        userRepository.save(data);
    }


}
