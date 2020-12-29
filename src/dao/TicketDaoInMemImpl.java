package dao;

import model.Ticket;
import java.util.*;

public class TicketDaoInMemImpl implements TicketDao {
    private List<Ticket> tickets = new ArrayList<>();

    @Override
    public void saveTicket(Ticket ticket) {
        if (!this.tickets.contains(ticket)) {
            this.tickets.add(ticket);
        }
    }

    @Override
    public Ticket getTicketByName(String ticketName) {
        for (Ticket ticket : this.tickets) {
            if (ticket.getName().equals(ticketName)) {
                return ticket;
            }
        }
        return null;
    }

    @Override
    public List<Ticket> getAll() {
        return this.tickets;
    }

    @Override
    public Ticket deleteTicket(Ticket ticket) {
        return this.tickets.remove(ticket) ? ticket : null;
    }

    @Override
    public Ticket deleteTicket(String name) {
        for (int i = 0; i < this.tickets.size(); i++) {
            if (this.tickets.get(i).getName().equals(name)) {
                return this.tickets.remove(i);
            }
        }
        return null;
    }

    @Override
    public void updateTicket(Ticket oldTicket, Ticket newTicket) {
        if (this.tickets.contains(oldTicket)) {
            int index = this.tickets.indexOf(oldTicket);
            this.tickets.set(index, newTicket);
        }
    }
}
