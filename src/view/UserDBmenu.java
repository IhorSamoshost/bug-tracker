package view;

import model.User;
import service.TicketService;
import service.UserService;

import java.util.List;
import java.util.Scanner;

public class UserDBmenu implements Menu {
    private final Scanner scanner;
    private final UserService userService;
    private final TicketService ticketService;
    private final User user;

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
        while (true) {
            System.out.println("\nTo select the operation with user info enter the corresponding number: ");
            for (String op : userOperations) {
                System.out.println(op);
            }
            System.out.println("Input your selected operation's number: ");
            switch (scanner.nextLine()) {
                case "1":
                    editSubMenu();
                    break;
                case "2":
                    findSubMenu();
                    break;
                case "3":
                    Response<List<User>> findAllResponse = userService.findAll();
                    System.out.println(findAllResponse.getResultMessage());
                    break;
                case "4":
                    deleteSubMenu();
                    break;
                case "5":
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

    private void editSubMenu() {
        String oldUserName;
        User oldUser;
        do {
            System.out.println("Input name of user to edit:");
            oldUserName = scanner.nextLine();
            oldUser = userService.getUserDao().getUserByName(oldUserName);
            if (oldUser == null) {
                System.out.printf("There is no user with name '%s' in the database.\n" +
                        "Input new name of user to edit:\n", oldUserName);
            }
        } while (oldUser == null);
        System.out.println("Input new name of user:");
        String newUserName;
        while (true) {
            newUserName = scanner.nextLine();
            if (userService.getUserDao().getUserByName(newUserName) == null) break;
            System.out.printf("User with name '%s' is already registered in the database.\n" +
                    "Try input other name:\n", newUserName);
        }
        System.out.println("Input new password of user:");
        String newPassword = scanner.nextLine();
        User newUser = new User(newUserName, newPassword);
        Response<User> editResponse = userService.edit(oldUser, newUser);
        System.out.println(editResponse.getResultMessage());
    }

    private void findSubMenu() {
        System.out.println("Input name of user to find:");
        String userName = scanner.nextLine();
        Response<User> findResponse = userService.find(userName);
        System.out.println(findResponse.getResultMessage() + "\n" +
                findResponse.getData());
    }

    private void deleteSubMenu() {
        System.out.println("Input name of user to delete:");
        String userName = scanner.nextLine();
        Response<User> deleteResponse = userService.delete(userName);
        System.out.println(deleteResponse.getResultMessage());
    }

    @Override
    public void back() {
        new OperationMenu(scanner, userService, ticketService, user).show();
    }
}
