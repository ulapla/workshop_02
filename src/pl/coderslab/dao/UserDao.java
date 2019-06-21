package pl.coderslab.dao;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.plain.User;
import pl.coderslab.utlis.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDao {
    private final String URL =
            "jdbc:mysql://localhost:3306/java_warsztat_2?useSSL=false&characterEncoding=utf8";
    private final String USER = "root";
    private final String PASSWORD = "coderslab";

    private static final String CREATE_USER_QUERY =
            "INSERT INTO user(name, email, password, user_group_id) VALUES (?, ?, ? ,?)";
    private static final String READ_USER_QUERY =
            "SELECT * FROM user where id = ?";
    private static final String UPDATE_USER_QUERY =
            "UPDATE user SET name = ?, email = ?, password = ?, user_group_id = ? where id = ?";
    private static final String DELETE_USER_QUERY =
            "DELETE FROM user WHERE id = ?";
    private static final String FIND_ALL_USERS_QUERY =
            "SELECT * FROM user";
    private static final String FIND_ALL_USERS_BY_GROUP_ID_QUERY =
            "SELECT * FROM user WHERE user_group_id = ?";




    public User create(User user) {
        try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            statement.setInt(4, user.getUserGroupId());
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


    public User read(int userId) {
        try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
            PreparedStatement statement = conn.prepareStatement(READ_USER_QUERY);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setUserGroupId(resultSet.getInt("user_group_id"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void update(User user) {
        try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_USER_QUERY);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            // czy zmieniono hasło / if password is changed
            if(!user.getPassword().equals(read(user.getId()).getPassword())) {
                //szyfrowanie hasła / hash new password
                statement.setString(3,BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            }
            else{//otherwise it is already hashed
                statement.setString(3, user.getPassword());
            }
            statement.setInt(4, user.getUserGroupId());
            statement.setInt(5, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void delete(int userId) {
        try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
            PreparedStatement statement = conn.prepareStatement(DELETE_USER_QUERY);
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // needed if List object is not used
    private User[] addToArray(User user, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1);
        tmpUsers[users.length] = user;
        return tmpUsers;
    }


    public User[] findAll() {
        try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
            User[] users = new User[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_USERS_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setUserGroupId(resultSet.getInt("user_group_id"));
                users = addToArray(user, users);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace(); return null;
        }
    }


    public List<User> findAllByGroupId(int groupId){
        try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
            List<User> users = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_USERS_BY_GROUP_ID_QUERY);
            statement.setInt(1, groupId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setUserGroupId(resultSet.getInt("user_group_id"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
