package dao;

import model.User;

import java.util.List;

public interface UserDao {

    void saveUser(User user);

    void deleteUser(User user);

    List<User> getAll();

    User getUserByName(String userName);

    void updateUser(User oldUser, User newUser);
}
