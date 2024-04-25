package server.tools;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import global.facility.Ticket;

import global.tools.Console;

import java.io.*;

import java.util.PriorityQueue;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * Класс для работы с файлом
 */
public class Parser {
    private final String filename;
    private final Console console;
    XmlMapper xmlMapper = new XmlMapper();



    public Parser(String filename, Console console) {
        this.filename = filename;
        this.console = console;
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.registerModule(new Jdk8Module());
        xmlMapper.registerModule(new JavaTimeModule());
    }
    /**
     * Метод который сохраняет коллекцию в файл
     */
    public void saveCollectionxml(PriorityQueue<Ticket> tickets) {

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println(xmlMapper.writeValueAsString(tickets));
            console.println("Коллекция успешна сохранена в файл!");
        } catch (IOException e) {
            console.printError("Загрузочный файл не может быть открыт!"); // Обработайте ошибку записи в файл
        }
    }
    /**
     * Метод , который загружает коллекцию из файла
     */
    public PriorityQueue<Ticket> loadCollection() {
        PriorityQueue<Ticket> result = new PriorityQueue<>();
        if (filename != null && !filename.isEmpty()) {
            File file = new File(filename);
            if (file.exists()) {
                try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
                    // Преобразование XML в коллекцию PriorityQueue<Ticket>
                    result = xmlMapper.readValue(inputStream, xmlMapper.getTypeFactory().constructCollectionType(
                            PriorityQueue.class, Ticket.class));
                } catch (FileNotFoundException exception) {
                    console.println("Загрузочный файл не найден!");
                } catch (IOException exception) {
                    console.println("Ошибка чтения файла: " + exception.getMessage());
                } catch (Exception exception) {
                    console.println("Непредвиденная ошибка при загрузке коллекции: " + exception.getMessage());
                }
            } else {
                console.println("Загрузочный файл не существует!");
            }
        } else {
            console.println("Аргумент командной строки с загрузочным файлом не найден!");
        }
        PriorityQueue<Ticket> finalResult = new PriorityQueue<>();
        result.forEach(ticket -> {
            if(ticket!= null&&ticket.validate()){
                finalResult.add(ticket);
            }
        });
        return finalResult;
    }

}
