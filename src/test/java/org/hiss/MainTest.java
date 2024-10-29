package org.hiss;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hiss.entity.Company;
import org.hiss.entity.User;
import org.hiss.util.HibernateUtil;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

class MainTest {

    @Test
    void oneToMany() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Company company = session.get(Company.class, 1);
        System.out.println("");

        session.getTransaction().commit();
    }


    @SneakyThrows
    @Test
    void checkReflectionApi() {
        User user = User.builder()
                .build();

        String sql = """
                insert
                into
                %s
                (%s)
                values
                (%s)
                """;
        // Динамическое оплучение таблицы
        String table = ofNullable(user.getClass().getAnnotation(Table.class))
                .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
                .orElse(user.getClass().getName());
        // Получем колонки в таблице
        /*
         * Получем филды нашей таблицы
         *   далее если они сущетсвуют получаем из аннотации Column все названия
         *   если их нет, то просто получаем названия филдов
         * И соединыяем их разделяя запятой
         * */
        Field[] declaredFields = user.getClass().getDeclaredFields();

        String columnNames = Arrays.stream(declaredFields)
                .map(field -> ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name)
                        .orElse(field.getName()))
                .collect(joining(", "));

        // Преобразовывем все филды в знаки вопроса и джойним их
        String columnValues = Arrays.stream(declaredFields)
                .map(field -> "?")
                .collect(joining(", "));

        System.out.println(sql.formatted(table, columnNames, columnValues));

        // Создаём запрос
        // Объявление переменной для подключения к базе данных
        Connection connection = null;
        // Создание подготовленного выражения (PreparedStatement) с использованием SQL-запроса
        // sql.formatted(table, columnNames, columnValues) генерирует SQL-запрос с подстановкой значений
        PreparedStatement preparedStatement = connection.prepareStatement(sql.formatted(table, columnNames, columnValues));
        // Цикл по всем полям, объявленным в классе, для установки значений в подготовленное выражение
        for (Field declaredField : declaredFields) {
            // Устанавливаем доступ к полям класса, даже если они приватные
            declaredField.setAccessible(true);
            // Получаем значение текущего поля у объекта user и устанавливаем его в подготовленное выражение
            // Здесь предполагается, что мы устанавливаем значения по порядку в PreparedStatement
            preparedStatement.setObject(1, declaredField.get(user));
        }
    }

}