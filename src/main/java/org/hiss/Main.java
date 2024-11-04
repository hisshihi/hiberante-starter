package org.hiss;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hiss.entity.*;
import org.hiss.util.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

@Slf4j
public class Main {

    public static void main(String[] args) {

        Company company = Company.builder()
                .name("Amazon")
                .build();

//        User user = User.builder()
//                .username("hiss@gmail.com")
//                .personalInfo(PersonalInfo.builder()
//                        .lastName("Hiss")
//                        .firstName("Dev")
//                        .birthDate(new Birthday(LocalDate.of(2002, 10, 18)))
//                        .build())
//                .role(Role.USER)
//                .companyId(company)
//                .build();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            try (session) {
                Transaction transaction = session.beginTransaction();

//                session.persist(user);

                session.getTransaction().commit();
            }
        }
    }
}