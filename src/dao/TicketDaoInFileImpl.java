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
    public void saveTicket(Ticket ticket) {
        File file = new File(PATH);
        ObjectOutputStream out;
        // Determine whether the file exists to determine which objectoutputstream to use
        if (file.isFile()) {
            try {
                out = new AppendableObjectOutputStream(new FileOutputStream(file,true));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("DDDDDDDDDD");


//        try(AppendableObjectOutputStream locFile = new AppendableObjectOutputStream(new FileOutputStream(PATH,true))){
//            locFile.writeObject(ticket);
//        }catch (IOException e){
//            e.printStackTrace();
//        }

        }
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

    private boolean append;
    private boolean initialized;
    private DataOutputStream dout;

    public AppendableObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    @Override
    protected void writeStreamHeader() throws IOException {

    }

}
}

//public class AppendableObjectOutputStream extends ObjectOutputStream {
//
//    private boolean append;
//    private boolean initialized;
//    private DataOutputStream dout;
//
//    protected AppendableObjectOutputStream(boolean append) throws IOException, SecurityException {
//        super();
//        this.append = append;
//        this.initialized = true;
//    }
//
//    public AppendableObjectOutputStream(OutputStream out, boolean append) throws IOException {
//        super(out);
//        this.append = append;
//        this.initialized = true;
//        this.dout = new DataOutputStream(out);
//        this.writeStreamHeader();
//    }
//
//    @Override
//    protected void writeStreamHeader() throws IOException {
//        if (!this.initialized || this.append) return;
//        if (dout != null) {
//            dout.writeShort(STREAM_MAGIC);
//            dout.writeShort(STREAM_VERSION);
//        }
//    }
//
//}


