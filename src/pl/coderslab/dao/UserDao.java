package pl.coderslab.dao;

import pl.coderslab.plain.User;

import java.sql.*;

public class UserDao {
    private final String URL =
            "jdbc:mysql://localhost:3306/java_warsztat_2?useSSL=false&characterEncoding=utf8";
    private final String USER = "root";
    private final String PASSWORD = "coderslab";

    private static final String CREATE_USER_QUERY =
            "INSERT INTO user(name, email, password) VALUES (?, ?, ?)";
    private static final String READ_USER_QUERY =
            "SELECT * FROM user where id = ?";
    private static final String UPDATE_USER_QUERY =
            "UPDATE user SET name = ?, email = ?, password = ? where id = ?";
    private static final String DELETE_USER_QUERY =
            "DELETE FROM user WHERE id = ?";
    private static final String FIND_ALL_USERS_QUERY =
            "SELECT * FROM user";



    public User create(User user) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
