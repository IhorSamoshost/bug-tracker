package view;

import dao.*;
import service.TicketServiceImpl;
import service.UserServiceImpl;

import java.sql.*;
import java.util.Scanner;

public class DataStorageMenu implements Menu {
    private final Scanner scanner = new Scanner(System.in);

    String[] dataStorageMethods = new String[]{
            "1. Data storage in RAM",
            "2. Data storage in a text file",
            "3. Data storage in a database (MySQL)",
            "0. Exit"
    };

    @Override
    public void show() {
        System.out.println("\nTo select the method of storing data in the computer memory,\n" +
                "input the appropriate number:");
        while (true) {
            for (String method : dataStorageMethods) {
                System.out.println(method);
            }
            System.out.println("Input your selected item's number: ");
            switch (scanner.nextLine()) {
                case "1":
                    new LoginMenu(scanner,
                            new UserServiceImpl(new UserDaoInMemImpl()),
                            new TicketServiceImpl(new TicketDaoInMemImpl())).show();
                    break;
                case "2":
                    new LoginMenu(scanner,
                            new UserServiceImpl(new UserDaoInFileImpl()),
                            new TicketServiceImpl(new TicketDaoInFileImpl())).show();
                    break;
                case "3":
                    new LoginMenu(scanner,
                            new UserServiceImpl(new UserDaoInDbImpl(connection())),
                            new TicketServiceImpl(new TicketDaoInDbImpl(connection()))).show();
                    break;
                case "0":
                    back();
                    break;
                default:
                    System.out.println("\nYou input incorrect number! Try again:");
            }
        }
    }

    @Override
    public void back() {
        exitProgram();
    }

    public Connection connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/bug_tracker?serverTimezone=UTC",
                            "root", "root");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("ClassNotFoundException caught");
            e.printStackTrace();
        }
        return null;
    }
}
