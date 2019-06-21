package pl.coderslab.dao;

import pl.coderslab.plain.Group;
import pl.coderslab.utlis.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDao {
    private static String CREATE_GROUP_QUERY = "INSERT INTO user_group (name) VALUES (?)";
    private static String READ_GROUP_QUERY = "SELECT * FROM user_group WHERE id = ?";
    private static String UPDATE_GROUP_QUERY = "UPDATE user_group SET name = ? WHERE id = ?";
    private static String DELETE_GROUP_QUERY = "DELETE FROM user_group WHERE id = ?";
    private static String FIND_ALL_GROUP_QUERY = "SELECT * FROM user_group";

    public Group create(Group group){
        try(Connection connection = DatabaseUtils.getConnection("java_warsztat_2")){
           PreparedStatement statement = connection.prepareStatement(CREATE_GROUP_QUERY, Statement.RETURN_GENERATED_KEYS);
           statement.setString(1, group.getName());
           statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()){
                group.setId(resultSet.getInt(1));
            }
            return group;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public Group read(int groupId){
        try(Connection connection = DatabaseUtils.getConnection("java_warsztat_2")){
          PreparedStatement statement = connection.prepareStatement(READ_GROUP_QUERY);
          statement.setInt(1, groupId);
          ResultSet resultSet = statement.executeQuery();
          if (resultSet.next()){
              Group group = new Group();
              group.setId(resultSet.getInt("id"));
              group.setName(resultSet.getString("name"));
              return group;
          }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void update(Group group){
        try(Connection connection = DatabaseUtils.getConnection("java_warsztat_2")){
            PreparedStatement statement = connection.prepareStatement(UPDATE_GROUP_QUERY);
            statement.setString(1, group.getName());
            statement.setInt(2, group.getId());
            statement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(int groupId){
        try(Connection connection = DatabaseUtils.getConnection("java_warsztat_2")){
            PreparedStatement statement = connection.prepareStatement(DELETE_GROUP_QUERY);
            statement.setInt(1, groupId);
            statement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Group> findAll(){
        try(Connection connection = DatabaseUtils.getConnection("java_warsztat_2")){
            List<Group> groups = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_GROUP_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Group group = new Group();
                group.setId(resultSet.getInt("id"));
                group.setName(resultSet.getString("name"));
                groups.add(group);
            }
            return groups;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

}