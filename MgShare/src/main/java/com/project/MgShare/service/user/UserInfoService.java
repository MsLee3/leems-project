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
public class UserInfoService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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

    public boolean confirmCurrentPassword(String currentPassword) { //confirm curren PW
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            String userEmail = authentication.getName();
            UserEntity userEntity = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new IllegalArgumentException("User not found"));

            return bCryptPasswordEncoder.matches(currentPassword, userEntity.getPassword());
        }
        return false;
    }


    public void updateUserInfo(UserInfoDTO userInfoDTO) { //userの情報更新
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            String userEmail = authentication.getName();
            UserEntity userEntity = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new IllegalArgumentException("User not found"));

            userEntity.setUsername(userInfoDTO.getUsername());
            userEntity.setPhoneNumber(userInfoDTO.getPhoneNumber());
            userEntity.setUserEmail(userInfoDTO.getUserEmail());

            if (userInfoDTO.getPassword() != null && !userInfoDTO.getPassword().isEmpty()) {
                userEntity.setPassword(bCryptPasswordEncoder.encode(userInfoDTO.getPassword()));
            }

            userRepository.save(userEntity);
        }
    }

    public void deleteUser() { //userの情報削除
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            String userEmail = authentication.getName();
            UserEntity userEntity = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new IllegalArgumentException("User not found"));
            userRepository.delete(userEntity);
        }
    }



    private UserInfoDTO toUserInfoDTO(UserEntity userEntity) { //Entity -> DTO　変換
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUsername(userEntity.getUsername());
        userInfoDTO.setUserEmail(userEntity.getUserEmail());
        userInfoDTO.setPhoneNumber(userEntity.getPhoneNumber());
        return userInfoDTO;
    }

}
