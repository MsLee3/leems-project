package com.project.MgShare.repository.user;

import com.project.MgShare.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email); //Find Email
    boolean existsUserEntitiesByEmail(String email); //Same Email Check
}
