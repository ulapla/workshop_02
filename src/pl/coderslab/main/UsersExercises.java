package pl.coderslab.main;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.plain.Exercise;
import pl.coderslab.plain.Solution;
import pl.coderslab.plain.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class UsersExercises {
    public static void main(String[] args) {


        boolean run = true;
        while (run) {
            System.out.println();
            System.out.println("---------------User's Exercises-----------------");

            System.out.println();
            System.out.println("Options:");
            System.out.println("Add");
            System.out.println("View");
            System.out.println("Quit");
            System.out.print("What would you like to do: ");

            Scanner scanner = new Scanner(System.in);
            String option = "";
            if (scanner.hasNext()) {
                option = scanner.nextLine().toLowerCase().trim();
            }

            UserDao userDao = new UserDao();
            ExerciseDao exerciseDao = new ExerciseDao();
            SolutionDao solutionDao = new SolutionDao();


            switch (option) {
                case "add":


                    User[] users = userDao.findAll();
                    for(User user : users){
                        System.out.println(user);
                    }
                    System.out.print("user id: ");
                    while(!scanner.hasNextInt()){
                        scanner.nextLine();
                        System.out.print("Type a number: ");
                    }
                    int userId = Integer.parseInt(scanner.nextLine());


                    List<Exercise> exercises = exerciseDao.findAll();
                    for(Exercise exercise : exercises){
                        System.out.println(exercise);
                    }
                    System.out.print("exercise id: ");
                    while(!scanner.hasNextInt()){
                        scanner.nextLine();
                        System.out.print("Type a number: ");
                    }
                    int exerciseId = Integer.parseInt(scanner.nextLine());

                    Solution solution = new Solution();
                    solution.setUserId(userId);
                    solution.setExerciseId(exerciseId);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    solution.setCreated(formatter.format(new Date()));
                    solutionDao.create(solution);

                    break;

                case "view":

                    User[] userList = userDao.findAll();
                    for(User user : userList){
                        System.out.println(user);
                    }
                    System.out.print("user id: ");
                    while(!scanner.hasNextInt()){
                        scanner.nextLine();
                        System.out.print("Type a number: ");
                    }
                    userId = Integer.parseInt(scanner.nextLine());

                    List<Solution> solutionList = solutionDao.findAllByUserId(userId);
                    for(Solution sol : solutionList){
                        System.out.println(sol);
                    }

                    break;



                case "quit":
                    run = false;
                    break;

                default:
                    System.out.println("That option does not exist");
                    break;


            }


        }
    }
}
