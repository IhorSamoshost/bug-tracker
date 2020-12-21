package model;

import model.enums.Priority;
import model.enums.Status;

import java.util.Calendar;

public class TicketBuilder {
    private final Ticket ticket;

    public TicketBuilder() {
        this.ticket = new Ticket();
    }

    public TicketBuilder setName(String name){
        this.ticket.setName(name);
        return this;
    }

    public TicketBuilder setDescription(String description){
        this.ticket.setDescription(description);
        return this;
    }

    public TicketBuilder setAssignee(User assignee){
        this.ticket.setAssignee(assignee);
        return this;
    }

    public TicketBuilder setReporter(User reporter){
        this.ticket.setReporter(reporter);
        return this;
    }

    public TicketBuilder setStatus(Status status){
        this.ticket.setStatus(status);
        return this;
    }

    public TicketBuilder setPriority(Priority priority){
        this.ticket.setPriority(priority);
        return this;
    }

    public TicketBuilder setSpentTime(int spentTime){
        this.ticket.setSpentTime(spentTime);
        return this;
    }

    public TicketBuilder setEstimatedTime(int estimatedTime){
        this.ticket.setEstimatedTime(estimatedTime);
        return this;
    }
    public TicketBuilder setStartedDate(Calendar date){
        this.ticket.setStartingDate(date);
        return this;
    }

    public TicketBuilder setDeadlineDate(Calendar date){
        this.ticket.setDeadline(date);
        return this;
    }

    public Ticket build(){
        return this.ticket;
    }
}
