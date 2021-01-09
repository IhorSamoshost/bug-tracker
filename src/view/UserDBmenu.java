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
    private User user;

    String[] userOperations = new String[]{
            "1. Find user",
            "2. Update user info",
            "3. Delete user",
            "4. Find all users",
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
            System.out.println("\nTo select the operation with user info enter the corresponding number:");
            for (String op : userOperations) {
                System.out.println(op);
            }
            System.out.println("Input your selected operation's number: ");
            switch (scanner.nextLine()) {
                case "1":
                    findSubMenu();
                    break;
                case "2":
                    editSubMenu();
                    break;
                case "3":
                    deleteSubMenu();
                    break;
                case "4":
                    Response<List<User>> findAllUsersResponse = userService.findAll();
                    System.out.println(findAllUsersResponse.getResultMessage() + "\n" + findAllUsersResponse.getData());
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

    private void findSubMenu() {
        System.out.println("Input name of user to find:");
        String userName = scanner.nextLine();
        Response<User> foundUserResponse = userService.find(userName);
        System.out.println(foundUserResponse.getResultMessage());
        if (foundUserResponse.getData() != null) System.out.println(foundUserResponse.getData());
    }

    private void editSubMenu() {
        String oldUserName;
        User oldUser;
        System.out.println("Input name of user to edit:");
        oldUserName = scanner.nextLine();
        oldUser = userService.find(oldUserName).getData();
        if (oldUser == null) {
            System.out.printf("There is no user with name '%s' in the database\n", oldUserName);
            return;
        }
        System.out.println("The user you want to change: \n" + oldUser);
        while (true) {
            System.out.println("Do you want to continue user updating? (y/n)");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("n")) {
                return;
            } else {
                if (answer.equalsIgnoreCase("y")) {
                    break;
                } else {
                    System.out.println("You input incorrect answer! Try again!");
                }
            }
        }
        System.out.println("Input new name of user:");
        String newUserName;
        while (true) {
            newUserName = scanner.nextLine();
            if (userService.find(newUserName).getData() == null || oldUserName.equals(newUserName)) break;
            System.out.printf("User with name '%s' is already registered in the database.\n" +
                    "Try input other name:\n", newUserName);
        }
        System.out.println("Input new password of user:");
        String newPassword = scanner.nextLine();
        User newUser = new User(newUserName, newPassword);
        Response<User> editUserResponse = userService.edit(oldUser, newUser);
        if (editUserResponse.isSuccess() && user.getUserName().equals(oldUserName)) {
            user = editUserResponse.getData();
        }
        System.out.println(editUserResponse.getResultMessage());
    }

    private void deleteSubMenu() {
        System.out.println("Input name of user to delete:");
        String userName = scanner.nextLine();
        Response<User> deleteResponse = userService.delete(userName);
        System.out.println(deleteResponse.getResultMessage());
        if (userService.find(user.getUserName()).getData() == null) {
            System.out.printf("User '%s' is deleted from users' database and so you should enter into system again!", user.getUserName());
            new LoginMenu(scanner, userService, ticketService).show();
        }
    }

    @Override
    public void back() {
        new OperationMenu(scanner, userService, ticketService, user).show();
    }
}
