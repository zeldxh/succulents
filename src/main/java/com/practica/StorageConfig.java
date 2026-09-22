package com.practica;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    // ruta absoluta a un archivo json de credenciales de firebase, fuera del repo
    @Value("${firebase.credentials.path}")
    private String credentialsPath;

    @Bean
    public Storage storage() throws IOException {
        try (InputStream entrada = new FileInputStream(credentialsPath)) {
            GoogleCredentials credenciales = GoogleCredentials.fromStream(entrada);
            return StorageOptions.newBuilder()
                    .setCredentials(credenciales)
                    .build()
                    .getService();
        }
    }
}
