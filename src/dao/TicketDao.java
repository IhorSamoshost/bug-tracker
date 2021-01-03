package dao;

import model.Ticket;

import java.util.List;

public interface TicketDao {
    void saveTicket(Ticket ticket);

    Ticket getTicketByName(String name);

    List<Ticket> getAll();

    Ticket deleteTicket(Ticket ticket);

    Ticket deleteTicket(String name);

    void updateTicket(Ticket oldTicket, Ticket newTicket);

}
