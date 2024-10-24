package org.hiss;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hiss.converter.BirthdayConverter;
import org.hiss.entity.Birthday;
import org.hiss.entity.Role;
import org.hiss.entity.User;
import org.hiss.util.HibernateUtil;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        User user = User.builder()
                .username("hiss@gmail.com")
                .lastname("Dev")
                .firstname("Hiss")
                .build();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session1 = sessionFactory.openSession()) {
                session1.beginTransaction();

                session1.saveOrUpdate(user);

                session1.getTransaction().commit();
            }
            try (Session session2 = sessionFactory.openSession()) {
                session2.beginTransaction();

                user.setFirstname("rehab");
//                session2.delete(user);
                session2.refresh(user);

                session2.getTransaction().commit();
            }
        }
    }
}