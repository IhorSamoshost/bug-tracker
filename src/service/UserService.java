package service;

import dao.UserDao;
import model.User;
import view.Response;

import java.io.IOException;
import java.util.List;

public interface UserService {
    Response<User> login(String userName, String password);

    Response<User> register(User newUser) throws IOException;

    Response<User> edit(User oldUser, User newUser) throws IOException;

    Response<User> find(String userName);

    Response<List<User>> findAll();

    Response<User> delete(String userName) throws IOException;

    UserDao getUserDao();
}