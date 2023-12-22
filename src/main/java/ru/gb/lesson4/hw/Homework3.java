package ru.gb.lesson4.hw;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Homework3 {
    public static void main(String[] args) {
        final SessionFactory sessionFactory = new Configuration()
                .configure("hibernate2.cfg.xml").buildSessionFactory();

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.createQuery("""
                insert into Author(author)
                values('Лев Толстой'),
                      ('Жюль Верн'),
                      ('Александр Дюма'),
                      ('Анджей Сапковский'),
                      ('Михаил Лермонтов'),
                      ('Гарри Гаррисон'),
                      ('Джоан Роулинг')""").executeUpdate();

            session.createNativeQuery("""
                insert into Book(name, author_id)
                values('Война и мир', 1),
                      ('Капитан Немо', 2),
                      ('Три мушкетера', 3),
                      ('Граф Монте-Кристо', 3),
                      ('Дети капитана Гранта', 2),
                      ('Ведьмак', 4),
                      ('Бородино', 5),
                      ('Стальная крыса', 6),
                      ('Таинственный остров', 2),
                      ('Гарри Поттер', 7)""", Book2.class).executeUpdate();

            session.getTransaction().commit();
        }


        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Book2> books = session.createQuery("select b from Book2 b", Book2.class).getResultList();
            System.out.println(books);

            List<Author> authors = session.createQuery("select a from Author a", Author.class).getResultList();
            for (Author author :authors) {
                System.out.println(author.getBooks());
            }
        }

        sessionFactory.close();
    }

}
