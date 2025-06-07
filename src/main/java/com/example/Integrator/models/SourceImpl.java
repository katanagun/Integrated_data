package com.example.Integrator.models;

import com.example.Integrator.IntegratorException;
import com.example.Integrator.interfaces.Converter;
import com.example.Integrator.interfaces.Data;
import com.example.Integrator.interfaces.Source;

public class SourceImpl<T> implements Source<T> {
    private final String title;
    private final Data<T> content;

    public SourceImpl(String title, Data<T> content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Data<T> getContent() throws IntegratorException {
        if (content == null) {
            throw new IntegratorException("Содержание источника отсутствует.");
        }
        return content;
    }

    @Override
    public <U> Data<U> getContent(Converter<T, U> converter) throws IntegratorException {
        if (content == null) {
            throw new IntegratorException("Содержание источника отсутствует.");
        }
        if (converter == null) {
            throw new IntegratorException("Конвертер не задан.");
        }

        U convertedData = converter.convert(content.getContent());

        return new DataImpl<>(convertedData);
    }
}
