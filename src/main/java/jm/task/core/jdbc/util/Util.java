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

    // Вынос параметров в строки для улучшения читаемости кода
    private static final String MY_URL = "jdbc:mysql://localhost:3306/mydbtest",
            MY_USER = "root",
            MY_PASS = "root",
            MY_DRIVER = "com.mysql.cj.jdbc.Driver",
            MY_DIALECT = "org.hibernate.dialect.MySQL8Dialect",
            MY_SHOW_SQL = "true",
            MY_CURRENT_SESSION_CONTEXT_CLASS = "thread";

    /* JDBC */
    private static Connection connection;

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(MY_URL, MY_USER, MY_PASS);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /* Hibernate */
    private SessionFactory sessionFactory;

    // On Demand Holder idiom
    private static class UtilHolder {
        static final Util HOLDER_UTIL = new Util();
    }

    private Util() {
        try {
            Properties settings = new Properties();
            settings.put(Environment.DRIVER, MY_DRIVER);
            settings.put(Environment.URL, MY_URL);
            settings.put(Environment.USER, MY_USER);
            settings.put(Environment.PASS, MY_PASS);
            settings.put(Environment.DIALECT, MY_DIALECT);
            settings.put(Environment.SHOW_SQL, MY_SHOW_SQL);
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, MY_CURRENT_SESSION_CONTEXT_CLASS);

            Configuration configuration = new Configuration();
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

    public static Util getUtilInstance() {
        return UtilHolder.HOLDER_UTIL;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

