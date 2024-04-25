package server.commands;

import client.tools.Ask;
import global.exeptions.NotFoundException;
import global.facility.Response;
import global.facility.Ticket;
import server.rulers.CollectionRuler;

/**
 * команда обновляющая значение элемента коллекции, id которого равен заданному
 */
public class UpdateById extends Command{

    private final CollectionRuler collectionRuler;

    public UpdateById( CollectionRuler collectionRuler){
        super("update_by_id" , "обновить значение элемента коллекции, id которого равен заданному");

        this.collectionRuler=collectionRuler;
    }
    /**
     * метод выполняет команду
     *
     * @return возвращает сообщение о  успешности выполнения команды
     */
    @Override
    public Response apply(String[] arguments , Ticket ticket) {
        if(arguments[1].isEmpty()){
            //console.println("Неправильное количество аргументов!");
            //console.println("Использование: '" + getName() + "'");
            return new Response("Неправильное количество аргументов!\n" + "Использование: '" + getName() + "'" );
        }
        try{
            long deletableId= Long.parseLong(arguments[1]);
            var deletable= collectionRuler.byId(deletableId);
            if (deletable == null) throw new NotFoundException();
            collectionRuler.remove(deletable);
            Ticket a =  ticket;
            if(a!= null&&a.validate()){
                collectionRuler.add(a);
                //console.println("Ticket добавлен!");
                return new Response("Ticket добавлен!");
            }else{
                //console.printError("Поля Ticket не валидны! Ticket не создан!");
                return new Response("Поля Ticket не валидны! Ticket не создан!");
            }
        }catch(NotFoundException e){
            //console.printError("Продукта с таким ID в коллекции нет!");
            return new Response("Продукта с таким ID в коллекции нет!");
        }
    }
}