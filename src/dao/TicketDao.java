package dao;

import model.Ticket;

import java.util.List;
//Содержит абстрактные и, возможно, дефолтные методы.
// Имплементируется классами этого уровня, реализующими операции для различных
// вариантов хранения базы тикетов в памяти компьютера

public interface TicketDao {
    void saveTicket(Ticket ticket);

    Ticket getTicketByName(String name);

    List<Ticket> getAll();

    Ticket deleteTicket(Ticket ticket);

    Ticket deleteTicket(String name);

    void updateTicket(Ticket oldTicket, Ticket newTicket);

}
