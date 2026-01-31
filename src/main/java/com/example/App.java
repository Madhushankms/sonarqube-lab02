package com.example;

import java.util.logging.Logger;

public class App {

    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) throws Exception {

        Calculator calc = new Calculator();

        // FIXED: The calculation is wrapped or passed as an argument
        logger.log(java.util.logging.Level.INFO, "Result: {0}", calc.calculate(10, 5, "add"));

        UserService service = new UserService();
        service.findUser("admin");
        service.deleteUser("admin");
    }
}