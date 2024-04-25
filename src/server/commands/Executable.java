package server.commands;

import global.facility.Response;
import global.facility.Ticket;

/**
 * Интерфейс для всех комманд
 */
public interface Executable {
    Response apply(String[] arguments , Ticket ticket);
}
