package view;

import model.Ticket;
import model.User;
import model.enums.Priority;
import model.enums.Status;
import service.TicketService;
import service.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TicketDBmenu implements Menu {
    private final Scanner scanner;
    private final UserService userService;
    private final TicketService ticketService;
    private final User user;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    String[] ticketOperations = new String[]{
            "1. Create a ticket",
            "2. Update ticket info",
            "3. Find ticket",
            "4. Find all tickets",
            "5. Find all tickets by user",
            "6. Find most time expensive tasks in DB",
            "7. Delete ticket",
            "8. Find each user's task with latest deadline",
            "9. Get total estimated time to execute all tasks for each assignee",
            "10. Back to operation type selection menu",
            "0. Exit"
    };

    public TicketDBmenu(Scanner scanner, UserService userService, TicketService ticketService, User user) {
        this.scanner = scanner.reset();
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
                    Response<Ticket> createTicketResponse = createSubMenu();
                    System.out.println(createTicketResponse.getResultMessage());
                    if (createTicketResponse.isSuccess()) {
                        System.out.println(createTicketResponse.getData());
                    }
                    break;
                case "2":
                    Response<Ticket> editTicketResponse = editSubMenu();
                    System.out.println(editTicketResponse.getResultMessage());
                    if (editTicketResponse.isSuccess()) {
                        System.out.println(editTicketResponse.getData());
                    }
                    break;
                case "3":
                    Response<Ticket> foundTicketResponse = ticketService.find(receiveTicketNameSubMenu());
                    System.out.println(foundTicketResponse.getResultMessage());
                    if (foundTicketResponse.isSuccess()) {
                        System.out.println(foundTicketResponse.getData());
                    }
                    break;
                case "4":
                    Response<List<Ticket>> allTicketsResponse = ticketService.findAll();
                    System.out.println(allTicketsResponse.getResultMessage());
                    if (allTicketsResponse.isSuccess()) {
                        allTicketsResponse.getData().forEach(System.out::println);
                    }
                    break;
                case "5":
                    Response<List<Ticket>> userTicketsResponse = ticketService.findAllByUser(receiveUserNameSubMenu());
                    System.out.println(userTicketsResponse.getResultMessage());
                    if (userTicketsResponse.isSuccess()) {
                        userTicketsResponse.getData().forEach(System.out::println);
                    }
                    break;
                case "6":
                    Response<List<Ticket>> mostTimeExpensiveTasksResponse = recieveMostTimeExpensiveTasksSubMenu();
                    System.out.println(mostTimeExpensiveTasksResponse.getResultMessage());
                    if (mostTimeExpensiveTasksResponse.isSuccess()) {
                        mostTimeExpensiveTasksResponse.getData().forEach(System.out::println);
                    }
                    break;
                case "7":
                    Response<Ticket> deleteTicketResponse = ticketService.delete(receiveTicketNameSubMenu());
                    System.out.println(deleteTicketResponse.getResultMessage());
                    break;
                case "8":
                    Response<List<Ticket>> latestDeadlineTasksResponse = ticketService.findEachUserLatestDeadlineTasks();
                    System.out.println(latestDeadlineTasksResponse.getResultMessage());
                    if (latestDeadlineTasksResponse.isSuccess()) {
                        latestDeadlineTasksResponse.getData().forEach(System.out::println);
                    }
                    break;
                case "9":
                    Response<Map<User, Integer>> estimatedTimeForEachAssigneeResponse
                            = ticketService.getEstimatedTimeForEachAssignee();
                    System.out.println(estimatedTimeForEachAssigneeResponse.getResultMessage());
                    if (estimatedTimeForEachAssigneeResponse.isSuccess()) {
                        estimatedTimeForEachAssigneeResponse.getData()
                                .forEach((key, value) -> System.out.println(key + ": " + value + " hours"));
                    }
                    break;
                case "10":
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

    private Response<Ticket> createSubMenu() {
        Ticket ticket = receiveTicketFromConsole();
        ticket.setStatus(Status.TODO);
        return ticketService.create(ticket);
    }

    private Response<Ticket> editSubMenu() {
        Ticket ticketToEdit;
        System.out.println("Input the name of ticket to edit: ");
        String oldTicketName = scanner.nextLine();
        ticketToEdit = ticketService.find(oldTicketName).getData();
        if (ticketToEdit == null) {
            System.out.printf("There is no ticket with name '%s' in the database", oldTicketName);
            return new Response<>(null, false, "Incorrect ticket name to edit!");
        }
        System.out.println("The ticket you want to change: \n" + ticketToEdit);
        while (true) {
            System.out.println("Do you want to continue ticket updating? (y/n)");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("n")) {
                return new Response<>(null, false, "Refusal to edit a ticket!");
            } else {
                if (answer.equalsIgnoreCase("y")) {
                    break;
                } else {
                    System.out.println("You input incorrect answer! Try again!");
                }
            }
        }
        Ticket newTicket = receiveTicketFromConsole();
        newTicket.setStatus(chooseStatusSubMenu());
        return ticketService.edit(ticketToEdit, newTicket);
    }

    private Ticket receiveTicketFromConsole() {
        Ticket ticket = new Ticket();
        System.out.println("Input ticket name: ");
        ticket.setName(scanner.nextLine());
        System.out.println("Input ticket description: ");
        ticket.setDescription(scanner.nextLine());
        System.out.println("Input name of ticket's assignee: ");
        User assignee = userService.find(scanner.nextLine()).getData();
        while (assignee == null) {
            System.out.println("Incorrect assignee's name! Input other name of ticket's assignee: ");
            assignee = userService.find(scanner.nextLine()).getData();
        }
        ticket.setAssignee(assignee);
        ticket.setReporter(user);
        ticket.setPriority(choosePrioritySubMenu());
        System.out.println("Input ticket spent time: ");
        ticket.setSpentTime(scanner.nextInt());
        System.out.println("Input ticket estimated time: ");
        ticket.setEstimatedTime(scanner.nextInt());
        scanner.nextLine();
        scanner.reset();
        System.out.println("Input starting date of work on the ticket (yyyy-MM-dd): ");
        ticket.setStartingDate(getCalendarFromConsole());
        System.out.println("Input deadline date of work on the ticket (yyyy-MM-dd): ");
        Calendar deadLine = getCalendarFromConsole();
        while (deadLine.before(ticket.getStartingDate())) {
            System.out.println("Deadline date of work can't be early than it's starting date!");
            System.out.println("Input deadline date of work again (yyyy-MM-dd):");
            deadLine = getCalendarFromConsole();
        }
        ticket.setDeadline(deadLine);
        return ticket;
    }

    private Priority choosePrioritySubMenu() {
        while (true) {
            System.out.println("\nChoose ticket priority's level: ");
            for (int i = 1; i <= Priority.values().length; i++) {
                System.out.println(i + " " + Priority.values()[i - 1]);
            }
            System.out.println("Input your selected prioriry's number: ");
            switch (scanner.nextLine()) {
                case "1":
                    return Priority.EXTRA_HIGH;
                case "2":
                    return Priority.HIGH;
                case "3":
                    return Priority.MIDDLE;
                case "4":
                    return Priority.LOW;
                default:
                    System.out.println("\nIncorrect number! Try again!");
            }
        }
    }

    private Status chooseStatusSubMenu() {
        while (true) {
            System.out.println("\nChoose ticket execution status: ");
            for (int i = 1; i <= Status.values().length; i++) {
                System.out.println(i + " " + Status.values()[i - 1]);
            }
            System.out.println("Input your selected status number: ");
            switch (scanner.nextLine()) {
                case "1":
                    return Status.TODO;
                case "2":
                    return Status.IN_PROGRESS;
                case "3":
                    return Status.IN_CONTROL_REVIEW;
                case "4":
                    return Status.NEED_REFACTORING;
                case "5":
                    return Status.RESOLVED;
                default:
                    System.out.println("\nIncorrect number! Try again!");
            }
        }
    }

    private String receiveTicketNameSubMenu() {
        System.out.println("Input ticket name: ");
        return scanner.nextLine();
    }

    public String receiveUserNameSubMenu() {
        System.out.println("Input user name: ");
        return scanner.nextLine();
    }

    private Calendar getCalendarFromConsole() {
        Calendar calendar = new GregorianCalendar();
        while (true) {
            String stringDateFromConsol = scanner.nextLine();
            try {
                Date date = formatter.parse(stringDateFromConsol);
                calendar.setTime(date);
                return calendar;
            } catch (ParseException e) {
                System.out.println("Incorrect date input!");
                System.out.println("Input date again (yyyy-MM-dd):");
            }
        }
    }

    private Response<List<Ticket>> recieveMostTimeExpensiveTasksSubMenu() {
        System.out.println("Input quantity of most time expensive tasks in DB you want to get: ");
        int tasksQuantity = scanner.nextInt();
        scanner.nextLine();
        scanner.reset();
        return ticketService.findMostTimeExpensiveTasksInDB(tasksQuantity);
    }

    @Override
    public void back() {
        new OperationMenu(scanner, userService, ticketService, user).show();
    }
}