package server.commands;

import global.facility.Response;
import server.rulers.CollectionRuler;
import global.facility.Ticket;
import java.util.List;
import java.util.stream.Collectors;

/**
 * команда выводящая значения поля event всех элементов в порядке возрастания
 */
public class PrintFieldAscendingEvent extends Command{

    private final CollectionRuler collectionRuler;

    public PrintFieldAscendingEvent( CollectionRuler collectionRuler){
        super("print_field_ascending_event","вывести значения поля event всех элементов в порядке возрастания");

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

        var eventMinAge=filterByMinAge();
        if(eventMinAge.isEmpty()){
            console.println("Значения event отсутствуют");
        }else{
            console.println("Значения event в порядке возрастания");
            eventMinAge.forEach(console::println);
        }
        return new Response("rfrf");

    }
    private List<Long> filterByMinAge() {
        return collectionRuler.getCollection().stream()
                .map(Ticket::getEventMinage)
                .sorted()
                .collect(Collectors.toList());
    }

}
