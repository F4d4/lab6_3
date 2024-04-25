package server.commands;


import global.facility.Response;
import global.facility.Ticket;
import server.rulers.CollectionRuler;

import java.util.List;
import java.util.stream.Collectors;

public class FilterStartsWIthName extends Command {
    private final CollectionRuler collectionRuler;
    /**
     * команда выводящая элементы ,  значение name которых начинается с заданной подстроки
     */
    public FilterStartsWIthName( CollectionRuler collectionRuler){
        super("filter_starts_with_name", "вывести элементы , значение name которых начинается с заданной подстроки ");
        this.collectionRuler=collectionRuler;

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
        var tickets= filterByName(arguments[1]);
        if(tickets.isEmpty()){
            console.println("Ticket, чьи name содержат '" + arguments[1] + "' не обнаружено.");
        }else{
            console.println("Ticket, чьи name содержат '" + arguments[1] + "' обнаружено " + tickets.size() + " шт.\n");
            tickets.forEach(console::println);
        }
        return new Response("rfrf");
    }

    private List<Ticket> filterByName(String partName) {
        return collectionRuler.getCollection().stream()
                .filter(product -> (product.getName() != null && product.getName().contains(partName)))
                .collect(Collectors.toList());
    }
}
