package com.zamambo.springboot_firebase_api.controllers;


import com.zamambo.springboot_firebase_api.services.FirebaseMessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class FirebaseMessagingController {
    @Autowired
    private FirebaseMessagingService firebaseMessagingService;

    @PostMapping("/send-to-user")
    public String sendMessageToUser(@RequestParam long userId, @RequestParam String title, @RequestParam String body) {
        return firebaseMessagingService.sendMessageToUser(userId, title, body);
    }
}
