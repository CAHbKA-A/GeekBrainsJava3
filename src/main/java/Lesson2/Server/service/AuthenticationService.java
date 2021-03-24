package Lesson2.Server.service;

import Lesson2.Server.interfaces.AuthenticationInt;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationService implements AuthenticationInt {

    private List<User> membersList;

    @Override
    public void createMemberList() {
        membersList = new ArrayList<>();
        membersList.add(new User("A", "A", "Alex"));
        membersList.add(new User("B", "B", "Bul"));
        membersList.add(new User("C", "C", "Carl"));
        for (int i = 1; i < 10; i++) {
            membersList.add(new User("A" + i, "A" + i, "Alex_" + i));
        }
    }

    @Override
    public String authenticationAlgorithm(String login, String pass) {
        for (User user : membersList) {

            if (user.login.equals(login) && user.password.equals(pass)) {
                //     System.out.println("!!!");


                return user.nickName;
            }
        }
        return "";
    }

    private class User {
        private String login;
        private String password;
        private String nickName;
        // private String permission;

        public User(String login, String password, String nickName) {
            this.login = login;
            this.password = password;
            this.nickName = nickName;
        }
    }
}
