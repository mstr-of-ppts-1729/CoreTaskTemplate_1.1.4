package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = new Util().getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try {
            try {
                connection.setAutoCommit(false);
                String sql = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                        "name VARCHAR(20), lastname VARCHAR(25), age INT (3))";
                Statement statement = connection.createStatement();
                statement.execute(sql);
                connection.commit();
            } catch (Exception e) {
                e.printStackTrace();
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
                connection.close();
            }
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            try {
                connection.setAutoCommit(false);
                Statement statement = connection.createStatement();
                String sql = "DROP TABLE IF EXISTS users";
                statement.execute(sql);
                connection.commit();
            } catch (Exception e) {
                e.printStackTrace();
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
                connection.close();
            }
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            try {
                connection.setAutoCommit(false);
                String sql = "INSERT INTO users (name, lastName, age) Values (?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setByte(3, age);
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println(
                            "User с именем " + name + " добавлен в базу данных");
                }
                connection.commit();
            } catch (Exception e) {
                e.printStackTrace();
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
                connection.close();
            }
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            try {
                connection.setAutoCommit(false);
                String sql = "DELETE FROM Users WHERE id=?";

                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setLong(1, id);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("A user  " + id + " was deleted successfully!");
                }
                connection.commit();
            } catch (Exception e) {
                e.printStackTrace();
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
                connection.close();
            }
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        try {
            try {
                connection.setAutoCommit(false);
                String sql = "SELECT * FROM Users";

                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(sql);
                while (result.next()) {
                    String name = result.getString(2);
                    String lastName = result.getString(3);
                    byte age = result.getByte(4);
                    User user = new User(name, lastName, age);
                    allUsers.add(user);
                }
                connection.commit();
            } catch (Exception e) {
                e.printStackTrace();
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
                connection.close();
            }
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        try {
            try {
                connection.setAutoCommit(false);
                String sql = "DELETE FROM Users";
                Statement statement = connection.createStatement();
                statement.execute(sql);
                connection.commit();
            } catch (Exception e) {
                e.printStackTrace();
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
                connection.close();
            }
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
    }
}
