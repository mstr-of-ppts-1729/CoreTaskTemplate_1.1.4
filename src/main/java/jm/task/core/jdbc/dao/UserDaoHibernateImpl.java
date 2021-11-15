package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;


import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final Session session = new Util().getSession();

    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users " +
                    "(id INTEGER PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45), " +
                    "lastname VARCHAR(45), age INT (3))").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                e.printStackTrace();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            if (tx != null) {
                tx.commit();
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                e.printStackTrace();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.persist(user);
            System.out.println("User с именем " + name + " добавлен в базу данных");
            if (tx != null) {
                tx.commit();
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                e.printStackTrace();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        try {
            User user = (User) session.get(User.class, id);
            tx = session.beginTransaction();
            session.delete(user);
            if (tx != null) {
                tx.commit();
            }
        } catch (
                Exception e) {
            if (tx != null) {
                tx.rollback();
                e.printStackTrace();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            allUsers = session.createQuery("FROM User").list();
            if (tx != null) {
                tx.commit();
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                e.printStackTrace();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return allUsers;
    }

    @Override
    public void cleanUsersTable() {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            if (tx != null) {
                tx.commit();
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                e.printStackTrace();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
