package org.hiss.util;

import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;

/*
* Поднятие бд
* */
@UtilityClass
public class HibernateTestUtil {

//    Создаём объект postgres container и выбираем версию
    public static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17");

//    Будет один раз вызван postgres(создан)
    static {
        postgres.start();
    }

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = HibernateUtil.buildConfiguration();
        configuration.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        configuration.setProperty("hibernate.connection.username", postgres.getUsername());
        configuration.setProperty("hibernate.connection.password", postgres.getPassword());
        configuration.configure();

        return configuration.buildSessionFactory();
    }

}
