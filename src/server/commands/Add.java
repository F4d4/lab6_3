package server.commands;

import client.tools.Ask;
import global.facility.Response;
import global.facility.Ticket;
import server.rulers.CollectionRuler;


/**
 * добавление элемента в коллекцию
 */

public class Add extends Command{
    private final CollectionRuler collectionRuler;

    public Add( CollectionRuler collectionRuler){
        super("add", "добавить новый элемент в коллекцию");
        this.collectionRuler=collectionRuler;
    }

    /**
     * метод выполняет команду
     *
     * @return возвращает сообщение о  успешности выполнения команды
     */

    public Response apply(String[] arguments , Ticket ticket){
        try{
            if(!arguments[1].isEmpty()){
                //console.println("Неправильное количество аргументов!");
                //console.println("Использование: '" + getName() + "'");
                return new Response("Неправильное количество аргументов!\n" + "Использование: '" + getName() + "'" );
            }
            //console.println("Начинаем создание Ticket");
            Ticket a =  ticket;
            if(a!= null&&a.validate()){
                collectionRuler.add(a);
                //console.println("Ticket добавлен!");
                return new Response("Ticket добавлен!");
            }else{
                //console.printError("Поля Ticket не валидны! Ticket не создан!");
                return new Response("Поля Ticket не валидны! Ticket не создан!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
