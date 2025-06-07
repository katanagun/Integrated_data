package com.example.Integrator.interfaces;

import com.example.Integrator.IntegratorException;

/**
 * Интерфейс определяет метод преобразования наборов данных из одного формата в другой.
 *
 * @param <T> - исходный тип данных.
 * @param <U> - тип данных, в который преобразуется исходные данные.
 * @author Ю.Д.Заковряшин, 2025
 */
@FunctionalInterface
public interface Converter<T, U> {
    /**
     * Метод преобразования данных из исходного формата T в формат U/
     * @param data исходные данные для преобразования.
     * @return преобразованные данные в формате U.
     * @throws IntegratorException выбрасывается в случае невозможности выполнить преобразование.
     */
    U convert (T data) throws IntegratorException;
}
