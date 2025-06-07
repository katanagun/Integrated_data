package com.example.Integrator.interfaces;


import com.example.Integrator.IntegratorException;

import java.util.List;

/**
 * Интерфейс определяет методы:
 * <ol>
 * <li>для преобразования параметров задачи на поиск в модель данных;</li>
 * <li>предварительного анализа источников данных на соответствии поставленной
 * задаче на поиск;</li>
 * <li>выбор источников данных для анализа.</li>
 * </ol>
 *
 * @author Ю.Д.Заковряшин, 2025
 */
public interface Preprocessor {

    /**
     * Метод возвращает установленное ограничение на длину списка источников.
     *
     * @return установленное ограничение на длину списка источников.
     */
    int getLimit();

    /**
     * Метод задаёт ограничение на длину списка источников.
     *
     * @param limit ограничение на длину списка источников.
     */
    void setLimit(int limit);

    /**
     * Метод формирует список источников для последующего анализа.
     *
     * @param <T> тип содержания источника.
     * @param source исходный источник.
     * @return список источников для последующего анализа.
     * @throws IntegratorException сключение выбрасывается в случае
     * невозможности сформировать список источников.
     */
    <T> List<Source<T>> getSources(Source<T> source) throws IntegratorException;
}

