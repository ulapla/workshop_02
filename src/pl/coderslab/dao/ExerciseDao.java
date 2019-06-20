package pl.coderslab.dao;

import pl.coderslab.plain.Exercise;
import pl.coderslab.utlis.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDao {
    private static String CREATE_EXERCISE_QUERY = "INSERT INTO exercise (title, description) VALUES (?, ?)";
    private static String READ_EXERCISE_QUERY = "SELECT * FROM exercise WHERE id = ?";
    private static String UPDATE_EXERCISE_QUERY = "UPDATE exercise SET title = ?, description = ? WHERE id = ?";
    private static String DELETE_EXERCISE_QUERY = "DELETE FROM exercise WHERE id = ?";
    private static String FIND_ALL_EXERCISE_QUERY = "SELECT * FROM exercise";


    public Exercise create(Exercise exercise){
        try ( Connection connection = DatabaseUtils.getConnection("java_warsztat_2")){
            PreparedStatement statement = connection.prepareStatement(CREATE_EXERCISE_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,exercise.getTitle());
            statement.setString(2,exercise.getDescription());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                exercise.setId(resultSet.getInt(1));
            }
            return exercise;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Exercise read(int exerciseId){
        try(Connection connection = DatabaseUtils.getConnection("java_warsztat_2")){
            PreparedStatement statement = connection.prepareStatement(READ_EXERCISE_QUERY);
            statement.setInt(1,exerciseId);
            statement.executeUpdate();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setTitle(resultSet.getString("title"));
                exercise.setDescription(resultSet.getString("description"));
                return exercise;
            }
        }
        catch (SQLException e){
            e.printStackTrace();

        }
        return null;
    }

    public void update(Exercise exercise){
        try(Connection connection = DatabaseUtils.getConnection("java_warsztat_2")){
            PreparedStatement statement = connection.prepareStatement(UPDATE_EXERCISE_QUERY);
            statement.setString(1, exercise.getTitle());
            statement.setString(2, exercise.getDescription());
            statement.setInt(3, exercise.getId());
            statement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(int exerciseId){
        try(Connection connection = DatabaseUtils.getConnection("java_warsztat_2")){
            PreparedStatement statement = connection.prepareStatement(DELETE_EXERCISE_QUERY);
            statement.setInt(1,exerciseId);
            statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<Exercise> findAll(){
        try(Connection connection = DatabaseUtils.getConnection("java_warsztat_2")){
            List <Exercise> exercises = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_EXERCISE_QUERY);
            statement.executeUpdate();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setTitle(resultSet.getString("title"));
                exercise.setDescription(resultSet.getString("description"));
                exercises.add(exercise);
            }
            return exercises;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
