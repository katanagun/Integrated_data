package com.example.Integrator.models;

import com.example.Integrator.IntegratorException;
import com.example.Integrator.interfaces.Converter;
import com.example.Integrator.interfaces.Data;

import java.util.List;

public class DataImpl<T> implements Data<T> {
    private final T content;

    public DataImpl(T content) {
        this.content = content;
    }

    @Override
    public T getContent() throws IntegratorException {
        if (content == null) {
            throw new IntegratorException("Набор данных отсутствует.");
        }
        return content;
    }

    @Override
    public T getContent(int limit) throws IntegratorException {
        if (content == null) {
            throw new IntegratorException("Набор данных отсутствует.");
        }

        // Проверяем, является ли content списком и применяем ограничение
        if (content instanceof List<?>) {
            List<?> listContent = (List<?>) content;
            int safeLimit = Math.min(limit, listContent.size());
            return (T) listContent.subList(0, safeLimit);
        }

        return content;
    }

    @Override
    public <U> U getContent(Converter<T, U> converter, int limit) throws IntegratorException {
        if (content == null) {
            throw new IntegratorException("Набор данных отсутствует.");
        }
        if (converter == null) {
            throw new IntegratorException("Конвертер не задан.");
        }
        T limitedContent = getContent(limit);
        return converter.convert(content);
    }

    @Override
    public <U> U getContent(Converter<T, U> converter) throws IntegratorException {
        if (content == null) {
            throw new IntegratorException("Набор данных отсутствует.");
        }
        if (converter == null) {
            throw new IntegratorException("Конвертер не задан.");
        }

        return converter.convert(content);
    }
}
