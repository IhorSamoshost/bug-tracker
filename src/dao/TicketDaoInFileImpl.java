package dao;

import model.Ticket;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
//Реализация операций с базой тикетов, хранящейся в текстовом файле

public class TicketDaoInFileImpl implements TicketDao {
    private static final String PATH = "src/resources/tickets.txt";

    public TicketDaoInFileImpl() {
    }

    @Override
    public void saveTicket(Ticket ticket) {
        File file = new File(PATH);
        if (!containsInFile(file, ticket)) {
            writeToFile(file, ticket);
        }
    }

    private boolean containsInFile(File file, Ticket ticket) {
        if (file.exists() && file.length() > 0) {
            List<Ticket> tickets = getTickets();
            return tickets.contains(ticket);
        }
        return false;
    }

    private void writeToFile(File file, Ticket ticket) {
        try (ObjectOutputStream locFile = createStream(file)) {
            locFile.writeObject(ticket);
            locFile.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ObjectOutputStream createStream(File file) throws IOException {
        if (!file.exists() || file.length() == 0) {
            return new ObjectOutputStream(new FileOutputStream(file, true));
        } else {
            return new AppendableObjectOutputStream(new FileOutputStream(file, true));
        }
    }

    private void writeToFile(List<Ticket> tickets) {
        try (ObjectOutputStream locFile = new ObjectOutputStream(new FileOutputStream(PATH))) {
            for (Ticket ticket : tickets) {
                locFile.writeObject(ticket);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Ticket getTicketByName(String ticketName) {
        List<Ticket> tickets = getTickets();
        for (Ticket ticket : tickets) {
            if (ticket.getName().equals(ticketName)) {
                return ticket;
            }
        }
        return null;
    }

    @Override
    public List<Ticket> getAll() {
        return getTickets();
    }

    @Override
    public Ticket deleteTicket(Ticket ticket) {
        List<Ticket> tickets = getTickets();
        tickets.remove(ticket);
        writeToFile(tickets);
        return ticket;
    }

    @Override
    public Ticket deleteTicket(String name) {
        List<Ticket> tickets = getTickets();
        Ticket temp = null;
        for (Ticket ticket : tickets) {
            if (ticket.getName().equals(name)) {
                temp = ticket;
            }
        }
        tickets.remove(temp);
        writeToFile(tickets);
        return temp;
    }

    @Override
    public void updateTicket(Ticket oldTicket, Ticket newTicket) {
        List<Ticket> tickets = getTickets();
        if (tickets.contains(newTicket)) {
            return;
        }
        if (tickets.contains(oldTicket)) {
            int index = tickets.indexOf(oldTicket);
            tickets.set(index, newTicket);
            writeToFile(tickets);
        }
    }

    private List<Ticket> getTickets() {
        List<Ticket> tickets = new ArrayList<>();
        try (ObjectInputStream locFile = new ObjectInputStream(new FileInputStream(PATH))) {
            boolean eof = false;
            while (!eof) {
                try {
                    Ticket ticket = (Ticket) locFile.readObject();
                    tickets.add(ticket);
                } catch (EOFException eofException) {
                    eof = true;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tickets;
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


