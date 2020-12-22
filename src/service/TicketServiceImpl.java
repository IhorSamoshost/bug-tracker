package service;

import dao.TicketDaoInDbImpl;
import model.User;
import view.Response;
import java.util.List;

public class TicketServiceImpl implements TicketService {

    @Override
    public Response<Ticket> create(Ticket newTicket) {
        return null;
    }

    @Override
    public Response<Ticket> edit(Ticket oldTicket, Ticket newTicket) {
        return null;
    }

    @Override
    public Response<Ticket> find(String ticketName) {
        return null;
    }

    @Override
    public Response<List<Ticket>> findAll() {
        return null;
    }

    @Override
    public Response<Ticket> delete(String ticketName) {
        return null;
    }

    @Override
    public Response<List<Ticket>> findAllByUser(String userName) {
        return null;
    }

    @Override
    public Response<List<Ticket>> findEachUserMostTimeExpensiveTasks() {
        return null;
    }

    @Override
    public TicketDao getTicketDao() {
        return null;
    }
}
