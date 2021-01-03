package service;

import dao.UserDao;
import model.User;
import view.Response;

import java.io.IOException;
import java.util.List;

public class UserServiceImpl implements UserService {

    UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Response<User> login(String userName, String password) {
        Response<User> userFindResponse = find(userName);
        User user = userFindResponse.getData();
        if (userFindResponse.isSuccess()) {
            return user.getPassword().equals(password)
                    ? new Response<>(user, true,
                    String.format("User successfully entered as '%s'", user.getUserName()))
                    : new Response<>(user, false,
                    "Wrong password");
        } else {
            return new Response<>(user, false,
                    String.format("User '%s' is not found in DataBase", userName));
        }
    }

    @Override
    public Response<User> register(User newUser) {
        try {
            userDao.saveUser(newUser);
            Response<User> user = find(newUser.getUserName());
            return user.isSuccess()
                    ? new Response<>(user.getData(), true,
                    String.format("User '%s' has successfully registered and logged in!", newUser.getUserName()))
                    : new Response<>(user.getData(), false,
                    String.format("User '%s' is not registered", newUser.getUserName()));
        } catch (IOException ioe) {
            return new Response<>(newUser, false, "There is no users' DB file");
        }
    }

    @Override
    public Response<User> edit(User oldUser, User newUser) {
        try {
            userDao.updateUser(oldUser, newUser);
            Response<User> user = find(newUser.getUserName());
            return user.isSuccess()
                    ? new Response<>(user.getData(), true,
                    String.format("User '%s' successfully updated", oldUser.getUserName()))
                    : new Response<>(user.getData(), false,
                    String.format("User '%s' is not updated", oldUser.getUserName()));
        } catch (IOException ioe) {
            return new Response<>(oldUser, false,
                    "There is no users' DB file");
        }
    }

    @Override
    public Response<User> find(String userName) {
        User user = userDao.getUserByName(userName);
        return (user == null)
                ? new Response<>(user, false,
                String.format("User '%s' is not found", userName))
                : new Response<>(user, true,
                String.format("User '%s' is successfully found:", user.getUserName()));
    }

    @Override
    public Response<List<User>> findAll() {
        List<User> userList = userDao.getAll();
        return (userList == null)
                ? new Response<>(userList, false, "Database not found")
                : new Response<>(userList, true, "There are users found in database:");
    }

    @Override
    public Response<User> delete(String userName) {
        User userToDelete = userDao.getUserByName(userName);
        if (userToDelete != null) {
            try {
                userDao.deleteUser(userToDelete);
            } catch (IOException ioe) {
                return new Response<>(userToDelete, false,
                        "There is no users' DB file");
            }
            User controlUser = userDao.getUserByName(userName);
            return (controlUser == null)
                    ? new Response<>(controlUser, true,
                    String.format("User '%s' deleted successfully", userName))
                    : new Response<>(controlUser, false,
                    String.format("User '%s' is not deleted", userName));
        }
        return new Response<>(userToDelete, false,
                String.format("User '%s' is not found in DataBase", userName));
    }
}