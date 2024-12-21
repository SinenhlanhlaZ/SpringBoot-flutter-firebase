package com.zamambo.springboot_firebase_api;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    FirebaseMessaging FirebaseConfig() throws IOException {
        /*FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }*/

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource("springbootflutterfirebase-firebase-adminsdk-7hjlh-635c0c9ad5.json").getInputStream());
        FirebaseOptions firebaseOptions = FirebaseOptions.builder().setCredentials(googleCredentials).build();
        FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions);
        return FirebaseMessaging.getInstance(app);

    }
}

