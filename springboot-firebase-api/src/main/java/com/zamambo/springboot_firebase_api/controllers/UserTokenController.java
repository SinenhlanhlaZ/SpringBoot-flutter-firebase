package com.zamambo.springboot_firebase_api.controllers;

import com.zamambo.springboot_firebase_api.models.UserToken;
import com.zamambo.springboot_firebase_api.repository.UserTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserTokenController {
    @Autowired
    private UserTokenRepo userTokenRepository;

    @PostMapping("/save-token")
    public String saveToken(@RequestParam long userId, @RequestParam String token) {
        UserToken userToken = new UserToken();
        userToken.setUserId(userId);
        userToken.setFcmToken(token);
        userTokenRepository.save(userToken);
        return "Token saved successfully";
    }
}
