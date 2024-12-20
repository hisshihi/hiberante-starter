package org.hiss;

import lombok.Cleanup;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.QueryHints;
import org.hiss.entity.*;
import org.hiss.util.HibernateTestUtil;
import org.hiss.util.HibernateUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static jakarta.persistence.FlushModeType.AUTO;
import static jakarta.persistence.FlushModeType.COMMIT;
import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

class MainTest {

    @Test
    void checkHql() {
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

//            select u.* from users u where u.personalInfo.firstName = 'Hiss'
            List<User> query = session.createNamedQuery(
                            "findUserByName", User.class
                    )
                    .setParameter("firstName", "Hiss")
                    .setParameter("companyName", "Google")
                    .setFlushMode(COMMIT)
                    .setHint(HINT_FETCH_SIZE, "50")
                    .list();

            int countRows = session.createQuery("update User u set u.role = 'ADMIN'")
                    .executeUpdate();

            session.createNativeQuery("select u.* from users where u.firstName = 'Hiss'", User.class);

            session.getTransaction().commit();

        }
    }

    @Test
    void checkH2() {
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Company company = Company.builder()
                    .name("Amazon")
                    .build();
            session.persist(company);

            Programmer programmer = Programmer.builder()
                    .username("hiss@gmail.com")
                    .language(Language.Java)
                    .companyId(company)
                    .build();
            session.persist(programmer);

            Manager manager = Manager.builder()
                    .username("arina@gmail.com")
                    .projectName("AWS")
                    .companyId(company)
                    .build();
            session.persist(manager);
            session.flush();

            session.clear();

            Programmer programmer1 = session.get(Programmer.class, 1L);
            User manager1 = session.get(User.class, 2L);
            System.out.println();

            session.getTransaction().commit();

        }
    }

    @Test
    void localeInfo() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Company company = session.get(Company.class, 1);
//            company.getLocales().add(LocaleInfo.of("ru", "Описание на русском языке"));
//            company.getLocales().add(LocaleInfo.of("en", "English description"));
            long start = System.currentTimeMillis();
            company.getUsers().forEach((k, v) -> System.out.println(v));
            long end = System.currentTimeMillis();
            System.out.println(end - start);

            session.getTransaction().commit();

        }
    }

    @Test
    void checkManyToMany() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, 6L);
            Chat chat = session.get(Chat.class, 3);

//            UserChat userChat = UserChat.builder()
//                    .createdAt(Instant.now())
//                    .createdBy(user.getUsername())
//                    .build();
//            userChat.setUser(user);
//            userChat.setChat(chat);
//
//            session.persist(userChat);

//            user.getChats().clear();

//            Chat chat = Chat.builder()
//                    .name("hiss")
//                    .build();
//
//            user.addChat(chat);
//
//            session.persist(chat);

            session.getTransaction().commit();

        }
    }

    @Test
    void checkOneToOne() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, 6L);
            System.out.println();

//            User user = User.builder()
//                    .username("test1@gmail.com")
//                    .build();
//            Profile profile = Profile.builder()
//                    .language("ru")
//                    .street("Gogolya 128")
//                    .build();
//
//            profile.setUser(user);
//            session.persist(user);

            session.getTransaction().commit();
        }
    }

    @Test
    void checkOrhanRemoval() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            // Получение объекта как прокси
            Company company = session.getReference(Company.class, 1);
//            company.getUsers().removeIf(user -> user.getId().equals(1L));

            session.getTransaction().commit();
        }
    }

    @Test
    void checkLazyInitialisation() {
        Company company = null;
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            // Получение объекта как прокси
            company = session.getReference(Company.class, 1);

            session.getTransaction().commit();
        }
//        Set<User> users = company.getUsers();
//        System.out.println(users.size());
    }

    @Test
    void deleteCompany() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Company newCompany = Company.builder()
                .id(4)
                .name("Amazon")
                .build();

        System.out.println(newCompany);
        Company company = session.get(Company.class, newCompany.getId());
        session.remove(company);

        session.getTransaction().commit();

    }

    @Test
    void addUserToNewCompany() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Company company = Company.builder()
                .name("Google")
                .build();

//        User user = User.builder()
//                .username("arina@gmail.com")
//                .build();
//
//        company.addUser(user);

        session.persist(company);

        session.getTransaction().commit();
    }

    @Test
    void oneToMany() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Company company = session.get(Company.class, 1);
        Hibernate.initialize(company.getUsers());
        System.out.println(company.getUsers());

        session.getTransaction().commit();
    }


    //    @SneakyThrows
    @Test
    void checkReflectionApi() {
//        User user = User.builder()
//                .build();
//
//        String sql = """
//                insert
//                into
//                %s
//                (%s)
//                values
//                (%s)
//                """;
//        // Динамическое оплучение таблицы
//        String table = ofNullable(user.getClass().getAnnotation(Table.class))
//                .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
//                .orElse(user.getClass().getName());
//        // Получем колонки в таблице
//        /*
//         * Получем филды нашей таблицы
//         *   далее если они сущетсвуют получаем из аннотации Column все названия
//         *   если их нет, то просто получаем названия филдов
//         * И соединыяем их разделяя запятой
//         * */
//        Field[] declaredFields = user.getClass().getDeclaredFields();
//
//        String columnNames = Arrays.stream(declaredFields)
//                .map(field -> ofNullable(field.getAnnotation(Column.class))
//                        .map(Column::name)
//                        .orElse(field.getName()))
//                .collect(joining(", "));
//
//        // Преобразовывем все филды в знаки вопроса и джойним их
//        String columnValues = Arrays.stream(declaredFields)
//                .map(field -> "?")
//                .collect(joining(", "));
//
//        System.out.println(sql.formatted(table, columnNames, columnValues));
//
//        // Создаём запрос
//        // Объявление переменной для подключения к базе данных
//        Connection connection = null;
//        // Создание подготовленного выражения (PreparedStatement) с использованием SQL-запроса
//        // sql.formatted(table, columnNames, columnValues) генерирует SQL-запрос с подстановкой значений
//        PreparedStatement preparedStatement = connection.prepareStatement(sql.formatted(table, columnNames, columnValues));
//        // Цикл по всем полям, объявленным в классе, для установки значений в подготовленное выражение
//        for (Field declaredField : declaredFields) {
//            // Устанавливаем доступ к полям класса, даже если они приватные
//            declaredField.setAccessible(true);
//            // Получаем значение текущего поля у объекта user и устанавливаем его в подготовленное выражение
//            // Здесь предполагается, что мы устанавливаем значения по порядку в PreparedStatement
//            preparedStatement.setObject(1, declaredField.get(user));
//        }
    }

}