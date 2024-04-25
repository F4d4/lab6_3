package server.commands;

import client.tools.Ask;
import global.facility.Response;
import global.facility.Ticket;
import server.rulers.CollectionRuler;

/**
 * Добавление элемента , если параметр price меньше минимального price в коллекции
 */
public class AddIfMin extends Command {
    private final CollectionRuler collectionRuler;

    public AddIfMin( CollectionRuler collectionRuler){
        super("add_if_min" , "добавить новый элемент в коллекцию , если его значение меньше чем у наименьшего элемента коллекции");

        this.collectionRuler = collectionRuler;
    }
    /**
     * метод выполняет команду
     *
     * @return возвращает сообщение о  успешности выполнения команды
     */
    @Override
    public Response apply (String[] arguments , Ticket ticket){
        try{
            if(!arguments[1].isEmpty()){
                //console.println("Неправильное количество аргументов!");
                //console.println("Использование: '" + getName() + "'");
                return new Response("Неправильное количество аргументов!\n" + "Использование: '" + getName() + "'" );
            }

            //console.println("Начинаем создание Ticket");
            Ticket a =  ticket;
            var minPrice = minPrice();
            if(a.getPrice()<minPrice){
                if(a!= null&&a.validate()){
                    collectionRuler.add(a);
                    //console.println("Ticket добавлен!");
                }else{
                    //console.printError("Поля Ticket не валидны! Ticket не создан!");
                    return new Response("Поля Ticket не валидны! Ticket не создан!");
                }
            }else{
                //console.println("Продукт не добавлен, цена не минимальная (" + a.getPrice() + " > " + minPrice +")");
                return new Response("Продукт не добавлен, цена не минимальная (" + a.getPrice() + " > " + minPrice +")");
            }
            collectionRuler.update();
            return new Response("Ticket добавлен!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private Long minPrice() {
        return collectionRuler.getCollection().stream()
                .map(Ticket::getPrice)
                .mapToLong(Long::longValue)
                .min()
                .orElse(Long.MAX_VALUE);
    }
}
