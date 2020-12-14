package view;

import model.User;
import service.TicketService;
import service.UserService;

import java.util.Scanner;

public class OperationMenu implements Menu {
    private Scanner scanner;
    private UserService userService;
    private TicketService ticketService;
    private User user;

    String[] jobKinds = new String[]{
            "1. Operations with the user base",
            "2. Operations with the ticket base",
            "3. Back to login menu",
            "0. Exit"
    };

    public OperationMenu(Scanner scanner, UserService userService, TicketService ticketService, User user) {
        this.scanner = scanner;
        this.userService = userService;
        this.ticketService = ticketService;
        this.user = user;
    }

    @Override
    public void show() {
        System.out.println("\nTo select the operation type input the corresponding number:");
        while (true) {
            for (String kind : jobKinds) {
                System.out.println(kind);
            }
            System.out.println("Input your selected operation type's number: ");
            switch (scanner.nextLine()) {
                case "1":
                    new UserDBmenu(scanner, userService, ticketService, user).show();
                    break;
                case "2":
                    new TicketDBmenu(scanner, userService, ticketService, user).show();
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

    @Override
    public void back() {
        new LoginMenu(scanner, userService, ticketService).show();
    }
}
