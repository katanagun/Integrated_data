package com.example.Integrator.services;

import com.example.Integrator.IntegratorException;
import com.example.Integrator.interfaces.Data;
import com.example.Integrator.interfaces.Reporter;
import com.example.Integrator.models.DataImpl;

import java.util.ArrayList;
import java.util.List;

public class ReporterImpl<T> implements Reporter<T> {

    private final List<T> reportContent = new ArrayList<>();

    @Override
    public boolean add(Data<T> content) throws IntegratorException {
        T data = content.getContent();
        if (data == null) {
            throw new IntegratorException("Невозможно добавить пустой набор данных.");
        }

        return reportContent.add(data);
    }

    @Override
    public Data<T> getReport() throws IntegratorException {
        if (reportContent.isEmpty()) {
            throw new IntegratorException("Итоговый отчёт пуст.");
        }

        if (reportContent.get(0) instanceof String) {
            String combined = String.join("\n\n", (List<String>) reportContent);
            return (Data<T>) new DataImpl<>(combined);
        } else {
            return new DataImpl<>(reportContent.get(0));
        }
    }
}
