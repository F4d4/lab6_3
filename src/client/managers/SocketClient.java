package client.managers;

import client.tools.Ask;
import global.facility.Request;
import global.facility.Response;
import global.facility.Ticket;
import global.tools.MyConsole;
import server.Main;
import server.rulers.CollectionRuler;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SocketClient {
    MyConsole console = new MyConsole();
    private String host;
    private int port;
    public SocketClient(String host, int port){
        this.port=port;
        this.host=host;
    }

    public void start() throws IOException, ClassNotFoundException, InterruptedException, Ask.AskBreak {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(host, port));
        socketChannel.configureBlocking(false);
        Scanner scanner= new Scanner(System.in);
        while(scanner.hasNextLine()){
            //Request request = new Request(scanner.nextLine());
            //sendRequest(request,socketChannel);
            String command = scanner.nextLine().trim();
            if(command.equals("exit")){
                console.println("Завершение сеанса");
                socketChannel.close();
                System.exit(1);
            }
            if(command.contains("add")||command.contains("update")||command.contains("add_if_min")){
                Ticket ticket = Ask.askTicket(console);
                Request request = new Request(command, ticket);
                sendRequest(request, socketChannel);
            }else{
                Request request = new Request(command);
                sendRequest(request,socketChannel);
            }
        }
    }

    public void sendRequest(Request request , SocketChannel channel) throws IOException, ClassNotFoundException, InterruptedException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);
        objectOutputStream.close();
        ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());

        while(buffer.hasRemaining()){
            channel.write(buffer);
        }
        System.out.println(getAnswer(channel));
    }

    public Object getAnswer(SocketChannel channel) throws IOException, ClassNotFoundException , InterruptedException{
        Selector selector = Selector.open();
        channel.register(selector, channel.validOps());
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime <10000){
            int readyChannels  = selector.select();

            if (readyChannels == 0) {
                continue;
            }

            int bytesRead = channel.read(buffer);
            if (bytesRead == -1) {
                break;
            }

            buffer.flip();
            byteArrayOutputStream.write(buffer.array(), 0, bytesRead);
            buffer.clear();

            byte[] responseBytes = byteArrayOutputStream.toByteArray();
            if (responseBytes.length > 0) {
                ObjectInputStream oi = new ObjectInputStream(new ByteArrayInputStream(responseBytes));
                Response answer = (Response) oi.readObject();
                return answer.getMessage();
            }
        }
        return null;
    }
}
