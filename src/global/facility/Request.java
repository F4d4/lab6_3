package global.facility;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 5760575944040770153L;
    private String commandMassage;
    private Ticket ticket;
    public Request(String commandMassage){
        this.commandMassage = commandMassage;
    }

    public String getCommandMassage(){
        return commandMassage;
    }

    @Override
    public String toString(){
        return commandMassage;
    }

    public Request(String commandMassage, Ticket ticket){
        this.commandMassage=commandMassage;
        this.ticket = ticket;
    }

    public Ticket getTicket(){
        return ticket;
    }
}
