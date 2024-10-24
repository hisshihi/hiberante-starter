package org.hiss;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hiss.converter.BirthdayConverter;
import org.hiss.entity.Birthday;
import org.hiss.entity.Role;
import org.hiss.entity.User;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        // Конвертация для дня рождения
        configuration.addAttributeConverter(new BirthdayConverter());
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            // Создаём сущность
//            User user = User.builder()
//                    .username("hiss5@gmail.com")
//                    .firstname("Hiss")
//                    .birthDate(new Birthday(LocalDate.of(2002, 10, 18)))
//                    .lastname("Dev")
//                    .role(Role.ADMIN)
//                    .build();
            // Сохраняем её
//            session.delete(user);
            User user1 = session.get(User.class, "hiss9@gmail.com");
            User user2 = session.get(User.class, "hiss9@gmail.com");
            user1.setLastname("hihi");

//            Уадление объекта из кеша
//            session.evict(user1);
            // Удаление всех объектов из кеша
//            session.clear();
            // Закрытие сессии и удаление кеша
//            session.close();

            //Чтобы отправлять запросы в бд, нужно делать коммит вручную
            session.getTransaction().commit();
        }
    }
}