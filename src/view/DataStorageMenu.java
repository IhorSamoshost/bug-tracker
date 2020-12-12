package view;

import dao.*;
import service.TicketServiceImpl;
import service.UserServiceImpl;

import java.util.Scanner;

public class DataStorageMenu implements Menu {
    private Scanner scanner = new Scanner(System.in);

    String[] dataStorageMethods = new String[]{
            "1. Data storage in RAM",
            "2. Data storage in a text file",
            "3. Data storage in a database (MySQL)",
            "0. Exit"
    };

    @Override
    public void show() {
        System.out.println("To select the method of storing data in the computer memory,\n" +
                "enter the appropriate number:");
        while (true) {
            for (String method : dataStorageMethods) {
                System.out.println(method);
            }
            System.out.println("Enter your selected item's number: ");
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
                            new UserServiceImpl(new UserDaoInDbImpl()),
                            new TicketServiceImpl(new TicketDaoInDbImpl())).show();
                    break;
                case "0":
                    back();
                    break;
            }
        }

    }

    @Override
    public void back() {
        exitProgram();
    }
}
