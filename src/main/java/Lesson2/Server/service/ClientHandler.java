package Lesson2.Server.service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;


public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private String name;
    private boolean isAuthorized;

    public String getName() {
        return name;
    }

    public ClientHandler(Server server, Socket socket) {

        isAuthorized = false;
        this.server = server;
        this.socket = socket;

        try {
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            while (!isAuthorized) {

                try {
                    socket.setSoTimeout(120000);
                    String inputMessage = readMessage();
                    System.out.println(inputMessage);
                    authentication(inputMessage);

                } catch (SocketTimeoutException socketTimeoutException) {
                    System.out.println("timeout socket");
                    sendMessage("Connection TimeOut!");
                    sendMessage("/q");
                    closeConnection();

                } catch (IOException e) {
                    //e.printStackTrace();
                    break; // если запустить клиент и не авторизовываясь закрыть окно.
                }
            }

            try {
                while (true) {
                    socket.setSoTimeout(0);
                    String inputMessage = readMessage();
                    System.out.println(inputMessage);


                    if (inputMessage.trim().equalsIgnoreCase("/q")) {
                        closeConnection();
                        Server.unSubScribe(this);
                        break;
                    }
                    if (inputMessage.trim().startsWith("/w ")) {
                        sendMessage(inputMessage);
                        inputMessage = inputMessage.substring(3);
                        int d = inputMessage.indexOf(" ");
                        try {
                            if (Server.sendPrivateMessage(inputMessage.substring(d + 1), this.name, inputMessage.substring(0, d))) {
                                sendMessage("Message delivered.");
                            } else {
                                sendMessage("No such user.");
                            }
                        } catch (StringIndexOutOfBoundsException e) {
                            sendMessage("Empty message.");
                        }
                    } else
                        Server.sendMessageToAll(Server.getTime() + "  " + this.name + ": " + inputMessage);
                }

            }
            catch (SocketException socketTimeoutException2) {
                System.out.println("Socket already closed");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    private void authentication(String inputMessage) {
        if (inputMessage.startsWith("/auth")) {
            String[] parts = inputMessage.split(" ");
            if (parts.length == 3) {

                String nick = (server.getAuthService()).authenticationAlgorithm(parts[1], parts[2]);
                System.out.println(nick);
                if (!nick.equals("")) {
                    this.name = nick;


                    //проверка присутсвия в чате
                    if (Server.isAlreadyConnected(this.name)) {
                        sendMessage(Server.getTime() + "  " + this.name + " already in chat!");
                        this.isAuthorized = false;

                    } else {
                        this.isAuthorized = true;
                        System.out.println("Client " + this.name + " authorized");
                        sendMessage("/authok");
                        Server.subScribe(this);
                    }
                } else sendMessage(Server.getTime() + "  " + "Wrong login or password. Try again.");


            }
        }
    }

    void sendMessage(String message) {
        try {
            dos.writeUTF(message);
        } catch (IOException e) {
            //  e.printStackTrace();
        }
    }

    private String readMessage() throws IOException {
        return dis.readUTF();
    }


    private void closeConnection() {
        if (dos != null) {
            try {
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (dis != null) {
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
