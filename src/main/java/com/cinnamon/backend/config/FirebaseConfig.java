package com.cinnamon.backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        InputStream serviceAccount;
        
        // Try to load from classpath resource first
        try {
            ClassPathResource resource = new ClassPathResource("serviceAccountKey.json");
            serviceAccount = resource.getInputStream();
        } catch (IOException e) {
            // Fallback to environment variable
            String serviceAccountJson = System.getenv("FIREBASE_SERVICE_ACCOUNT_JSON");
            if (serviceAccountJson == null || serviceAccountJson.isEmpty()) {
                throw new IllegalStateException("serviceAccountKey.json not found in resources and FIREBASE_SERVICE_ACCOUNT_JSON environment variable is not set");
            }
            serviceAccount = new ByteArrayInputStream(serviceAccountJson.getBytes(StandardCharsets.UTF_8));
        }

        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();

        return FirebaseApp.initializeApp(options);
    }
}