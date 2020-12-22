package service;

import dao.TicketDao;
import model.Ticket;
import view.Response;
import java.util.List;

public interface TicketService {
    Response<Ticket> create(Ticket newTicket);
    Response<Ticket> edit(Ticket oldTicket, Ticket newTicket);
    Response<Ticket> find(String ticketName);
    Response<List<Ticket>> findAll();
    Response<Ticket> delete(String ticketName);
    Response<List<Ticket>> findAllByUser(String userName);
    Response<List<Ticket>> findEachUserMostTimeExpensiveTasks ();
    TicketDao getTicketDao();


}

