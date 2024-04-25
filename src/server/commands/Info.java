package server.commands;


import global.facility.Response;
import global.facility.Ticket;
import server.rulers.CollectionRuler;

import java.time.LocalDateTime;

public class Info extends Command {
    private final CollectionRuler collectionRuler;
    /**
     * команда выводящая информацию о коллекции
     */
    public Info( CollectionRuler collectionRuler) {
        super("info", "вывести информацию о коллекции");

        this.collectionRuler = collectionRuler;
    }

    /**
     * метод выполняет команду
     *
     * @return возвращает сообщение о  успешности выполнения команды
     */
    @Override
    public Response apply(String[] arguments , Ticket ticket) {
        if (!arguments[1].isEmpty()) {
            //console.println("Неправильное количество аргументов!");
            //console.println("Использование: '" + getName() + "'");
            return new Response("Неправильное количество аргументов!\nИспользование: '\" + getName() + \"'");
        }

        LocalDateTime lastInitTime = collectionRuler.getLastInitTime();
        String lastInitTimeString = (lastInitTime == null) ? "в данной сессии инициализации еще не происходило" :
                lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();

        LocalDateTime lastSaveTime = collectionRuler.getLastSaveTime();
        String lastSaveTimeString = (lastSaveTime == null) ? "в данной сессии сохранения еще не происходило" :
                lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();
        String s="" ;
        s+="Сведения о коллекции:\n";
        s+=" Тип: " + collectionRuler.getCollection().getClass().toString();
        s+=" \nКоличество элементов: " + collectionRuler.getCollection().size();
        s+=" \nДата последнего сохранения: " + lastSaveTimeString;
        s+=" \nДата последней инициализации: " + lastInitTimeString;
        s+="\n";
        //console.println("Сведения о коллекции:");
        //console.println(" Тип: " + collectionRuler.getCollection().getClass().toString());
        //console.println(" Количество элементов: " + collectionRuler.getCollection().size());
        //console.println(" Дата последнего сохранения: " + lastSaveTimeString);
        //console.println(" Дата последней инициализации: " + lastInitTimeString);
        return new Response(s);
    }
}