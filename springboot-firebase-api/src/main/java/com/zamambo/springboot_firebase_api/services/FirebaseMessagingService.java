package com.zamambo.springboot_firebase_api.services;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.zamambo.springboot_firebase_api.models.UserToken;
import com.zamambo.springboot_firebase_api.repository.UserTokenRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirebaseMessagingService {
    private static final Logger LOG = LoggerFactory.getLogger(FirebaseMessagingService.class);
    @Autowired
    private UserTokenRepo userTokenRepository;

    public String sendMessageToUser(long userId, String title, String body) {
        // Retrieve the token for the user
        UserToken userToken = userTokenRepository.findByUserId(userId);

        if (userToken == null || userToken.getFcmToken() == null) {
            LOG.warn("User not registered for notifications, user: {}", userId);
            return "User not registered for notifications";
        }

        String token = userToken.getFcmToken();

        // Create the message
        Message message = Message.builder()
                .setToken(token)
                .putData("title", title)
                .putData("body", body)
                .build();

        try {
            // Send the message
            String response = FirebaseMessaging.getInstance().send(message);
            LOG.info("Message sent: {}", response);
            return "Message sent: " + response;
        } catch (Exception e) {
            //e.printStackTrace();
            LOG.error("Failed to send message, exception -> {}", e.getMessage());
            return "Failed to send message";
        }
    }
}
