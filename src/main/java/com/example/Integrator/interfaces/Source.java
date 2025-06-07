package com.example.Integrator.interfaces;

import com.example.Integrator.IntegratorException;

/**
 * Интерфейс представляет источник информации.
 *
 * @param <T> тип данных, которые извлекаются из источника.
 * @author Ю.Д.Заковряшин, 2025
 */
public interface Source<T> {

    /**
     * Метод возвращает строковое описание источника.
     *
     * @return строковое описание источника.
     */
    public String getTitle();

    /**
     * Метод возвращает всё содержание источника в исходном формате.
     *
     * @return полное содержимое источника.
     * @throws IntegratorException выбрасывается в случае невозможности получить содержание источника.
     */
    public Data<T> getContent() throws IntegratorException;

    /**
     * Метод возвращает содержимое источника, преобразованное с помощью объекта converter.
     *
     * @param converter задаёт ссылку на объект, который будет преобразовывать данные из исходного формата T в формат
     *                 U.
     * @throws IntegratorException выбрасывается в случае невозможности получить содержание источника.
     */
    public <U> Data<U> getContent(Converter<T, U> converter) throws IntegratorException;
}

