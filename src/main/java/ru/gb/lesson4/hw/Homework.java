package ru.gb.lesson4.hw;

import java.sql.*;

public class Homework {

  /**
   * Задания необходимо выполнять на ЛЮБОЙ СУБД (postgres, mysql, sqllite, h2, ...)
   *
   * 1. С помощью JDBC выполнить:
   * 1.1 Создать таблицу book с колонками id bigint, name varchar, author varchar, ...
   * 1.2 Добавить в таблицу 10 книг
   * 1.3 Сделать запрос select from book where author = 'какое-то имя' и прочитать его с помощью ResultSet
   *
   * 2. С помощью JPA(Hibernate) выполнить:
   * 2.1 Описать сущность Book из пункта 1.1
   * 2.2 Создать Session и сохранить в таблицу 10 книг
   * 2.3 Выгрузить список книг какого-то автора
   *
   * 3.* Создать сущность Автор (id biging, name varchar), и в сущности Book сделать поле типа Author (OneToOne)
   * 3.1 * Выгрузить Список книг и убедиться, что поле author заполнено
   * 3.2 ** В классе Author создать поле List<Book>, которое описывает список всех книг этого автора. (OneToMany)
   */

  /**
   * ДЗ для преподавателя:
   * 1. Разобраться с jdbc:h2:file
   * 2. Подготовить расказ про PreparedStatement и SqlInjection
   */

  public static void main(String[] args) {
      String url = "jdbc:mysql://localhost:3306/java_junior?useSSL=false&serverTimezone=UTC";
      String user = "root";
      String password = "12345";

//      try{
//          Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
//          System.out.println("Connection succesfull!");
//      }
//      catch(Exception ex){
//          System.out.println("Connection failed...");
//
//          System.out.println(ex);
//      }

      try {
          Connection connection = DriverManager.getConnection(url, user, password);
          System.out.println("Подключение к базе данных успешно установлено!");
          prepareTables(connection);
          insertData(connection);
          executeSelect(connection);
          connection.close();
      } catch (SQLException e) {
          System.out.println("Ошибка при подключении к базе данных:");
          e.printStackTrace();
      }
  }

    private static void prepareTables(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("drop table if exists book");
            statement.execute("""   
          create table book (
          id bigint PRIMARY KEY AUTO_INCREMENT,
          name varchar(255),
          author varchar(255)
        )
        """);
        }
    }
    private static void insertData(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            int updatedRows = statement.executeUpdate("""
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
                      ('Гарри Поттер', 'Джоан Роулинг')""");
            System.out.println(updatedRows);
        }
    }

    private static void executeSelect(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from book where author = 'Жюль Верн'");
            int counter = 0;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String author = resultSet.getString("author");

                System.out.println("[" + counter++ + "] id = " + id + ", name = " + name + ", author = " + author);
            }
        }
    }


}
