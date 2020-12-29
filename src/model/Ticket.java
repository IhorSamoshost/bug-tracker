package model;

import model.enums.Priority;
import model.enums.Status;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

public class Ticket implements Serializable {
    private String name;
    private String description;
    private User assignee;
    private User reporter;
    private Status status;
    private Priority priority;
    private int spentTime;
    private int estimatedTime;
    private Calendar startingDate;
    private Calendar deadline;

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
                  int spentTime, int estimatedTime, Calendar startingDate, Calendar deadline) {
        this.name = name;
        this.description = description;
        this.assignee = assignee;
        this.reporter = reporter;
        this.status = status;
        this.priority = priority;
        this.spentTime = spentTime;
        this.estimatedTime = estimatedTime;
        this.startingDate = startingDate;
        this.deadline = deadline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
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

    public int getSpentTime() {
        return spentTime;
    }

    public void setSpentTime(int spentTime) {
        this.spentTime = spentTime;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Calendar getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Calendar startingDate) {
        this.startingDate = startingDate;
    }

    public Calendar getDeadline() {
        return deadline;
    }

    public void setDeadline(Calendar deadline) {
        this.deadline = deadline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return spentTime == ticket.spentTime && estimatedTime == ticket.estimatedTime && name.equals(ticket.name) && Objects.equals(description, ticket.description) && Objects.equals(assignee, ticket.assignee) && reporter.equals(ticket.reporter) && status == ticket.status && priority == ticket.priority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, assignee, reporter, status, priority, spentTime, estimatedTime);
    }

    @Override
    public String toString() {
        String result = "\n\tTicket " + this.name.toUpperCase();
        if (this.description != null) {
            result = result + " \nDescription: " + this.description;
        }
        if (this.assignee != null) {
            result = result + "\nAssignee: " + this.assignee;
        }
        if (this.reporter != null) {
            result = result + "\nReporter: " + this.reporter;
        }
        if (this.status != null) {
            result = result + "\nStatus: " + this.status;
        }
        if (this.priority != null) {
            result = result + "\nPriority: " + this.priority;
        }
        result = result + "\nSpent time: " + this.spentTime;
        result = result + "\nEstimated time: " + this.estimatedTime;
        if (this.startingDate != null) {
            result = result + "\nStart: " + this.startingDate.getTime();
        }
        if (this.deadline != null) {
            result = result + "\nDeadline: " + this.deadline.getTime();
        }
        return result;
    }
}
