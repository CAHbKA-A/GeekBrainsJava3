package Lesson2.Server.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final int PORT = 8899;
    private static List<ClientHandler> clientList;
    private AuthenticationService authService;
    private static ExecutorService executorService;
    private static final Logger LOGGER = LogManager.getLogger(Server.class.getName());

    public Server() {

        clientList = new ArrayList<>();
        LOGGER.info("Server starting...");
        authService = new AuthenticationService();
        // executorService = Executors.newFixedThreadPool(70);//с ограничением одновременных пользователей/ Пандемия. больше 70 не собираемся.
        executorService = Executors.newCachedThreadPool();//без огрничений
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            LOGGER.info("Server ready.");
            while (true) {
                Socket socket = serverSocket.accept();
                LOGGER.info("Client Connecting..");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            LOGGER.error("ServerSocket Error:" + e);
        } finally {
            executorService.shutdown();
        }
    }

    synchronized static void sendMessageToAll(String message) {
        message = formatMassage(message);// разбивка длинных сообщений на строки
        for (ClientHandler client : clientList) {
            client.sendMessage(message);
        }
    }

    //разбивка длинных сообщений  на строки\n
    //TODO добавить переносы не только для пробела
    static String formatMassage(String message) {
        if (message.length() > 70) {
            int startIndex = 0;
            int endIndex = 70;
            StringBuilder sb = new StringBuilder();
            while (startIndex < message.length() - 1) {
                endIndex = message.indexOf(" ", endIndex);
                if (endIndex == -1) endIndex = message.length();
                sb.append(message.substring(startIndex, endIndex))
                        .append("\n");
                startIndex = endIndex;
                endIndex = startIndex + 70;
            }
            message = sb.toString();
        }
        return message;
    }

    synchronized static boolean sendPrivateMessage(String message, String nickFrom, String nickTo) {
        // делать проверку отправления самому себе не стал. может перед тем как отправить  я хочу проверить на себе
        message = formatMassage(message);// разбивка длинных сообщений на строки
        for (ClientHandler clientHandler : clientList) {
            if (clientHandler.getName().equalsIgnoreCase(nickTo)) {
                clientHandler.sendMessage(getTime() + ": User " + nickFrom + " sent privat massage to you: " + message);
                return true;
            }
        }
        return false;
    }

    static synchronized void subScribe(ClientHandler client) {
        clientList.add(client);
        sendMessageToAll(getTime() + "  " + client.getName() + " joined the chat!!!");
        sendMessageToAll(createUserList());
    }

    static synchronized void unSubScribe(ClientHandler client) {
        clientList.remove(client);
        sendMessageToAll(getTime() + "  " + client.getName() + " Leave the chat!!!");
        LOGGER.info(client.getName() + " disconnected");
        sendMessageToAll(createUserList());
    }

    public AuthenticationService getAuthService() {
        return authService;
    }

    public static boolean isAlreadyConnected(String nick) {
        for (ClientHandler clientHandler : clientList) {
            if (clientHandler.getName().equals(nick)) return true;
        }
        return false;
    }


    public static String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(Calendar.getInstance().getTime());
    }

    static synchronized String createUserList() {
        StringBuilder userList = new StringBuilder("/USERLIST");
        for (ClientHandler client : clientList) {
            userList.append("  ")
                    .append(client.getName());
        }
        return userList.toString();
    }

    public void addThread(Thread ch) {
        executorService.execute(ch);
    }
}
