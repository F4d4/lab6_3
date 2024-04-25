package server.rulers;

import global.facility.Ticket;
import server.tools.Parser;

import java.time.LocalDateTime;
import java.util.*;
/**
 * Класс Руководителя коллекцией
 */

public class CollectionRuler {
    private long currentId = 1;
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final Parser parser;
    private PriorityQueue<Ticket> collection = new PriorityQueue<>();

    public CollectionRuler(Parser parser){
        this.parser = parser;
        this.lastInitTime=null;
        this.lastSaveTime=null;
    }

    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }


    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }


    public PriorityQueue<Ticket> getCollection() {
        return collection;
    }


    /**
     * Ищет элемент коллекции по id
     */
    public Ticket byId(Long id){
        for (Ticket element:collection){
            if (element.getId() == id) return element;
        }
        return null;
    }

    /**
     * Проверяет содержит ли коллекция элемент
     *
     * @return возвращает true если элемент существует в коллекции
     */
    public boolean isContain(Ticket t){
        return t == null || byId(t.getId()) != null;
    }

    /**
     * Получить свободный id
     */
    public long getFreeId() {
        while (byId(++currentId) != null);
        return currentId;
    }
    /**
     * Сортировка коллекции
     */
    public void update() {
        List<Ticket> tempList = new ArrayList<>(collection);
        Collections.sort(tempList);
        collection = new PriorityQueue<>(tempList);
    }
    /**
     * Инициализация коллекции из файла
     *
     *@return возвращает сообщение о  успешности выполнения метода
     */

    public boolean init() {
        collection.clear();;
        collection = parser.loadCollection();
        lastInitTime = LocalDateTime.now();
        for (var e : collection)
            if (byId(e.getId()) == null) {
                collection.clear();
                return false;
            } else {
                if (e.getId()>currentId) currentId = e.getId();
            }
        update();
        return true;
    }
    /**
     * Сохранение коллекции в файл
     */

    public void saveCollection() {
        parser.saveCollectionxml(collection);
        lastSaveTime = LocalDateTime.now();
    }
    /**
     * Добавление элемента в коллекцию
     *
     * @return возвращает сообщение о  успешности выполнения метода
     */
    public boolean add(Ticket a){
        if(isContain(a)){
            return false;
        }
        collection.add(a);
        update();
        return true;
    }
    /**
     * Удаляет элемент из коллекции
     */
    public void remove(Ticket t){
        collection.remove(t);
    }
    /**
     * Удаляет все элементы в коллекции
     */
    public void removeAll(){
        collection.clear();
    }
    /**
     * удаляет первый элемент коллекции
     */
    public void removefirst(){
        collection.poll();
    }



    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";

        StringBuilder info = new StringBuilder();
        for (var Ticket : collection) {
            info.append(Ticket+"\n\n");
        }
        return info.toString().trim();
    }



}
