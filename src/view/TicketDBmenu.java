package view;

import model.Ticket;
import model.User;
import service.TicketService;
import service.UserService;

import java.util.Scanner;

public class TicketDBmenu implements Menu {
    private Scanner scanner;
    private UserService userService;
    private TicketService ticketService;
    private User user;

    String[] ticketOperations = new String[]{
            "1. Create a ticket",
            "2. Update ticket info",
            "3. Find ticket",
            "4. Find all tickets",
            "5. Find all tickets by user",
            "6. Find each user's most time expensive tasks",
            "7. Delete ticket",
            "8. Back to operation type selection menu",
            "0. Exit"
    };

    public TicketDBmenu(Scanner scanner, UserService userService, TicketService ticketService, User user) {
        this.scanner = scanner;
        this.userService = userService;
        this.ticketService = ticketService;
        this.user = user;
    }

    @Override
    public void show() {
        System.out.println("\nTo select the operation with user info enter the corresponding number:");
        while (true) {
            for (String op : ticketOperations) {
                System.out.println(op);
            }
            System.out.println("Input your selected operation's number: ");
            switch (scanner.nextLine()) {
                case "1":
                    createSubMenu();
                    break;
                case "2":
                    editSubMenu();
                    break;
                case "3":
                    ticketService.find(receiveTicketInfoSubMenu());
                    break;
                case "4":
                    ticketService.findAll();
                    break;
                case "5":
                    ticketService.findAllByUser(receiveUserInfoSubMenu());
                    break;
                case "6":
                    ticketService.findEachUserMostTimeExpensiveTasks();
                    break;
                case "7":
                    ticketService.delete(ticketService.find(receiveTicketInfoSubMenu()).getData().getTicketName());
                    break;
                case "8":
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

    private void createSubMenu() {
        ticketService.create(new Ticket());
    }

    private void editSubMenu() {
        ticketService.edit(new Ticket(), new Ticket());
    }

    public String receiveTicketInfoSubMenu() {
        System.out.println("Input ticket name: ");
        return scanner.nextLine();
    }

    public String receiveUserInfoSubMenu() {
        System.out.println("Input user name: ");
        return scanner.nextLine();
    }

    @Override
    public void back() {
        new OperationMenu(scanner, userService, ticketService, user).show();
    }
}
