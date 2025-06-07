package com.example.Integrator.services;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SearchService {
    private static final String SEARCH_URL = "https://yandex.ru/search/?text=";

    public static String sendSearchRequest(String query) throws IOException {
        String url = SEARCH_URL + URLEncoder.encode(query, StandardCharsets.UTF_8.name());
        OkHttpClient client = new OkHttpClient.Builder()
                .followRedirects(true)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/103.0.0.0 Safari/537.36")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .header("Accept-Language", "en-US,en;q=0.9")
                .header("Referer", "https://yandex.ru/")
                .header("Connection", "keep-alive")
                .header("Upgrade-Insecure-Requests", "1")
                .build();


        try (Response response = client.newCall(request).execute()) {
            return response.body() != null ? response.body().string() : "";
        }
    }
}
