package ru.gb.lesson4.hw;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.gb.lesson4.User;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Homework2 {
    public static void main(String[] args) {
        final SessionFactory sessionFactory = new Configuration()
                .configure("hibernate2.cfg.xml").buildSessionFactory();

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.createNativeQuery("""
                insert into book(name, author) 
                values('Война и мир', 'Лев Толстой'),
                  ('Капитан Немо', 'Жюль Верн'),
                  ('Три мушкетера', 'Александр Дюма'),
                  ('Граф Монте-Кристо', 'Александр Дюма'),
                  ('Дети капитана Гранта', 'Жюль Верн'),
                  ('Ведьмак', 'Анджей Сапковский'),
                  ('Бородино', 'Михаил Лермонтов'),
                  ('Стальная крыса', 'Гарри Гаррисон'),
                  ('Таинственный остров', 'Жюль Верн'),
                  ('Гарри Поттер', 'Джоан Роулинг')""", Book.class).executeUpdate();

            Book book = new Book("Сорок пять", "Александр Дюма");
            session.persist(book);

            session.getTransaction().commit();
        }


        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Book> books = session.createQuery("select b.name from Book b where author = 'Жюль Верн'", Book.class).getResultList();
            System.out.println(books);

        }

        sessionFactory.close();
    }

}
