package com.example.Integrator.services;

import com.example.Integrator.IntegratorException;
import com.example.Integrator.interfaces.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TaskImpl<K, V> implements Task<K, V> {

    private final Map<K, V> properties = new HashMap<>();

    @Override
    public void readTask(File file) throws IntegratorException {

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Пропуск пустых строк и комментариев
                if (line.isEmpty() || line.startsWith("#") || line.startsWith("//")) {
                    continue;
                }

                String[] parts = line.split("=", 2);
                if (parts.length != 2) {
                    throw new IntegratorException("Некорректная строка в конфигурации: " + line);
                }

                try {
                    K key = (K) parts[0].trim();
                    V value = (V) parts[1].trim();
                    this.properties.put(key, value);
                } catch (ClassCastException e) {
                    throw new IntegratorException("Ошибка преобразования ключа или значения: " + line, e);
                }
            }
        } catch (IOException e) {
            throw new IntegratorException("Ошибка чтения файла: " + file.getName(), e);
        }

    }

    @Override
    public Map<K, V> getProperties() throws IntegratorException {
        return properties;
    }

    @Override
    public V getProperty(K key) throws IntegratorException {
        if (!properties.containsKey(key)) {
            throw new IntegratorException("Ключ не найден: " + key);
        }
        return properties.get(key);
    }
}
