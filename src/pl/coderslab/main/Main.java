package pl.coderslab.main;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.dao.GroupDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.plain.Exercise;
import pl.coderslab.plain.Group;
import pl.coderslab.plain.Solution;
import pl.coderslab.plain.User;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        boolean work = true;
        while(work) {
            System.out.println();
            System.out.println("---------------Users administration-----------------");
            UserDao userDao = new UserDao();
            User[] users = userDao.findAll();
            for (User user :users){
                System.out.println(user);
            }

            System.out.println("Options:");
            System.out.println("Add");
            System.out.println("Edit");
            System.out.println("Delete");
            System.out.println("Quit");
            System.out.print("What would you like to do: ");

            Scanner scanner = new Scanner(System.in);
            String option = "";
            if(scanner.hasNext()){
                option = scanner.nextLine().toLowerCase().trim();
            }


            User user = new User();

            switch (option) {
                case "add":

                    System.out.print("name: ");
                    user.setName(scanner.nextLine());
                    System.out.print("email: ");
                    user.setEmail(scanner.nextLine());
                    System.out.print("password: ");
                    user.setPassword(scanner.nextLine());
                    System.out.print("Group id: ");
                    while (!scanner.hasNextInt()){
                        scanner.nextLine();
                        System.out.print("Type a number:");
                    }
                    user.setUserGroupId(Integer.parseInt(scanner.nextLine()));
                    userDao.create(user);

                    break;

                case "edit":

                    System.out.print("User id: ");
                    while (!scanner.hasNextInt()){
                        scanner.nextLine();
                        System.out.print("Type a number:");
                    }
                    int userId = Integer.parseInt(scanner.nextLine());
                    user = userDao.read(userId);

                    System.out.print("name: ");
                    user.setName(scanner.nextLine());
                    System.out.print("email: ");
                    user.setEmail(scanner.nextLine());
                    System.out.print("password: ");
                    user.setPassword(scanner.nextLine());
                    System.out.print("Group id: ");
                    while (!scanner.hasNextInt()){
                        scanner.nextLine();
                        System.out.print("Type a number:");
                    }
                    user.setUserGroupId(Integer.parseInt(scanner.nextLine()));
                    userDao.update(user);

                    break;

                case "delete":
                    System.out.print("User id: ");
                    while (!scanner.hasNextInt()){
                        scanner.nextLine();
                        System.out.print("Type a number:");
                    }
                    userId = Integer.parseInt(scanner.nextLine());

                    //usunęcie rozwiązań użytkownika / user's solutions delete
                    SolutionDao solutionDao = new SolutionDao();
                    List<Solution> solutionToDelete = solutionDao.findAllByUserId(userId);
                    for(Solution solution : solutionToDelete){
                        solutionDao.delete(solution.getId());
                    }

                    userDao.delete(userId);

                    break;

                case "quit":
                    work = false;
                    break;

                    default:
                        System.out.println("That option does not exist");
                        break;



            }


        }
    }
}
