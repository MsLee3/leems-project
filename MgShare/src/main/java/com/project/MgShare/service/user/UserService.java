package com.project.MgShare.service.user;

import com.project.MgShare.dto.user.RegisterDTO;
import com.project.MgShare.dto.user.UserInfoDTO;
import com.project.MgShare.model.user.UserEntity;
import com.project.MgShare.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public UserInfoDTO getCurrentUser() { //MyPage USER 確認
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !((authentication).getPrincipal() instanceof String)) {

            String userEmail = authentication.getName();
            UserEntity userEntity = userRepository.findByUserEmail(userEmail).orElse(null);

            if (userEntity != null) {

                return toUserInfoDTO(userEntity);
            }
        }
        return null;
    }

    private UserInfoDTO toUserInfoDTO(UserEntity userEntity) { //Entity -> DTO　変換
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUsername(userEntity.getUsername());
        userInfoDTO.setUserEmail(userEntity.getUserEmail());
        userInfoDTO.setPhoneNumber(userEntity.getPhoneNumber());
        return userInfoDTO;
    }

}
