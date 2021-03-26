package Lesson2.Server.service;

import Lesson2.Server.interfaces.AuthenticationInt;
//TODO шифрование пароля

public class AuthenticationService implements AuthenticationInt {

 @Override
    public String authenticationAlgorithm(String login, String pass) {
        return DabaBaseService.authentication(login, pass);
    }
}
