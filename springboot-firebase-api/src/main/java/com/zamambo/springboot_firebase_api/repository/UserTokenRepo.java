package com.zamambo.springboot_firebase_api.repository;

import com.zamambo.springboot_firebase_api.models.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepo  extends JpaRepository<UserToken, String> {
    UserToken findByUserId(String userId);
}
