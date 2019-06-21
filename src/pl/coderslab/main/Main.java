package pl.coderslab.main;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.plain.Exercise;
import pl.coderslab.plain.Solution;
import pl.coderslab.plain.User;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        User user = new User("Jan Kowalski", "Jan.Kowalski@gmail.com", "tajne", 1);
        User user1 = new User("Adam Kowalski", "adam.Kowalski@gmail.com", "tajne", 1);
        User user2 = new User("Robert Kowalski", "robert.Kowalski@gmail.com", "tajne", 1);

        UserDao userDao = new UserDao();

//        userDao.create(user);
//        userDao.create(user1);
//        userDao.create(user2);

//        int id = user.getId();

        User loadUser = userDao.read(1);
        System.out.println(loadUser);

        User notExist = userDao.read(100);
        System.out.println(notExist);

        loadUser.setName("Adam Nowak");
        loadUser.setEmail("adam.nowak@gmail.com");
        userDao.update(loadUser);

        User copyUser = userDao.read(1);
        System.out.println(copyUser);

        //userDao.delete(id);

        User loadAgain = userDao.read(1);
        System.out.println(loadAgain);

        System.out.println("find all users");
        User[] users = userDao.findAll();
        for(User myUser : users) {
            System.out.println(myUser);
        }

//        userDao.delete(id);
//        userDao.delete(user1.getId());
//        userDao.delete(user2.getId());


//        Solution solution = new Solution("2019-06-02","2019-06-03","nowe rozwiązanie",1,1);
//        SolutionDao solutionDao = new SolutionDao();
//        solutionDao.create(solution);
//        System.out.println(solutionDao.read(solution.getId()));
//        solution.setUpdated("2019-06-19");
//        solutionDao.update(solution);
//        System.out.println(solutionDao.read(solution.getId()));
//        solutionDao.delete(solution.getId());
//        List<Solution> all = solutionDao.findAll();
//        for(Solution sol : all){
//            System.out.println(sol);
//        }

        Exercise exercise = new Exercise();
        exercise.setTitle("Zadanie 2");
        exercise.setDescription("tresc zadania");
        ExerciseDao exerciseDao = new ExerciseDao();
        exerciseDao.create(exercise);
        System.out.println(exerciseDao.read(exercise.getId()));
        exercise.setDescription("nowa treść zadania");
        exerciseDao.update(exercise);
        System.out.println(exerciseDao.read(exercise.getId()));
        System.out.println("------------");
        List<Exercise> all = exerciseDao.findAll();
        for(Exercise exer :all){
            System.out.println(exer);
        }
        exerciseDao.delete(exercise.getId());
        List<Exercise> allex = exerciseDao.findAll();
        System.out.println("------------");
        for(Exercise exer :allex){
            System.out.println(exer);
        }
    }



}
