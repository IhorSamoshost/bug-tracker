package dao;

import model.Ticket;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
//Реализация операций с базой тикетов, хранящейся в текстовом файле

public class TicketDaoInFileImpl implements TicketDao{
    private static final String PATH = "src/resources/tickets.txt";
    private List<Ticket> tickets = new ArrayList<>();
    public TicketDaoInFileImpl() {
    }

    @Override
    public void updateTicket(Ticket oldTicket, Ticket newTicket) {

    }

    @Override
    public void saveTicket(Ticket ticket) {
        File file = new File(PATH);
        ObjectOutputStream out;

    }

    @Override
    public Ticket getTicketByName(String ticketName) {

        try(ObjectInputStream locFile = new ObjectInputStream(new FileInputStream(PATH))){
            boolean eof = false;
            while(!eof){
                try{
                    Ticket ticket = (Ticket) locFile.readObject();
                    this.tickets.add(ticket);
                }catch (EOFException eofException){
                    eof = true;
                }
            }
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        for(Ticket ticket: this.tickets){
            if(ticket.getName().equals(ticketName)){
                return ticket;
            }
        }
        return null;
    }

    @Override
    public List<Ticket> getAll() {
        try(ObjectInputStream locFile = new ObjectInputStream(new FileInputStream(PATH))){
            boolean eof = false;
            while(!eof){
                try{
                    Ticket ticket = (Ticket) locFile.readObject();
                    this.tickets.add(ticket);
                }catch (EOFException eofException){
                    eof = true;
                }
            }
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return this.tickets;
    }

    @Override
    public Ticket deleteTicket(Ticket ticket) {
        return null;
    }

    @Override
    public Ticket deleteTicket(String name) {
        return null;
    }

static class AppendableObjectOutputStream extends ObjectOutputStream {

    public AppendableObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    @Override
    protected void writeStreamHeader() throws IOException {
        reset();
    }

}
}


