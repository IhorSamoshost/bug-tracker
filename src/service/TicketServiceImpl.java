package service;

import dao.TicketDao;
import model.Ticket;
import model.User;
import view.Response;

import java.util.*;
import java.util.stream.Collectors;

public class TicketServiceImpl implements TicketService {

    TicketDao ticketDao;

    public TicketServiceImpl(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Override
    public Response<Ticket> create(Ticket newTicket) {
        ticketDao.saveTicket(newTicket);
        Response<Ticket> ticket = find(newTicket.getName());
        return ticket.isSuccess()
                ? new Response<>(ticket.getData(), true, "Ticket successfully created")
                : new Response<>(ticket.getData(), false, "Ticket is not created");
    }

    @Override
    public Response<Ticket> edit(Ticket oldTicket, Ticket newTicket) {
        ticketDao.updateTicket(oldTicket, newTicket);
        Response<Ticket> ticket = find(newTicket.getName());
        return ticket.isSuccess()
                ? new Response<>(ticket.getData(), true, "Ticket edited successfully")
                : new Response<>(ticket.getData(), false, "Ticket  is not edited");
    }

    @Override
    public Response<Ticket> find(String ticketName) {
        Ticket ticket = ticketDao.getTicketByName(ticketName);
        return (ticket == null)
                ? new Response<>(ticket, false, String.format("Ticket '%s' is not found ", ticketName))
                : new Response<>(ticket, true, String.format("Ticket '%s' is found: ", ticketName));
    }

    @Override
    public Response<List<Ticket>> findAll() {
        List<Ticket> ticketList = ticketDao.getAll();
        return (ticketList == null || ticketList.isEmpty())
                ? new Response<>(ticketList, false, "DataBase is not found")
                : new Response<>(ticketList, true, "There are tickets found in database:");
    }

    @Override
    public Response<Ticket> delete(String ticketName) {
        ticketDao.deleteTicket(ticketName);
        Ticket controlTicket = ticketDao.getTicketByName(ticketName);
        return (controlTicket == null)
                ? new Response<>(controlTicket, true, "Ticket successfully deleted")
                : new Response<>(controlTicket, false, "Ticket is not found");
    }

    @Override
    public Response<List<Ticket>> findAllByUser(String userName) {
        List<Ticket> ticketList = ticketDao.getAll();
        List<Ticket> filterList = ticketList.stream()
                .filter(ticket -> ticket.getAssignee().getUserName().equals(userName))
                .collect(Collectors.toList());
        return (filterList.isEmpty())
                ? new Response<>(filterList, false,
                String.format("There are no tickets for assignee '%s' in database", userName))
                : new Response<>(filterList, true,
                String.format("There are tickets for assignee '%s' in database: ", userName));
    }

    @Override
    public Response<Map<User, Integer>> getEstimatedTimeForEachAssignee() {
        List<Ticket> ticketList = ticketDao.getAll();
        Map<User, Integer> eachUserEstimatedTimeMap =
                ticketList.stream().collect(Collectors
                        .groupingBy(Ticket::getAssignee, Collectors.summingInt(Ticket::getEstimatedTime)));
        return eachUserEstimatedTimeMap == null
                ? new Response<>(eachUserEstimatedTimeMap, false, "The database is empty")
                : new Response<>(eachUserEstimatedTimeMap, true,
                "Total estimated time to execute all tasks for each assignee: ");
    }

    @Override
    public Response<List<Ticket>> findMostTimeExpensiveTasksInDB(int count) {
        List<Ticket> resultList = ticketDao.getAll().stream()
                .sorted(Comparator.comparingInt(Ticket::getEstimatedTime).reversed())
                .limit(count).collect(Collectors.toList());
        return resultList.isEmpty()
                ? new Response<>(resultList, false, "Any most time expensive task is not found")
                : new Response<>(resultList, true, "Most time expensive tasks in database: ");
    }

    public Response<List<Ticket>> findEachUserLatestDeadlineTasks() {
        List<Ticket> tickets = ticketDao.getAll();
        if (tickets == null) {
            return new Response<>(tickets, false, "There are no tickets in DB! ");
        }
        List<Ticket> eachUserLatestTasks = tickets.stream()
                .collect(Collectors.groupingBy(Ticket::getAssignee, Collectors.maxBy(Comparator.comparing(Ticket::getDeadline))))
                .values().stream().map(Optional::get).collect(Collectors.toList());
        return new Response<>(eachUserLatestTasks, true, "Latest deadline tasks for each assignee are found from DB: ");
    }
}