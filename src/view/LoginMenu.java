package view;

import model.User;
import service.TicketService;
import service.UserService;

import java.util.Scanner;

public class LoginMenu implements Menu {
    private final Scanner scanner;
    private final UserService userService;
    private final TicketService ticketService;

    String[] enterItems = new String[]{
            "1. Log in of a registered user",
            "2. Registration of a new user",
            "3. Back to method of storing data in the computer memory selection menu",
            "0. Exit"
    };

    public LoginMenu(Scanner scanner, UserService userService, TicketService ticketService) {
        this.scanner = scanner;
        this.userService = userService;
        this.ticketService = ticketService;
    }

    @Override
    public void show() {
        while (true) {
            System.out.println("\nTo select a way to log in the application,\n" +
                    "input the corresponding number:");
            for (String item : enterItems) {
                System.out.println(item);
            }
            System.out.println("Input your selected item's number: ");
            switch (scanner.nextLine()) {
                case "1":
                    loginSubMenu();
                    break;
                case "2":
                    registerSubMenu();
                    break;
                case "3":
                    back();
                    break;
                case "0":
                    exitProgram();
                    break;
                default:
                    System.out.println("\nYou input incorrect number! Try again:");
            }
        }
    }

    private void loginSubMenu() {
        System.out.println("Input your login:");
        String login = scanner.nextLine();
        System.out.println("Input your password:");
        String password = scanner.nextLine();
        Response<User> loginResponse = userService.login(login, password);
        if (loginResponse.isSuccess()) {
            System.out.println(loginResponse.getResultMessage());
            new OperationMenu(scanner, userService, ticketService, loginResponse.getData()).show();
        } else {
            System.out.println(loginResponse.getResultMessage());
        }
    }

    private void registerSubMenu() {
        String login;
        System.out.println("Input your login:");
        while (true) {
            login = scanner.nextLine();
            if (userService.find(login).getData() == null) break;
            System.out.println("User with this login is already registered in the database.\n" +
                    "Try input other login:");
        }
        System.out.println("Input your password:");
        String password = scanner.nextLine();
        User newUser = new User(login, password);
        Response<User> registerResponse = userService.register(newUser);
        if (registerResponse.isSuccess()) {
            System.out.println(registerResponse.getResultMessage());
            new OperationMenu(scanner, userService, ticketService, registerResponse.getData()).show();
        } else {
            System.out.println(registerResponse.getResultMessage());
        }
    }

    @Override
    public void back() {
        new DataStorageMenu().show();
    }
}
