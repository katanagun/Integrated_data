package com.example.Integrator.services;

import com.example.Integrator.IntegratorException;
import com.example.Integrator.interfaces.Converter;

public class ConverterImpl<T, U> implements Converter<T, U> {
    private final java.util.function.Function<T, U> conversionFunction;

    public ConverterImpl(java.util.function.Function<T, U> conversionFunction) {
        this.conversionFunction = conversionFunction;
    }

    @Override
    public U convert(T data) throws IntegratorException {
        try {
            return conversionFunction.apply(data);
        } catch (Exception e) {
            throw new IntegratorException("Ошибка преобразования данных: " + e.getMessage(), e);
        }
    }
}
