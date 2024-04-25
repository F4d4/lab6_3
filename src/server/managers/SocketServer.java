package server.managers;

import global.facility.Request;
import global.facility.Response;
import global.facility.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.rulers.CommandRuler;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SocketServer {
    private static final Logger log = LoggerFactory.getLogger(SocketServer.class);
    private final CommandRuler commandRuler;
    private Selector selector;
    private InetSocketAddress address;
    private Set<SocketChannel> session;

    public SocketServer(String host, int port, CommandRuler commandRuler) {
        this.address = new InetSocketAddress(host, port);
        this.session = new HashSet<>();
        this.commandRuler=commandRuler;
    }

    public void start() throws IOException, ClassNotFoundException {
        this.selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(address);
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);

        log.info("Server started...");

        while(true) {
            // blocking, wait for events
            this.selector.select();
            Iterator keys = this.selector.selectedKeys().iterator();
            while(keys.hasNext()) {
                SelectionKey key = (SelectionKey) keys.next();
                keys.remove();
                if (!key.isValid()) continue;
                if (key.isAcceptable()) accept(key);
                else if (key.isReadable()) read(key);
            }
        }
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel = serverSocketChannel.accept();
        channel.configureBlocking(false);
        channel.register(this.selector, SelectionKey.OP_READ);
        this.session.add(channel);
        System.out.println("System:user new: " + channel.socket().getRemoteSocketAddress() + "\n");
    }


    private void read(SelectionKey key) throws IOException, ClassNotFoundException {
        SocketChannel channel = (SocketChannel) key.channel();
        channel.configureBlocking(false); // Устанавливаем неблокирующий режим для канала

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int numRead = channel.read(buffer);

        // Проверяем, есть ли данные для чтения
        if (numRead == -1) {
            // Если метод read вернул -1, это означает, что клиент закрыл соединение
            this.session.remove(channel);
            System.out.println("System:user left: " + channel.socket().getRemoteSocketAddress() + "\n");
            key.cancel();
            return;
        }

        buffer.flip();
        // Создаем ByteArrayInputStream с использованием только прочитанных данных из буфера
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array(), 0, numRead);
        ObjectInputStream oi = new ObjectInputStream(byteArrayInputStream);

        // Преобразуем массив байтов в строку
        Request request = (Request) oi.readObject();
        String gotData = request.getCommandMassage();
        Ticket gotTicket = request.getTicket();
        System.out.println("Got: " + gotData + "\n" + gotTicket);
        String[] tokens = {" ", " "};
        tokens = (gotData.trim() + " ").split(" ", 2);
        String executingCommand = tokens[0];
        var command = commandRuler.getCommands().get(executingCommand);
        if (command == null) {
            sendAnswer(new Response("Команда '" + tokens[0] + "' не найдена. Наберите 'help' для справки\n"), key);
            return;
        }
        Response response = command.apply(tokens , gotTicket);
        sendAnswer(response, key);
    }


    public void sendAnswer(Response response, SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        client.configureBlocking(false);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        objectOutputStream.close();
        ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        while(buffer.hasRemaining()){
            client.write(buffer);
        }
    }

}

