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

public class UserProgram {
    public static void main(String[] args) {

        int userId = Integer.parseInt(args[0]);


        boolean run = true;
        while (run) {
            System.out.println();
            System.out.println("---------------Your Solutions-----------------");

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

                    List<Solution> solutions = solutionDao.findAllByUserId(userId);
                    for(Solution solution : solutions){
                        if(solution.getDescription() == null){
                            System.out.println(solution);
                        }
                    }
                    Solution solution;

                    while(true) {
                        System.out.print("solution id: ");
                        while (!scanner.hasNextInt()) {
                            scanner.nextLine();
                            System.out.print("Type a number: ");
                        }
                        int solutionId = Integer.parseInt(scanner.nextLine());
                        solution = solutionDao.read(solutionId);
                        if (solution.getDescription() == null) {
                          break;
                        }
                        System.out.println("wrong id");
                        solution = null;
                        break;
                    }

                    if (solution != null) {

                        System.out.print("exercise id: ");

                        while (!scanner.hasNextInt()) {
                            scanner.nextLine();
                            System.out.print("Type a number: ");
                        }
                        int exerciseId = Integer.parseInt(scanner.nextLine());


                        solution.setUserId(userId);
                        solution.setExerciseId(exerciseId);
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        solution.setCreated(formatter.format(new Date()));
                        System.out.println("Description: ");
                        solution.setDescription(scanner.nextLine());
                        solutionDao.update(solution);
                    }
                    break;

                case "view":

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
