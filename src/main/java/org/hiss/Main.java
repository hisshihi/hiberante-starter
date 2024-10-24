package org.hiss;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hiss.entity.User;
import org.hiss.util.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    // Создаём логгер
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        User user = User.builder()
                .username("hiss@gmail.com")
                .lastname("Dev")
                .firstname("Hiss")
                .build();

        log.info("User entity is in transient state, object {}", user);

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();
            try (session1) {
                Transaction transaction = session1.beginTransaction();
                log.trace("Transaction is created {}", transaction);

                session1.saveOrUpdate(user);
                log.trace("User is in persistent state: {}, session {}", user, transaction);

                session1.getTransaction().commit();
            }
            log.warn("User is in detached state: {}, session is closed {}", user, session1);
        } catch (Exception e) {
            log.error("Exception occurred,", e);
            throw e;
        }
    }
}