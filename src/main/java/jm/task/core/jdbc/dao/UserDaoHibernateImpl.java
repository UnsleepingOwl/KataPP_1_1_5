package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private Util util = Util.getSessionFactorUtil();
    private SessionFactory sessionFactory = util.getSessionFactory();
    private Session session;

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS User (" +
                    "id BIGINT NOT NULL AUTO_INCREMENT," +
                    "name VARCHAR(64)," +
                    "lastName VARCHAR(64)," +
                    "age SMALLINT," +
                    "PRIMARY KEY ( id ) )";
            session.createSQLQuery(CREATE_TABLE)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }


    @Override
    public void dropUsersTable() {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            String DROP_TABLE = "DROP TABLE IF EXISTS User";
            session.createSQLQuery(DROP_TABLE).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> listUsers = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            listUsers = session.createQuery("FROM User").getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return listUsers;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}

