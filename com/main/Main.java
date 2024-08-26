package com.main;

import com.Controlador.LoginController;
import com.Vista.LoginView;

public class Main {
    public static void main(String[] args) {
        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController(loginView);
        loginView.setVisible(true);
    }
}
