package model;

import model.enums.Priority;
import model.enums.Status;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Calendar;
import java.util.Objects;

public class Ticket implements Serializable {
    private String name;
    private String description;
    private User assignee; // must be serializable
    private User reporter;// must be serializable
    private Status status;
    private Priority priority;
    private Calendar spentTime;
    private Calendar estimatedTime;

    public Ticket() {
    }

    public Ticket(String name) {
        this.name = name;
    }

    public Ticket(String name, User reporter) {
        this.name = name;
        this.reporter = reporter;
    }

    public Ticket(String name, String description, User assignee, User reporter,
                  Status status, Priority priority,
                  Calendar spentTime, Calendar estimatedTime) {
        this.name = name;
        this.description = description;
        this.assignee = assignee;
        this.reporter = reporter;
        this.status = status;
        this.priority = priority;
        this.spentTime = spentTime;
        this.estimatedTime = estimatedTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.isBlank()) {
            throw new InvalidParameterException();
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description.isBlank()) {
            throw new InvalidParameterException();
        }
        this.description = description;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Calendar getSpentTime() {
        return spentTime;
    }

    public void setSpentTime(Calendar spentTime) {
        this.spentTime = spentTime;
    }

    public Calendar getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Calendar estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return name.equals(ticket.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        String result = "\n\tTicket "+this.name.toUpperCase();
        if(this.description  != null){
            result = result+" \nDescription: "+this.description;
        }
        if(this.assignee != null){
            result = result + "\nAssignee: "+this.assignee;
        }
        if(this.reporter != null){
            result = result + "\nReporter: "+this.reporter;
        }
        if(this.status != null){
            result = result + "\nStatus: "+this.status;
        }
        if(this.priority != null){
            result = result + "\nPriority: "+this.priority;
        }
        if(this.spentTime != null){
            result = result + "\nSpent time: "+this.spentTime.getTime();
        }
        if(this.estimatedTime != null){
            result = result + "\nEstimated time: "+this.estimatedTime.getTime();
        }
        return result;

//                "Ticket{" +
//                "name='" + name + '\'' +
//                ", description='" + description + '\'' +
//                ", assignee=" + assignee +
//                ", reporter=" + reporter +
//                ", status=" + status +
//                ", priority=" + priority +
//                ", spentTime=" + spentTime +
//                ", estimatedTime=" + estimatedTime +
//                '}';
    }
}
