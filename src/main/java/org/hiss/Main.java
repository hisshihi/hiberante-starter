package org.hiss;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hiss.entity.Birthday;
import org.hiss.entity.PersonalInfo;
import org.hiss.entity.Role;
import org.hiss.entity.User;
import org.hiss.util.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

@Slf4j
public class Main {

    public static void main(String[] args) {

        User user = User.builder()
                .username("hiss@gmail.com")
                .personalInfo(PersonalInfo.builder()
                        .lastName("Hiss")
                        .firstName("Dev")
                        .birthDate(new Birthday(LocalDate.of(2002, 10, 18)))
                        .build())
                .role(Role.USER)
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