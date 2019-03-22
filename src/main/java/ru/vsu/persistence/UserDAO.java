package ru.vsu.persistence;

import ru.vsu.user.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DAO<User> {
    public UserDAO() {
        super();

        this.tableName = "users";
        this.columnNames = new String[] {"name", "age"};
    }

    @Override
    protected List<User> convertFrom(ResultSet resultSet) throws SQLException {
        List<User> result = new ArrayList<>();
        while(resultSet.next()){
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setAge(resultSet.getInt("age"));
            result.add(user);
        }
        return result;
    }

    @Override
    protected String[] getInstanceValues(User user) {
        return new String[]{"'" + user.getName() + "'", ""+user.getAge()};
    }

    @Override
    protected Long getId(User user) {
        return user.getId();
    }
}
