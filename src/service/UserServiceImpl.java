package service;

import dao.UserDao;
import model.User;
import view.Response;

import java.io.IOException;
import java.util.List;

public class UserServiceImpl implements UserService {

    UserDao userDao;

    public UserServiceImpl(UserDao UserDao) {
        this.userDao = userDao;

    }

    @Override
    public Response<User> login(String userName, String password) {
        Response<User> userFindResponse = find(userName);
        User user = userFindResponse.getData();
        if (userFindResponse.isSuccess()) {
            return user.getPassword().equals(password)
                    ? new Response<>(user, true, "Successful")
                    : new Response<>(user, false, "Wrong password");
        } else {
            return new Response<>(user, false, "User is not found in DataBase");
        }

    }

    @Override
    public Response<User> register(User newUser) throws IOException {
        userDao.saveUser(newUser);
        Response<User> user = find(newUser.getUserName());
        return user.isSuccess()
                ? new Response<>(user.getData(), true, "Successful")
                : new Response<>(user.getData(), false, "User  is not registered");

    }

    @Override
    public Response<User> edit(User oldUser, User newUser) throws IOException {
        userDao.updateUser(oldUser, newUser);
        Response<User> user = find(newUser.getUserName());
        return user.isSuccess()
                ? new Response<>(user.getData(), true, "Successful")
                : new Response<>(user.getData(), false, "User  is not edited");

    }

    @Override
    public Response<User> find(String userName) {
        User user = userDao.getUserByName(userName);
        return (user == null)
                ? new Response<>(user, false, "User  is not found")
                : new Response<>(user, true, "User found successfully");

    }

    @Override
    public Response<List<User>> findAll() {
        List<User> userList = userDao.getAll();
        return (userList == null)
                ? new Response<>(userList, false, "DataBase not found")
                : new Response<>(userList, true, "DataBase found successfully");

    }

    @Override
    public Response<User> delete(String userName) throws IOException {
        userDao.deleteUser(userDao.getUserByName(userName));
        User user = userDao.getUserByName(userName);
        return (user == null)
                ? new Response<>(user, true, "User deleted successfully")
                : new Response<>(user, false, "User is not found");


    }

    @Override
    public UserDao getUserDao() {
        return userDao;
    }

}