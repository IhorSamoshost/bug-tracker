package dao;

import model.Ticket;

import java.util.*;

//Реализация операций с базой тикетов, хранящейся в оперативной памяти (in RAM)

public class TicketDaoInMemImpl implements TicketDao {
    private Map<String, Ticket> tickets = new HashMap<>();
    //private List<Ticket> tickets = new ArrayList<>();

    @Override
    public void saveTicket(Ticket ticket) {
        this.tickets.putIfAbsent(ticket.getName(), ticket);
//        if(!this.tickets.contains(ticket)) {
//            this.tickets.add(ticket);
//        }else{
//            System.out.println("The ticket "+ticket.getName().toUpperCase()+" cannot be added.");
//        }
    }

    @Override
    public Ticket getTicketByName(String ticketName) {
//        for (Ticket ticket: this.tickets){
//            if(ticket.getName().equals(ticketName)){
//                return ticket;
//            }
//        }
//        System.out.println("There is no such ticket.");
//        return null;
        if (this.tickets.containsKey(ticketName)) {
            return this.tickets.get(ticketName);
        }
        return null;
    }

    @Override
    public List<Ticket> getAll() {
        return new ArrayList<>(this.tickets.values());
    }

    //it's better to return boolean ???
    @Override
    public Ticket deleteTicket(Ticket ticket) {
        return deleteTicket(ticket.getName());
//        if(this.tickets.contains(ticket)){
//            this.tickets.remove(ticket);
//            return ticket;
//        }
//        return null;
    }

    @Override
    public Ticket deleteTicket(String name) {
        return this.tickets.remove(name);
    }

    @Override
    public void updateTicket(Ticket oldTicket, Ticket newTicket) {
        if (this.tickets.containsKey(oldTicket.getName())) {
            this.tickets.remove(oldTicket.getName());
            this.tickets.put(newTicket.getName(), newTicket);
        }

    }
}
