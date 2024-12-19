package com.zamambo.springboot_firebase_api.services;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.zamambo.springboot_firebase_api.models.UserToken;
import com.zamambo.springboot_firebase_api.repository.UserTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirebaseMessagingService {

    @Autowired
    private UserTokenRepo userTokenRepository;

    public String sendMessageToUser(String userId, String title, String body) {
        // Retrieve the token for the user
        UserToken userToken = userTokenRepository.findByUserId(userId);

        if (userToken == null || userToken.getFcmToken() == null) {
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
            return "Message sent: " + response;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send message";
        }
    }
}
