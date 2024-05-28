package com.project.MgShare.model.user;

import com.project.MgShare.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserEntityTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        UserEntity user = new UserEntity();
        user.setUsername("Test User");
        user.setUserEmail("testuser@mirineglobal.com");
        user.setPassword("password");
        user.setPhoneNumber("1234567890");
        user.setRole("ROLE_USER");

        userRepository.save(user);

        Optional<UserEntity> foundUser = userRepository.findByUserEmail("testuser@mirineglobal.com");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("Test User");
    }

    @Test
    public void testFindUserByEmail() {
        UserEntity user = new UserEntity();
        user.setUsername("Test User");
        user.setUserEmail("testuser@mirineglobal.com");
        user.setPassword("password");
        user.setPhoneNumber("1234567890");
        user.setRole("ROLE_USER");

        userRepository.save(user);

        Optional<UserEntity> foundUser = userRepository.findByUserEmail("testuser@mirineglobal.com");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUserEmail()).isEqualTo("testuser@mirineglobal.com");
    }

    @Test
    public void testExistsUserByEmail() {
        UserEntity user = new UserEntity();
        user.setUsername("Test User");
        user.setUserEmail("testuser@mirineglobal.com");
        user.setPassword("password");
        user.setPhoneNumber("1234567890");
        user.setRole("ROLE_USER");

        userRepository.save(user);

        boolean exists = userRepository.existsUserEntitiesByUserEmail("testuser@mirineglobal.com");
        assertThat(exists).isTrue();
    }

    @Test
    public void testDeleteUser() {
        UserEntity user = new UserEntity();
        user.setUsername("Test User");
        user.setUserEmail("testuser@mirineglobal.com");
        user.setPassword("password");
        user.setPhoneNumber("1234567890");
        user.setRole("ROLE_USER");

        userRepository.save(user);

        Optional<UserEntity> foundUser = userRepository.findByUserEmail("testuser@mirineglobal.com");
        assertThat(foundUser).isPresent();

        userRepository.delete(foundUser.get());

        Optional<UserEntity> deletedUser = userRepository.findByUserEmail("testuser@mirineglobal.com");
        assertThat(deletedUser).isNotPresent();
    }
}
