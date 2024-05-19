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

    public void  FindUserByEmail(String email) {
        userRepository.findByEmail(email);
    }

    public void UserRegisterSave(RegisterDTO registerDTO) {

        boolean isUserExist = userRepository.existsUserEntitiesByEmail(registerDTO.getEmail());


        System.out.println(registerDTO.getUsername());
        System.out.println(registerDTO.getEmail());
        System.out.println(registerDTO.getPassword());

        if (isUserExist) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (registerDTO.getPassword() == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        UserEntity data = new UserEntity();

        data.setUsername(registerDTO.getUsername());
        data.setPhoneNumber(registerDTO.getPhoneNumber());
        data.setEmail(registerDTO.getEmail());
        data.setPassword(bCryptPasswordEncoder.encode(registerDTO.getPassword()));
        data.setRole("ROLE_USER"); //権限設定

        userRepository.save(data);
    }

}
