package com.yu.reggie.function.database;

import javafx.util.Pair;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyDatabase {
    protected MyDatabase() throws Exception {
        throw new Exception("Database cannot be Instantiate!");
    }

    private static final String URL = "jdbc:mysql://" + "localhost:3306/selfservice_ordering_system?" + "serverTimezone=UTC&useSSL=false&" + "allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    protected static void close(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    protected static Pair<List<List<String>>, List<Blob>> query(String sql, String blobColumn) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = createConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            List<List<String>> result = new ArrayList<List<String>>();
            ResultSetMetaData meta = resultSet.getMetaData();
            List<String> head = new ArrayList<String>();
            List<Blob> images = new ArrayList<Blob>();
            Pair<List<List<String>>, List<Blob>> pair = new Pair<>(result, images);
            int columnCount = meta.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                head.add(meta.getColumnLabel(i));
            }
            result.add(head);
//			images.add(null);//to line up with result's title;
            while (resultSet.next()) {
                List<String> line = new ArrayList<String>();
                for (int i = 1; i <= columnCount; i++) {
                    if (blobColumn.equals(head.get(i - 1))) {
                        images.add(resultSet.getBlob(i));
                    }
                    line.add(resultSet.getString(i));
                }
                result.add(line);
            }
            return pair;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.err.println(sql);
            return null;
        } finally {
            close(connection, statement, resultSet);
        }
    }

    protected static List<List<String>> query(String sql) {
//		System.out.println("Query: " + sql);
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = createConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            List<List<String>> result = new ArrayList<List<String>>();
            ResultSetMetaData meta = resultSet.getMetaData();
            List<String> head = new ArrayList<String>();
            int columnCount = meta.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                head.add(meta.getColumnLabel(i));
            }
            result.add(head);
            while (resultSet.next()) {
                List<String> line = new ArrayList<String>();
                for (int i = 1; i <= columnCount; i++) {
                    line.add(resultSet.getString(i));
                }
                result.add(line);
            }
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.err.println(sql);
            return null;
        } finally {
            close(connection, statement, resultSet);
        }
    }

    protected static boolean nonQuery(String sql) {
//		System.out.println("NonQuery: " + sql);
        Connection connection = null;
        Statement statement = null;
        try {
            connection = createConnection();
            statement = connection.createStatement();
            statement.execute(sql);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.err.println(sql);
            return false;
        } finally {
            close(connection, statement, null);
        }
    }

    protected static boolean nonQuery(String sql, InputStream image) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = createConnection();
            statement = connection.prepareStatement(sql);
            if (image != null) {
                statement.setBlob(1, image);
            }
            return statement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.err.println(sql);
            return false;
        } finally {
            close(connection, statement, null);
        }
    }

    public static List<String> findByColumn(List<List<String>> result, String columnName) {
        if (!isResultValid((result))) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        int index = -1;
        List<String> title = result.get(0);
        for (int i = 0; i < title.size(); i++) {
            if (title.get(i).equals(columnName)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            for (List<String> line : result) {
                list.add(line.get(index));
            }
            return list;
        } else {
            return null;
        }
    }

    protected static boolean isResultValid(List<List<String>> result) {
        return result != null && result.size() > 1 && result.get(0).size() != 0;
    }

    protected static void printResult(List<List<String>> result) {
        for (List<String> line : result) {
            StringBuilder lineString = new StringBuilder();
            for (String data : line) {
                lineString.append(data).append("\t");
            }
            System.out.println(lineString);
        }
    }
}
