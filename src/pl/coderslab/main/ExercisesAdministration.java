package pl.coderslab.main;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.plain.Exercise;
import pl.coderslab.plain.Solution;

import java.util.List;
import java.util.Scanner;

public class ExercisesAdministration {
    public static void main(String[] args) {


        boolean run = true;
        while(run) {
            System.out.println();
            System.out.println("---------------Exercises administration-----------------");
            ExerciseDao exerciseDao = new ExerciseDao();
            List<Exercise> exerciseList = exerciseDao.findAll();
            for (Exercise exercise : exerciseList){
                System.out.println(exercise);
            }

            System.out.println();
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


            Exercise exercise = new Exercise();

            switch (option) {
                case "add":

                    System.out.print("title: ");
                    exercise.setTitle(scanner.nextLine());
                    System.out.print("description: ");
                    exercise.setDescription(scanner.nextLine());
                    exerciseDao.create(exercise);

                    break;

                case "edit":

                    System.out.print("Exercise id: ");
                    while (!scanner.hasNextInt()){
                        scanner.nextLine();
                        System.out.print("Type a number:");
                    }
                    int exerciseId = Integer.parseInt(scanner.nextLine());
                    exercise = exerciseDao.read(exerciseId);

                    System.out.print("title: ");
                    exercise.setTitle(scanner.nextLine());
                    System.out.print("description: ");
                    exercise.setDescription(scanner.nextLine());
                    exerciseDao.update(exercise);

                    break;

                case "delete":
                    System.out.print("Exercise id: ");
                    while (!scanner.hasNextInt()){
                        scanner.nextLine();
                        System.out.print("Type a number:");
                    }
                    exerciseId = Integer.parseInt(scanner.nextLine());

                    //usunęcie rozwiązań zadania / exercise's solutions delete
                    SolutionDao solutionDao = new SolutionDao();
                    List<Solution> solutionToDelete = solutionDao.findAllByExerciseId(exerciseId);
                    for(Solution solution : solutionToDelete){
                        solutionDao.delete(solution.getId());
                    }

                    exerciseDao.delete(exerciseId);

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
