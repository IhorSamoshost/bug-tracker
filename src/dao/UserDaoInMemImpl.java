package dao;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDaoInMemImpl implements UserDao{
    private final List<User> users = new ArrayList<>();

    public UserDaoInMemImpl() {
        users.add(new User("admin", "admin"));
        users.add(new User("guest", "12345"));
    }

    @Override
    public User getUserByName(String userName) {
        return users.stream()
                .filter(user -> user.getUserName()
                        .equals(userName)).findFirst().orElse(null);
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public void saveUser(User user) {
        if(getUserByName(user.getUserName()) == null) {
            users.add(user);
        }
    }

    @Override
    public void updateUser(User oldUser, User newUser) {
        int userIndex = users.indexOf(oldUser);
        if(userIndex >= 0) {
            users.set(userIndex, newUser);
        }
    }

    @Override
    public void deleteUser(User user) {
        users.remove(user);
    }
}
