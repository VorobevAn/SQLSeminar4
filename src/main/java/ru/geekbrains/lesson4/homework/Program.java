package ru.geekbrains.lesson4.homework;

import ru.geekbrains.lesson4.models.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Задание
 * =======
 * Создайте базу данных (например, SchoolDB).
 * В этой базе данных создайте таблицу Courses с полями id (ключ), title, и duration.
 * Настройте Hibernate для работы с вашей базой данных.
 * Создайте Java-класс Course, соответствующий таблице Courses, с необходимыми аннотациями Hibernate.
 * Используя Hibernate, напишите код для вставки, чтения, обновления и удаления данных в таблице Courses.
 * Убедитесь, что каждая операция выполняется в отдельной транзакции.
 */

public class Program {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3305/";
        String user = "root";
        String password = "admin";

        try {
            // Подключение к базе данных
            Connection connection = DriverManager.getConnection(url, user, password);

            // Создание базы данных
            createDatabase(connection);
            System.out.println("Database created successfully");

            // Использование базы данных
            useDatabase(connection);
            System.out.println("Use database successfully");

            // Создание таблицы
            createTable(connection);
            System.out.println("Create table successfully");

            // Добавление данных
            List<Course> courseList = new ArrayList<>();
            Course course1 = new Course("Spring", 20);
            Course course2 = new Course("SQL", 5);
            Course course3 = new Course("Java", 45);
            courseList.add(course1);
            courseList.add(course2);
            courseList.add(course3);
            for (var course : courseList) {
                insertData(connection, course);
            }
            System.out.println("Insert data successfully");

            // Чтение данных
            Collection<Course> courses = readData(connection);
            for (var course : courses)
                System.out.println(course);
            System.out.println("Read data successfully");

            // Обновление
            for (var course : courses)
                if (course.getNameCourse().equals("SQL")){
                    course.updateCourse("Piton", 40);
                    updateData(connection, course);
                }
            System.out.println("Update data successfully");


            // Чтение данных
            courses = readData(connection);
            for (var cours : courses)
                System.out.println(cours);
            System.out.println("Read data successfully");

            // Удаление данных
            for (var cours : courses)
                deleteData(connection, cours.getId());
            System.out.println("Delete data successfully");

            // Закрытие соединения
            connection.close();
            System.out.println("Database connection close successfully");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    // Создание базы данных
    private static void createDatabase(Connection connection) throws SQLException {
        String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS SchoolDB;";
        try (PreparedStatement statement = connection.prepareStatement(createDatabaseSQL)) {
            statement.execute();
        }
    }

    //    Подключиться к базе данных
    private static void useDatabase(Connection connection) throws SQLException {
        String useDatabaseSQL = "USE SchoolDB;";
        try (PreparedStatement statement = connection.prepareStatement(useDatabaseSQL)) {
            statement.execute();
        }
    }

    // Создание таблицы
    private static void createTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS courses (id INT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255), duration INT);";
        try (PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
            statement.execute();
        }
    }

    //    Добавить данные в таблицу
    private static void insertData(Connection connection, Course course) throws SQLException {
        String insertDataSQL = "INSERT INTO courses (title, duration) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(insertDataSQL)) {
            statement.setString(1, course.getNameCourse());
            statement.setInt(2, course.getDuration());
            statement.executeUpdate();
        }
    }

    //    чтение данных из таблицы
    private static Collection<Course> readData(Connection connection) throws SQLException {
        ArrayList<Course> coursesList = new ArrayList<>();
        String readDataSQL = "SELECT * FROM courses;";
        try (PreparedStatement statement = connection.prepareStatement(readDataSQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nameCourse = resultSet.getString("title");
                int duration = resultSet.getInt("duration");
                coursesList.add(new Course(id, nameCourse, duration));
            }
            return coursesList;
        }
    }

    //    Обновление данных в таблице
    private static void updateData(Connection connection, Course course) throws SQLException {
        String updateDataSQL = "UPDATE courses SET duration=?, title=? WHERE id=?;";
        try (PreparedStatement statement = connection.prepareStatement(updateDataSQL)) {
            statement.setInt(1, course.getDuration());
            statement.setString(2, course.getNameCourse());
            statement.setInt(3, course.getId());
            statement.executeUpdate();
        }
    }

    //    Удаление из таблицы
    private static void deleteData(Connection connection, int id) throws SQLException {
        String deleteDataSQL = "DELETE FROM courses WHERE id=?;";
        try (PreparedStatement statement = connection.prepareStatement(deleteDataSQL)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }
}
