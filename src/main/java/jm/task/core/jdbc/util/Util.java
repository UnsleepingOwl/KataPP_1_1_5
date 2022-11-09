package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.*;
import java.util.Properties;

public class Util {
    private static final String MY_URL = "jdbc:mysql://localhost:3306/mydbtest",
            MY_USER = "root",
            MY_PASS = "root",
            MY_DRIVER = "com.mysql.cj.jdbc.Driver",
            MY_DIALECT = "org.hibernate.dialect.MySQL8Dialect",
            MY_SHOW_SQL = "true",
            MY_CURRENT_SESSION_CONTEXT_CLASS = "thread";
    private static Connection connection;
    private static SessionFactory sessionFactory;

    private static class ConnectionHolder {
        public static Util UTIL_CONNECTION =
                new Util(MY_URL, MY_USER, MY_PASS);
    }
    private static class SessionFactoryHolder {
        public static Util UTIL_SESSION_FACTORY =
                new Util(MY_DRIVER, MY_URL, MY_USER, MY_PASS, MY_DIALECT, MY_SHOW_SQL, MY_CURRENT_SESSION_CONTEXT_CLASS);
    }

    private Util(String MY_URL, String MY_USER, String MY_PASS) {
        try {
            connection = DriverManager.getConnection(MY_URL, MY_USER, MY_PASS);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Util(String MY_DRIVER, String MY_URL, String MY_USER, String MY_PASS, String MY_DIALECT, String MY_SHOW_SQL, String MY_CURRENT_SESSION_CONTEXT_CLASS) {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, MY_DRIVER);
                settings.put(Environment.URL, MY_URL);
                settings.put(Environment.USER, MY_USER);
                settings.put(Environment.PASS, MY_PASS);
                settings.put(Environment.DIALECT, MY_DIALECT);
                settings.put(Environment.SHOW_SQL, MY_SHOW_SQL);
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, MY_CURRENT_SESSION_CONTEXT_CLASS);

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Util getConnectionUtil() {
        return ConnectionHolder.UTIL_CONNECTION;
    }

    public static Util getSessionFactorUtil() {
        return SessionFactoryHolder.UTIL_SESSION_FACTORY;
    }

    // Соединение через JDBC
    public Connection getConnection() {
        return connection;
    }


    //Соединение через Hibernate
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

