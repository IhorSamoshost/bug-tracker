package view;

import model.User;
import service.TicketService;
import service.UserService;

import java.util.Scanner;

public class UserDBmenu implements Menu {
    private Scanner scanner;
    private UserService userService;
    private TicketService ticketService;
    private User user;

    String[] userOperations = new String[]{
            "1. Update user info",
            "2. Find user",
            "3. Find all users",
            "4. Delete user",
            "5. Back to operation type selection menu",
            "0. Exit"
    };

    public UserDBmenu(Scanner scanner, UserService userService, TicketService ticketService, User user) {
        this.scanner = scanner;
        this.userService = userService;
        this.ticketService = ticketService;
        this.user = user;
    }

    @Override
    public void show() {
        System.out.println("To select the operation with user info enter the corresponding number:");
        while (true) {
            for (String op : userOperations) {
                System.out.println(op);
            }
            System.out.println("Input your selected operation's number: ");
            switch (scanner.nextLine()) {
                case "1":
                    userService.edit(user.getUserName());
                    break;
                case "2":
                    userService.find(user.getUserName());
                    break;
                case "3":
                    userService.findAll();
                    break;
                case "4":
                    userService.delete(user.getUserName());
                    break;
                case "5":
                    back();
                    break;
                case "0":
                    exitProgram();
                    break;
            }
        }
    }

    @Override
    public void back() {
        new OperationMenu(scanner, userService, ticketService, user).show();
    }
}
