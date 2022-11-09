package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final Util CONNECTION_UTIL = Util.getConnectionUtil();
    private static final Connection CONNECTION = CONNECTION_UTIL.getConnection();

    public UserDaoJDBCImpl() {
    }

    // Удаление таблицы User(ов) – не должно приводить к исключению, если таблицы не существует
    public void createUsersTable() {
        try (Statement statement = CONNECTION.createStatement()) {
            try {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS USERS (" +
                        "id BIGINT NOT NULL AUTO_INCREMENT," +
                        "name VARCHAR(64)," +
                        "lastName VARCHAR(64)," +
                        "age SMALLINT," +
                        "PRIMARY KEY ( id ) )");
                CONNECTION.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                CONNECTION.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Удаление таблицы User(ов) – не должно приводить к исключению, если таблицы не существует
    public void dropUsersTable() {
        try (Statement statement = CONNECTION.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            CONNECTION.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    // Добавление User в таблицу
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement prepStat = CONNECTION.prepareStatement("INSERT INTO users (name, lastName, age) VALUES(?, ?, ?)")) {
            prepStat.setString(1, name);
            prepStat.setString(2, lastName);
            prepStat.setByte(3, age);
            prepStat.executeUpdate();
            CONNECTION.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    // Удаление User из таблицы ( по id )
    public void removeUserById(long id) {
        try (PreparedStatement prepStat = CONNECTION.prepareStatement("DELETE FROM users where id = ?")) {

            prepStat.setLong(1, id);
            prepStat.executeUpdate();
            CONNECTION.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    // Получение всех User(ов) из таблицы
    public List<User> getAllUsers() {
        List<User> userList = new LinkedList<>();
        try (Statement statement = CONNECTION.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
            CONNECTION.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return userList;
    }

    // Очистка содержания таблицы
    public void cleanUsersTable() {
        try (Statement statement = CONNECTION.createStatement()) {
            statement.executeUpdate("TRUNCATE users");
            CONNECTION.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
