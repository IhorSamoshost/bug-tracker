package service;

import dao.UserDao;
import model.User;
import view.Response;

import java.util.List;

public interface UserService {
    Response<User> login(String userName, String password);
    Response<User> register(User newUser);
    Response<User> edit(User oldUser, User newUser);
    Response<User> find(String userName);
    Response<List<User>> findAll();
    Response<User> delete(String userName);
    UserDao getUserDao();
}
