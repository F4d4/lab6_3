package server.commands;

import global.facility.Response;
import global.facility.Ticket;
import server.rulers.CollectionRuler;
import global.exeptions.NotFoundException;

/**
 * команда удаляющая элемент из коллекции по его id
 */
public class RemoveById extends Command {
    private final CollectionRuler collectionRuler;


    public RemoveById( CollectionRuler collectionRuler) {
        super("remove_by_id", "удалить элемент из коллекции по его id");

        this.collectionRuler = collectionRuler;
    }
    /**
     * метод выполняет команду
     *
     * @return возвращает сообщение о  успешности выполнения команды
     */
    @Override
    public Response apply(String[] arguments , Ticket ticket){
        if(arguments[1].isEmpty()){
            console.println("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
            return new Response("rfrf");
        }
        try{
            long deletableId= Long.parseLong(arguments[1]);
            var deletable= collectionRuler.byId(deletableId);
            if (deletable == null) throw new NotFoundException();
            collectionRuler.remove(deletable);
        }catch(NotFoundException e){
            console.printError("Продукта с таким ID в коллекции нет!");
        }
        return new Response("rfrf");
    }
}
