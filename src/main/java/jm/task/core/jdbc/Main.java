package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Создание 4 User(ов)
        System.out.println("\n|Main| Создание 4 User'ов\n");
        User firstUser = new User("D-9341", "Disposable", (byte) 30);
        User secondUser = new User("D-9215", "Disposable", (byte) 24);
        User thirdUser = new User("D-1126", "Disposable", (byte) 20);
        User fourthUser = new User("D-9117", "Disposable", (byte) 19);

        UserServiceImpl userService = new UserServiceImpl();

        //Создание таблицы User(ов)
        System.out.println("\n|Main| Создание таблицы User'ов\n");
        userService.createUsersTable();

        // Добавление 4 User(ов) в таблицу с данными на свой выбор
        // После каждого добавления должен быть вывод в консоль (User с именем – name добавлен в базу данных )
        // User #1
        userService.saveUser(firstUser.getName(), firstUser.getLastName(), firstUser.getAge());
        System.out.println("|Main| User с именем" + " - " + firstUser.getName() + " добавлен в базу данных");
        // User #2
        userService.saveUser(secondUser.getName(), secondUser.getLastName(), secondUser.getAge());
        System.out.println("|Main| User с именем" + " - " + secondUser.getName() + " добавлен в базу данных");
        // User #3
        userService.saveUser(thirdUser.getName(), thirdUser.getLastName(), thirdUser.getAge());
        System.out.println("|Main| User с именем" + " - " + thirdUser.getName() + " добавлен в базу данных");
        // User #4
        userService.saveUser(fourthUser.getName(), fourthUser.getLastName(), fourthUser.getAge());
        System.out.println("|Main| User с именем" + " - " + fourthUser.getName() + " добавлен в базу данных");

        // Получение всех User(ов) из базы и вывод в консоль
        System.out.println("\n|Main| Получение всех User'ов из базы и вывод в консоль\n");
        List<User> resultList = userService.getAllUsers();
        for (User user : resultList) {
            System.out.println(user.toString());
        }

        // Очистка таблицы User(ов)
        userService.cleanUsersTable();

        // Удаление таблицы
        userService.dropUsersTable();

    }
}

