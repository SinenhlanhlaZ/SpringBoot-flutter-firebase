package com.zamambo.springboot_firebase_api.controllers;

import com.zamambo.springboot_firebase_api.models.UserToken;
import com.zamambo.springboot_firebase_api.repository.UserTokenRepo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserTokenController {
    private static final Logger LOG = LoggerFactory.getLogger(UserTokenController.class);
    @Autowired
    private UserTokenRepo userTokenRepository;

    @PostMapping("/save-token")
    public String saveToken(@RequestParam String userGuid, @RequestParam String token) {
        try {
            LOG.info("user id: {}, token: {}", userGuid, token);
            UserToken userToken = new UserToken();
            userToken.setUserGuid(userGuid);
            userToken.setFcmToken(token);
            LOG.info("Saving token... {}", userToken);
            userTokenRepository.save(userToken);
            LOG.info("Token saved successfully");
            return "Token saved successfully";
        }catch (Exception e) {
            LOG.info("Exception -> {}", e.getMessage());
            return "Failed to store token";
        }
    }
}
