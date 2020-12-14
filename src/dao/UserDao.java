package dao;

import model.User;

import java.util.List;

public interface UserDao {
    void saveUser(User user);
    User getUserByName(String userName);
    List<User> getAll();
    User deleteUser(User user);
    void updateUser(User oldUser, User newUser);
}
