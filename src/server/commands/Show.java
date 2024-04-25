package server.commands;

import global.facility.Response;
import global.facility.Ticket;
import server.rulers.CollectionRuler;

/**
 * команда выводящая в стандартный поток вывода все элементы коллекции в строковом представлении
 */
public class Show extends Command  {

    private final CollectionRuler collectionRuler;
    public Show( CollectionRuler collectionRuler){
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionRuler=collectionRuler;

    }
    /**
     * метод выполняет команду
     *
     * @return возвращает сообщение о  успешности выполнения команды
     */
    @Override
    public Response apply(String[] arguments , Ticket ticket){
        if(!arguments[1].isEmpty()){
            console.println("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
            return new Response("rfrf");
        }

        console.println(collectionRuler);
        return new Response("rfrf");
    }
}