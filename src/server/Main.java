package server;

import global.tools.Console;
import global.tools.MyConsole;
import server.commands.Add;
import server.commands.AddIfMin;
import server.commands.Info;
import server.commands.UpdateById;
import server.managers.SocketServer;
import server.rulers.CollectionRuler;
import server.rulers.CommandRuler;
import server.tools.Parser;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Console console = new MyConsole();
        if(System.getenv("DATANAME")==null){
            console.println("Введите имя загружаемого файла как переменную окружения");
            System.exit(1);
        }

        Parser parser= new Parser(System.getenv("DATANAME"),console);
        CollectionRuler collectionRuler= new CollectionRuler(parser);
        if (!collectionRuler.init()) {
            System.exit(1);
        }
        CommandRuler commandRuler = new CommandRuler(){{
            register("info", new Info(collectionRuler));
            register("add" , new Add(collectionRuler));
            register("add_if_min", new AddIfMin(collectionRuler));
            register("update_by_id",new UpdateById(collectionRuler) );
        }};

        new SocketServer("localhost" , 8080 , commandRuler).start();
    }
}
