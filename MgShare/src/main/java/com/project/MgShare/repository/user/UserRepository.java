package com.project.MgShare.repository.user;

import com.project.MgShare.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserEmail(String userEmail); //Find Email
    boolean existsUserEntitiesByUserEmail(String userEmail); //Same Email Check
}
