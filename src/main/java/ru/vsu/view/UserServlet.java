package ru.vsu.view;

import ru.vsu.user.boundary.UserService;
import ru.vsu.user.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();
        user.setName("addedName");
        user.setAge(50);

        userService.addUser(user);

        List<User> users = userService.getUsers();
        req.setAttribute("users", users);
        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);

        super.doGet(req, resp);
    }
}
