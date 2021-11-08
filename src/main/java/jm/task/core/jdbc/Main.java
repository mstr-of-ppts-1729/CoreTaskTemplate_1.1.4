package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserService service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("Jean", "Grey", (byte) 30);
        service.saveUser("Scott", "Summers", (byte) 32);
        service.saveUser("Charles", "Xavier", (byte) 60);
        service.saveUser("James", "Howlett", (byte) 80);
        System.out.println(service.getAllUsers());
        service.dropUsersTable();
    }
}