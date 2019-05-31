package pl.coderslab.main;

import pl.coderslab.dao.UserDao;
import pl.coderslab.plain.User;

public class Main {
    public static void main(String[] args) {
        User user = new User("Jan Kowalski", "Jan.Kowalski@gmail.com", "tajne");
        UserDao userDao = new UserDao();
        userDao.create(user);
        int id = user.getId();

        User loadUser = userDao.read(id);
        System.out.println(loadUser);

        User notExist = userDao.read(100);
        System.out.println(notExist);

        loadUser.setName("Adam Nowak");
        loadUser.setEmail("adam.nowak@gmail.com");
        userDao.update(loadUser);

        User copyUser = userDao.read(id);
        System.out.println(copyUser);

        userDao.delete(id);

        User loadAgain = userDao.read(id);
        System.out.println(loadAgain);

    }
}
