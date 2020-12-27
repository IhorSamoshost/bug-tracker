package service;

import dao.TicketDao;
import model.Ticket;
import model.User;
import view.Response;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class TicketServiceImpl implements TicketService {

    TicketDao ticketDao;

    public TicketServiceImpl(TicketDao TicketDao) {
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
                ? new Response<>(ticket, false, "Ticket  is not found")
                : new Response<>(ticket, true, "Successful");
    }

    @Override
    public Response<List<Ticket>> findAll() {
        List<Ticket> ticketList = ticketDao.getAll();
        return (ticketList == null)
                ? new Response<>(ticketList, false, "DataBase is  not found")
                : new Response<>(ticketList, true, "Successful");

    }

    @Override
    public Response<Ticket> delete(String ticketName) {
        Ticket ticket = ticketDao.deleteTicket(ticketDao.getTicketByName(ticketName));
        return (ticket == null)
                ? new Response<>(ticket, true, "Ticket successfully deleted")
                : new Response<>(ticket, false, "Ticket is not found");
    }

    @Override
    public Response<List<Ticket>> findAllByUser(String userName) {
        List<Ticket> ticketList = ticketDao.getAll();
        List<Ticket> filterList = ticketList.stream()
                .filter(ticket -> ticket.getAssignee().getUserName().equals(userName))
                .collect(Collectors.toList());
        return (filterList.isEmpty())
                ? new Response<>(filterList, false, "Ticket is not found")
                : new Response<>(filterList, true, "Ticket found successfully");

    }

    @Override
    public Map<User, Response<Integer>> getUserAllEstimatedTime() {
        List<Ticket> ticketList = ticketDao.getAll();
        Map<User, Integer> userMap = new HashMap<>();
        ticketList.forEach(ticket -> {
            User user = ticket.getAssignee();
            int estimatedTime = ticket.getEstimatedTime();
            if (userMap.containsKey(user))
                userMap.replace(user, userMap.get(user) + estimatedTime);
            else
                userMap.put(user, estimatedTime);
        });
        Map<User, Response<Integer>> resultMap = new HashMap<>();
        userMap.forEach((user, integer) -> {
            resultMap.put(user, (integer == 0)
                    ? new Response<>(integer, false, "Estimated time not found")
                    : new Response<>(integer, true, "Estimated time equally: "));
        });
        return resultMap;
    }

    @Override
    public Response<List<Ticket>> findEachUserMostTimeExpensiveTasks(int count) {
        List<Ticket> ticketList = ticketDao.getAll();
        List<Ticket> resultList = ticketList.stream()
                .sorted(Comparator.comparingInt(Ticket::getEstimatedTime))
                .collect(Collectors.toList()).subList(ticketList.size() - count - 1, ticketList.size() - 1);
        return (resultList.isEmpty())
                ? new Response<>(resultList, false, "Most time expensive tasks not found")
                : new Response<>(resultList, true, "Most time expensive equally: ");

    }

    @Override
    public TicketDao getTicketDao() {
        return ticketDao;
    }
}