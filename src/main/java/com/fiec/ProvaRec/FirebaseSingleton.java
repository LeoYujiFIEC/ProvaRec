package com.fiec.ProvaRec;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseSingleton {

    private static FirebaseSingleton instance = null;

    private FirebaseSingleton() {
        try {
            // abaixo está downloads, mas, você pode colocar em outro ponto
            String filePath = System.getenv("HOMEPATH") + "Documents/Java/Javafiec2024-projeto.json";
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(
                            new FileInputStream(filePath)))
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            System.out.println("Erro ao inicializar o Firebase: " + e.getMessage());
        }
    }

    public static FirebaseSingleton getInstance() {
        if (instance == null) {
            instance = new FirebaseSingleton();
        }
        return instance;
    }
}