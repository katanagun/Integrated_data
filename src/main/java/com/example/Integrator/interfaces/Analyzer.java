package com.example.Integrator.interfaces;

import com.example.Integrator.IntegratorException;

/**
 * Интерфейс определяет методы анализа выбранного источника данных.
 *
 * @param <T> тип данных, которые должны анализироваться.
 * @author Ю.Д.Заковряшин, 2025
 */
public interface Analyzer<T> {
    /**
     * Метод выполняет анализ источника данных без преобразования исходного типа данных.
     *
     * @param source источник данных для анализа.
     * @return возвращается результат анализа в исходном формате источника данных.
     * @throws IntegratorException выбрасывается в случае невозможности выполнить анализ данных.
     */
    public Data<T> analyze(Source<T> source) throws IntegratorException;

    /**
     * Метод выполняет анализ источника данных с преобразованием исходного типа данных.
     *
     * @param <U>       формат данных, в котором должен быть представлен результат анализа.
     * @param source    источник данных для анализа.
     * @param converter ссылка на объект, который используется для преобразования данных из исходного формата
     *                  T в формат U.
     * @return результат анализа данных источника в формате U.
     * @throws IntegratorException выбрасывается в случае невозможности выполнить анализ данных.
     */
    public <U> Data<U> analyze(Source<T> source, Converter<T, U> converter) throws IntegratorException;


    /**
     * Метод выполняет анализ заданного исходного набора данных.
     *
     * @param data исходный набор данных для анализа.
     * @return результат анализа исходного набора данных в формате исходного набора данных.
     * @throws IntegratorException выбрасывается в случае невозможности выполнить анализ данных.
     */
    public Data<T> analyze(Data<T> data) throws IntegratorException;


    /**
     * Метод выполняет анализ заданного исходного набора данных с преобразованием исходного типа данных.
     *
     * @param <U>       формат данных, в котором должен быть представлен результат анализа.
     * @param data      исходный набор данных для анализа.
     * @param converter ссылка на объект, который используется для преобразования данных из исходного формата
     *                  T в формат U.
     * @return результат анализа исходного набора данных в формате U.
     * @throws IntegratorException выбрасывается в случае невозможности выполнить анализ данных.
     */
    public <U> Data<U> analyze(Data<T> data, Converter<T, U> converter) throws IntegratorException;

}

