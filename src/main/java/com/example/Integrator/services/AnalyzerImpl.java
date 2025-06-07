package com.example.Integrator.services;

import com.example.Integrator.IntegratorException;
import com.example.Integrator.interfaces.Analyzer;
import com.example.Integrator.interfaces.Converter;
import com.example.Integrator.interfaces.Data;
import com.example.Integrator.interfaces.Source;
import com.example.Integrator.models.DataImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

public class AnalyzerImpl<T> implements Analyzer<T> {

    Converter<T, String> stringConverter = new ConverterImpl<>(Object::toString);
    Converter<String, T> TConverter = new ConverterImpl<>(s -> (T) s);

    private final Set<String> targets;

    public AnalyzerImpl(Set<String> targets) {
        this.targets = targets.stream().map(String::toLowerCase).collect(Collectors.toSet());
    }

    @Override
    public Data<T> analyze(Source<T> source) throws IntegratorException {
        Data<T> datacontent = source.getContent();
        String content = datacontent.getContent(stringConverter);
        Data<String> res = runSemanticAnalysis(content);
        return new DataImpl<>(TConverter.convert(res.getContent()));
    }

    @Override
    public <U> Data<U> analyze(Source<T> source, Converter<T, U> converter) throws IntegratorException {
        return null;
    }

    @Override
    public Data<T> analyze(Data<T> data) throws IntegratorException {
        String content = data.getContent(stringConverter);
        Data<String> res = runSemanticAnalysis(content);
        return new DataImpl<>(TConverter.convert(res.getContent()));
    }

    @Override
    public <U> Data<U> analyze(Data<T> data, Converter<T, U> converter) throws IntegratorException {
        Data<T> analyzed = analyze(data);
        return new DataImpl<>(converter.convert(analyzed.getContent()));
    }
    
    private Data<String> runSemanticAnalysis(String text) throws IntegratorException {
        if (text == null || text.trim().isEmpty()) {
            throw new IntegratorException("Входной текст отсутствует.");
        }

        String prompt = buildPrompt(text);

        String aiResult = sendPromptToAI(prompt);

        if (aiResult == null || aiResult.isBlank()) {
            throw new IntegratorException("Анализатор ИИ не вернул результат.");
        }

        return new DataImpl<>(aiResult.trim());
    }

    private String buildPrompt(String text) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Проанализируй следующий текст и для каждого из указанных параметров (targets) выдели соответствующую информацию.\n\n");
        prompt.append("Текст:\n").append(text).append("\n\n");
        prompt.append("Параметры анализа (targets):\n");
        for (String target : targets) {
            prompt.append("- ").append(target).append("\n");
        }
        prompt.append("\nВерни результат в виде:\n<target>: <найденная информация>\n");
        return prompt.toString();
    }

    protected String sendPromptToAI(String prompt) {
        String apiKey = "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";
        String model = "deepseek/deepseek-r1-0528-qwen3-8b:free";

        try {
            HttpClient client = HttpClient.newHttpClient();
            ObjectMapper mapper = new ObjectMapper();

            String requestBody = mapper.writeValueAsString(Map.of(
                    "model", model,
                    "messages", new Object[]{
                            Map.of("role", "system", "content", "Ты анализатор текста."),
                            Map.of("role", "user", "content", prompt)
                    }
            ));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://openrouter.ai/api/v1/chat/completions"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .header("HTTP-Referer", "http://localhost:8080")
                    .header("X-Title", "MyApp")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.err.println("DeepSeek API ошибка: " + response.body());
                return null;
            }

            JsonNode json = mapper.readTree(response.body());
            return json.get("choices").get(0).get("message").get("content").asText();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

    }
}
