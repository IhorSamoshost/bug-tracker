package service;

import dao.TicketDao;
import model.Ticket;
import model.User;
import view.Response;

import java.util.List;
import java.util.Map;

public interface TicketService {
    Response<Ticket> create(Ticket newTicket);

    Response<Ticket> edit(Ticket oldTicket, Ticket newTicket);

    Response<Ticket> find(String ticketName);

    Response<List<Ticket>> findAll();

    Response<Ticket> delete(String ticketName);

    Response<List<Ticket>> findAllByUser(String userName);

    Map<User, Response<Integer>> getUserAllEstimatedTime();

    Response<List<Ticket>> findEachUserMostTimeExpensiveTasks(int count);

    TicketDao getTicketDao();
}