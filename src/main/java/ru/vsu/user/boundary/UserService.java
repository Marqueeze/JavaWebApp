package ru.vsu.user.boundary;

import ru.vsu.persistence.DAO;
import ru.vsu.persistence.UserDAO;
import ru.vsu.user.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private DAO<User> userDAO = new UserDAO();

    public List<User> getUsers(){
        return userDAO.findAll();
    }

    public User getUserById(Long id){
        return userDAO.findById(id);
    }

    public String addUser(User user) {
        if (userDAO.save(user))
            return "Added succesfully";
        else
            return "Exception while addin user";
    }

    public String updateUser(User user){
        if (userDAO.update(user))
            return "Added succesfully";
        else
            return "Exception while addin user";
    }

    public String deleteUser(User user) {
        if (userDAO.delete(user))
            return "Added succesfully";
        else
            return "Exception while addin user";
    }
    public List<User> getMockUsers(){
        List<User> users = new ArrayList<User>();

        for(int i = 1; i <= 10; i++){
            users.add(new User(((long) i), "User_" + i, i * 10));
        }
        return users;
    }
}
