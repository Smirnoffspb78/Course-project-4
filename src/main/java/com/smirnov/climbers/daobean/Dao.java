package com.smirnov.climbers.daobean;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import static com.smirnov.climbers.ValidateObjects.validate;
/**
 * Базовый класс для методов Dao объектов.
 */
@Getter
public abstract class Dao<T, E> {
    /**
     * Наименование Entity Manager для подключения к базе данных.
     */
    @NotBlank
    private final String nameEntityManager;

    /**
     * Конструктор задает базовые параметры для Dao классов
     * @param nameEntityManager Имя Entity Manager.
     */
    protected Dao(@NotBlank String nameEntityManager) {
        validate(nameEntityManager);
        this.nameEntityManager = nameEntityManager;
    }

    /**
     * Извлекает объекты из БД.
     * @param t идентификатор из БД.
     * @return Объект.
     */
    abstract E findById(T t);

    /**
     * Добавляет объекты в БД по идентификатору.
     * @param e Идентификатор
     * @return Объект
     */
    abstract T insert(E e);
}
