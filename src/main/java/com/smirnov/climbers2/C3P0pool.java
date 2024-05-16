package com.smirnov.climbers2;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *Выполняет подключение к базе данных
 */
public class C3P0pool {
    private C3P0pool(){}

    /**
     * Пул соединений.
     */
    private static final ComboPooledDataSource pool=new ComboPooledDataSource();

    /**
     * Возвращает готовое соединение
     * @return соединение
     * @throws SQLException, если не удалось подключить к базе данных
     */
    public static Connection getConnection() throws SQLException {
        return pool.getConnection();
    }
}
