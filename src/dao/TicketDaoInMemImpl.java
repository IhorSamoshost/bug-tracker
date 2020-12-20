package dao;

import model.Ticket;

import java.util.*;

//Реализация операций с базой тикетов, хранящейся в оперативной памяти (in RAM)

//comments -> HashMap realisation

public class TicketDaoInMemImpl implements TicketDao {
    //    private Map<String, Ticket> tickets = new HashMap<>();
    private List<Ticket> tickets = new ArrayList<>();

    @Override
    public void saveTicket(Ticket ticket) {
//        this.tickets.putIfAbsent(ticket.getName(), ticket);
        if (!this.tickets.contains(ticket)) {
            this.tickets.add(ticket);
        }
    }

    @Override
    public Ticket getTicketByName(String ticketName) {
//        return this.tickets.get(ticketName);
        for (Ticket ticket : this.tickets) {
            if (ticket.getName().equals(ticketName)) {
                return ticket;
            }
        }
        return null;
    }

    @Override
    public List<Ticket> getAll() {
//        return new ArrayList<>(this.tickets.values());
        return tickets;
    }

    @Override
    public Ticket deleteTicket(Ticket ticket) {
//        return deleteTicket(ticket.getName());
        return this.tickets.remove(ticket) ? ticket : null;
    }

    @Override
    public Ticket deleteTicket(String name) {
//        return this.tickets.remove(name);
        for (int i = 0; i < this.tickets.size(); i++) {
            if (this.tickets.get(i).getName().equals(name)) {
                return this.tickets.remove(i);
            }
        }
        return null;
    }

    @Override
    public void updateTicket(Ticket oldTicket, Ticket newTicket) {
//        if (this.tickets.containsKey(oldTicket.getName())) {
//            this.tickets.remove(oldTicket.getName());
//            this.tickets.put(newTicket.getName(), newTicket);
//        }
        if (this.tickets.contains(oldTicket)) {
            int index = this.tickets.indexOf(oldTicket);
            this.tickets.set(index, newTicket);
        }
    }
}
