package view;

import model.User;
import service.TicketService;
import service.UserService;

import java.util.Scanner;

public class LoginMenu implements Menu {
    private Scanner scanner;
    private UserService userService;
    private TicketService ticketService;

    String[] enterItems = new String[]{
            "1. Login of a registered user",
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
        System.out.println("To select a method for logging in the application,\n" +
                "input the corresponding number:");
        while (true) {
            for (String item : enterItems) {
                System.out.println(item);
            }
            System.out.println("Input your selected item's number: ");
            switch (scanner.nextLine()) {
                case "1":
                    loginSubMenu();
                    break;
                case "2":
                    Response<User> registerResponse = userService.register();
                    if (registerResponse.isSuccess()) {
                        User user = registerResponse.getData();
                        System.out.println(registerResponse.getResultMessage());
                        new OperationMenu(scanner, userService, ticketService, user).show();
                    } else {
                        System.out.println(registerResponse.getResultMessage());
                    }
                    break;
                case "3":
                    back();
                    break;
                case "0":
                    exitProgram();
                    break;
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
            User user = loginResponse.getData();
            System.out.println(loginResponse.getResultMessage());
            new OperationMenu(scanner, userService, ticketService, user).show();
        } else {
            System.out.println(loginResponse.getResultMessage());
        }
    }

    @Override
    public void back() {
        new DataStorageMenu().show();
    }
}
