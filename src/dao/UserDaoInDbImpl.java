package dao;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoInDbImpl implements UserDao {
    private Connection conn;

    public UserDaoInDbImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void saveUser(User user) {
        String userName = user.getUserName();
        String password = user.getPassword();
        User checkUser = getUserByName(userName);
        if (checkUser == null) {
            String addUserQuery = "insert into user_table (user_name, password) values ('" +
                    userName + "', '" + password + "');";
            try (Statement stmnt = conn.createStatement()) {
                stmnt.executeUpdate(addUserQuery);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("User with this login is already registered in the database.");
        }
    }

    @Override
    public void updateUser(User oldUser, User newUser) {
        String oldUserName = oldUser.getUserName();
        String newUserName = newUser.getUserName();
        String newPassword = newUser.getPassword();
        User checkUser = getUserByName(oldUserName);
        if (checkUser != null) {
            String updateUserQuery =
                    String.format("update user_table set user_name = '%s', password = '%s' where user_name = '%s';",
                            newUserName, newPassword, oldUserName);
            try (Statement stmnt = conn.createStatement()) {
                stmnt.executeUpdate(updateUserQuery);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.printf("There is no user with name '%s' in the database\n", oldUserName);
        }
    }

    @Override
    public User getUserByName(String userName) {
        if (userName != null) {
            String userNameFromDB = null;
            String userPassword = null;
            String selectUserByNameQuery = String.format("select user_table.user_name, user_table.password " +
                    "from user_table where user_table.user_name = '%s';", userName);
            try (Statement stmnt = conn.createStatement()) {
                ResultSet resultSetForSoughtUser = stmnt.executeQuery(selectUserByNameQuery);
                while (resultSetForSoughtUser.next()) {
                    userNameFromDB = resultSetForSoughtUser.getString("user_name");
                    userPassword = resultSetForSoughtUser.getString("password");
                }
                return userNameFromDB != null ? new User(userNameFromDB, userPassword) : null;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            } catch (NullPointerException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String selectAllUsersQuery = "select user_table.user_name, user_table.password from user_table;";
        try (Statement stmnt = conn.createStatement()) {
            ResultSet resultUserSet = stmnt.executeQuery(selectAllUsersQuery);
            while (resultUserSet.next()) {
                String userName = resultUserSet.getString("user_name");
                String userPass = resultUserSet.getString("password");
                users.add(new User(userName, userPass));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User deleteUser(User user) {
        String userName = user.getUserName();
        String deleteUserByNameQuery =
                String.format("delete from user_table where user_table.user_name = '%s';", userName);
        try (Statement stmnt = conn.createStatement()) {
            stmnt.executeUpdate(deleteUserByNameQuery);
            return getUserByName(userName);
        } catch (SQLException e) {
            e.printStackTrace();
            return getUserByName(userName);
        }
    }
}
