package com.example.Integrator.services;

import java.util.Map;

/**
 * Класс для формирования поискового запроса на основе модели данных.
 */
public class QueryGenerator {
    public static String generateSearchQuery(Map<String, String> modelData) {
        String object = modelData.getOrDefault("object", "");
        String additions = modelData.getOrDefault("additions", "");
        String targets = modelData.getOrDefault("targets", "");

        return String.format("%s %s %s", object, additions, targets)
                .replace(",", "")
                .trim()
                .replaceAll("\\s+", " ");
    }
}
