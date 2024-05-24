package com.project.MgShare.service.user;

import com.project.MgShare.model.user.UserEntity;
import com.project.MgShare.repository.user.UserRepository;
import com.project.MgShare.Role.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override //login method
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        Optional<UserEntity> _userEntity = this.userRepository.findByUserEmail(userEmail);

        if(_userEntity.isEmpty()) {
            throw new UsernameNotFoundException("no user");
        }

        UserEntity userEntity = _userEntity.get();
        List<GrantedAuthority> authorities = new ArrayList<>();

        if("admin".equals(userEmail)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        }
        else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }

        return new User(userEntity.getUserEmail(), userEntity.getPassword(), authorities);
    }
}
