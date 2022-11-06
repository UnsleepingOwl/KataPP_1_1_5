package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();
//    private UserDaoHibernateImpl userDao = new UserDaoHibernateImpl();
    public UserServiceImpl() {

    }


    // Создание таблицы для User(ов) – не должно приводить к исключению, если такая таблица уже существует
    public void createUsersTable() {
        userDao.createUsersTable();
    }

    // Удаление таблицы User(ов) – не должно приводить к исключению, если таблицы не существует
    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    // Добавление User в таблицу
    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
    }

    // Удаление User из таблицы ( по id )
    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }

    // Получение всех User(ов) из таблицы
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    // Очистка содержания таблицы
    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }
}
