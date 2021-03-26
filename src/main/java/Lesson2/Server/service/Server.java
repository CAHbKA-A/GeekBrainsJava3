package Lesson2.Server.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Server {
    private final int PORT = 8899;
    private static List<ClientHandler> clientList;
    private AuthenticationService authService;

    public Server() {

        clientList = new ArrayList<>();
        System.out.println("Server starting...");
        authService = new AuthenticationService();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Server ready.");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client Connecting..");
                new ClientHandler(this, socket);
            }
        } catch (IOException ignored) {
        }
    }

    synchronized static void sendMessageToAll(String message) {
        for (ClientHandler client : clientList) {
            client.sendMessage(message);
        }
    }

    synchronized static boolean sendPrivateMessage(String message, String nickFrom, String nickTo) {
        // делать проверку отправления самому себе не стал. может перед тем как отправить  я хочу проверить на себе
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
        System.out.println(client.getName() + " disconnected");
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
}
