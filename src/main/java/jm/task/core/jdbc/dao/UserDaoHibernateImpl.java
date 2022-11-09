//package jm.task.core.jdbc.dao;
//
//import jm.task.core.jdbc.model.User;
//import jm.task.core.jdbc.util.Util;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//
//import java.sql.ResultSet;
//import java.util.List;
//
//public class UserDaoHibernateImpl implements UserDao {
//    private SessionFactory sessionFactory = Util.getSessionFactory();
//
//    public UserDaoHibernateImpl() {
//
//    }
//
//    @Override
//    public void createUsersTable() {
//        try (Session session = sessionFactory.getCurrentSession()) {
//            try {
//                session.beginTransaction();
//                String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS User (" +
//                        "id BIGINT NOT NULL AUTO_INCREMENT," +
//                        "name VARCHAR(64)," +
//                        "lastName VARCHAR(64)," +
//                        "age SMALLINT," +
//                        "PRIMARY KEY ( id ) )";
//                session.createSQLQuery(CREATE_TABLE)
//                        .executeUpdate();
//                session.getTransaction().commit();
//            } catch (Exception e) {
//                e.printStackTrace();
//                session.getTransaction().rollback();
//            }
//        }
//    }
//
//    @Override
//    public void dropUsersTable() {
//        try (Session session = sessionFactory.getCurrentSession()) {
//            try {
//                session.beginTransaction();
//                String DROP_TABLE = "DROP TABLE IF EXISTS User";
//                session.createSQLQuery(DROP_TABLE).executeUpdate();
//                session.getTransaction().commit();
//            } catch (Exception e) {
//                e.printStackTrace();
//                session.getTransaction().rollback();
//            }
//        }
//    }
//
//    @Override
//    public void saveUser(String name, String lastName, byte age) {
//        try (Session session = sessionFactory.getCurrentSession()) {
//            try {
//                session.beginTransaction();
//                session.save(new User(name, lastName, age));
//                session.getTransaction().commit();
//            } catch (Exception e) {
//                e.printStackTrace();
//                session.getTransaction().rollback();
//            }
//        }
//    }
//
//    @Override
//    public void removeUserById(long id) {
//        try (Session session = sessionFactory.getCurrentSession()) {
//                session.beginTransaction();
//                User user = session.get(User.class, id);
//                session.delete(user);
//                session.getTransaction().commit();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//    @Override
//    public List<User> getAllUsers() {
//        List<User> listUsers = null;
//        try (Session session = sessionFactory.getCurrentSession()) {
//            session.beginTransaction();
//            return session.createQuery("FROM User").getResultList();
//        } catch (Exception e) {
//            e.printStackTrace();
//            sessionFactory.getCurrentSession().getTransaction().rollback();
//        }
//        return listUsers;
//    }
//
//    @Override
//    public void cleanUsersTable() {
//        try (Session session = sessionFactory.getCurrentSession()) {
//            try {
//                session.beginTransaction();
//                session.createQuery("DELETE FROM User").executeUpdate();
//                session.getTransaction().commit();
//            } catch (Exception e) {
//                e.printStackTrace();
//                session.getTransaction().rollback();
//            }
//        }
//    }
//}
