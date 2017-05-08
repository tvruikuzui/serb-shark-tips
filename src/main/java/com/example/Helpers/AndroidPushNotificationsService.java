package com.example.Helpers;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
 * Created by User on 30/04/2017.
 */

@Service
public class AndroidPushNotificationsService {
    private static final String FIREBASE_SERVER_KEY = "AAAAVmsva0Q:APA91bFo_M3OAyyYwZHz7zxNtvQiYgv4cSwekeEReZfLzSwTpV5kKN2EgaODrDnb3FF43cURW5yKyQCGWVhp2rF3EmnNfMVfz2tdU9kGZ6XfMnUpvDZyOdniWVMPXbUtmhIFD0bTd3of";

    @Async
    public CompletableFuture<FirebaseResponse> send(HttpEntity<String> entity) {

        RestTemplate restTemplate = new RestTemplate();

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);

        FirebaseResponse firebaseResponse = restTemplate.postForObject("https://fcm.googleapis.com/fcm/send", entity, FirebaseResponse.class);

        return CompletableFuture.completedFuture(firebaseResponse);
    }
}
