package com.example.Integrator.services;

import com.example.Integrator.IntegratorException;
import com.example.Integrator.interfaces.Preprocessor;
import com.example.Integrator.interfaces.Source;


import java.util.ArrayList;
import java.util.List;

public class PreprocessorImpl<T> implements Preprocessor {

    List<Source<T>> sources = new ArrayList<>();

    private int limit;

    public PreprocessorImpl(int limit) {
        this.limit = limit;
    }

    @Override
    public int getLimit() {
        return limit;
    }

    @Override
    public void setLimit(int limit) {
        if (limit > 0) {
            this.limit = limit;
        } else {
            throw new IllegalArgumentException("Ограничение должно быть положительным числом.");
        }
    }

    @Override
    public <T> List<Source<T>> getSources(Source<T> source) throws IntegratorException {
        return null;
    }

    public void addSourceToList(Source<T> source){
        sources.add(source);
    }

    public List<Source<T>> getListSources() {
        return sources;
    }
}
