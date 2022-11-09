package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static Util util = Util.getConnectionUtil();
    Connection connection = util.getConnection();

    // Удаление таблицы User(ов) – не должно приводить к исключению, если таблицы не существует
    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            try {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS USERS (" +
                        "id BIGINT NOT NULL AUTO_INCREMENT," +
                        "name VARCHAR(64)," +
                        "lastName VARCHAR(64)," +
                        "age SMALLINT," +
                        "PRIMARY KEY ( id ) )"
                );
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Удаление таблицы User(ов) – не должно приводить к исключению, если таблицы не существует
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            try {
                statement.executeUpdate("DROP TABLE IF EXISTS users");
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Добавление User в таблицу
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement prepStat = connection.prepareStatement("INSERT INTO users (name, lastName, age) VALUES(?, ?, ?)")) {
            try {
                prepStat.setString(1, name);
                prepStat.setString(2, lastName);
                prepStat.setByte(3, age);
                prepStat.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Удаление User из таблицы ( по id )
    public void removeUserById(long id) {
        try (PreparedStatement prepStat = connection.prepareStatement("DELETE FROM users where id = ?")) {
            try {
                prepStat.setLong(1, id);
                prepStat.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Получение всех User(ов) из таблицы
    public List<User> getAllUsers() {
        List<User> userList = new LinkedList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setName(resultSet.getString("name"));
                    user.setLastName(resultSet.getString("lastName"));
                    user.setAge(resultSet.getByte("age"));
                    userList.add(user);
                }
            connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return userList;
    }

    // Очистка содержания таблицы
    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            try {
                statement.executeUpdate("TRUNCATE users");
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
