package ru.vsu.persistence;

import ru.vsu.user.entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class DAO<T> {

    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/phonebook";
    private static final String DATABASE_USER = "postgres";
    private static final String DATABASE_PASSWORD = "Yfevtyrj1";

    protected String tableName;
    protected String[] columnNames;

    DAO() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<T> findAll(){
        return executeSelectQuery(String.format("select * from %s", tableName));
    }

    public T findById(Long id){
        return executeSelectQuery(String.format("select * from %s where id = %d", tableName, id)).get(0);
    }

    public boolean save(T t){
        try{

            String format = String.format("insert into %s (%s) VALUES (%s)",
                    tableName,
                    String.join(", ", columnNames),
                    String.join(", ", getInstanceValues(t)));
            executeNonSelectQuery(format);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean update(T t){
        try{
            String[] values = getValuesWithColumnNames(t);
            String query = String.format("update %s set %s where id=%d",
                    tableName,
                    String.join(", ", values),
                    getId(t));
            executeNonSelectQuery(query);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean delete(T t) {
        try {
            String[] values = getValuesWithColumnNames(t);
            String query = String.format("delete from %s where %s",
                    tableName,
                    String.join(" and ", values));
            executeNonSelectQuery(query);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    private void executeNonSelectQuery(String query){
        try (
                Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<T> executeSelectQuery(String query) {
        try (
                Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return convertFrom(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String[] getValuesWithColumnNames(T t){
        String[] instanceValues = getInstanceValues(t);
        String[] values = new String[columnNames.length];
        for(int i = 0; i<values.length; i++){
            values[i]=columnNames[i]+"="+instanceValues[i];
        }
        return values;
    }

    protected abstract List<T> convertFrom(ResultSet resultSet) throws SQLException;
    protected abstract String[] getInstanceValues(T t);
    protected abstract Long getId(T t);


}
