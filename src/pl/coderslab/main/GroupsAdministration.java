package pl.coderslab.main;

import pl.coderslab.dao.GroupDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.plain.Group;
import pl.coderslab.plain.Solution;
import pl.coderslab.plain.User;

import java.util.List;
import java.util.Scanner;

public class GroupsAdministration {
    public static void main(String[] args) {


        boolean run = true;
        while(run) {
            System.out.println();
            System.out.println("---------------Groups administration-----------------");
            GroupDao groupDao = new GroupDao();
            List<Group> groupList = groupDao.findAll();
            for (Group group : groupList){
                System.out.println(group);
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


            Group group = new Group();

            switch (option) {
                case "add":

                    System.out.print("name: ");
                    group.setName(scanner.nextLine());
                    groupDao.create(group);

                    break;

                case "edit":

                    System.out.print("Group id: ");
                    while (!scanner.hasNextInt()){
                        scanner.nextLine();
                        System.out.print("Type a number:");
                    }
                    int groupId = Integer.parseInt(scanner.nextLine());
                    group = groupDao.read(groupId);

                    System.out.print("name: ");
                    group.setName(scanner.nextLine());
                    groupDao.update(group);

                    break;

                case "delete":
                    System.out.print("Group id: ");
                    while (!scanner.hasNextInt()){
                        scanner.nextLine();
                        System.out.print("Type a number:");
                    }
                    groupId = Integer.parseInt(scanner.nextLine());

                    //usunęcie rozwiązań użytkownika / user's solutions delete
                    UserDao userDao = new UserDao();
                    SolutionDao solutionDao = new SolutionDao();
                    List<User> userList = userDao.findAllByGroupId(groupId);
                    for(User user : userList){
                        List<Solution> solutionList = solutionDao.findAllByUserId(user.getId());
                        for(Solution solution : solutionList){
                            solutionDao.delete(solution.getId());
                        }
                        userDao.delete(user.getId());
                    }

                    groupDao.delete(groupId);

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
