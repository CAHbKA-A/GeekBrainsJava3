package Lesson2.Server.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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
    private String login;
    private static final Logger LOGGER = LogManager.getLogger(ClientHandler.class.getName());

    private boolean isAuthorized;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClientHandler(Server server, Socket socket) {

        isAuthorized = false;
        this.server = server;
        this.socket = socket;

        try {
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            LOGGER.error("Data IO Stream error: " + e);
        }

        Thread clientHandlerThread = new Thread(() -> {
            while (!isAuthorized) {

                try {
                    socket.setSoTimeout(120000);
                    String inputMessage = readMessage();
                    LOGGER.info("Somebody sent to server message: "+inputMessage);
                    authentication(inputMessage);

                } catch (SocketTimeoutException socketTimeoutException) {
                    LOGGER.debug("timeout socket, Connection TimeOut!");
                    sendMessage("Connection TimeOut!");
                    sendMessage("/q");
                    closeConnection();

                } catch (IOException e) {
                    LOGGER.error("Socket for client error: " + e);
                    break; // если запустить клиент и не авторизовываясь закрыть окно.
                }
            }

            try {
                while (true) {
                    socket.setSoTimeout(0);
                    String inputMessage = readMessage();
                    LOGGER.info(this.name+ " sent message to server : "+inputMessage);

                    if (inputMessage.trim().startsWith("/")) { // обработка команд
                        //TODO переделать на caseof

                        if (inputMessage.trim().equalsIgnoreCase("/q")) {
                            closeConnection();
                            Server.unSubScribe(this);
                            break;
                        }
                        if (inputMessage.trim().startsWith("/w ")) {
                            inputMessage = loginIn(inputMessage);
                        }
                        if (inputMessage.trim().startsWith("/myname ")) {
                            changeName(inputMessage);
                        }
                    } else  //не служебные сообщения
                        Server.sendMessageToAll(Server.getTime() + "  " + this.name + ": " + inputMessage);
                }

            } catch (SocketException socketTimeoutException2) {
                LOGGER.error("Socket timeout error: " + socketTimeoutException2);
            } catch (IOException e) {
                LOGGER.error("Read message IO error: " + e);
            }
        });
        server.addThread(clientHandlerThread);
    }

    private String loginIn(String inputMessage) {
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
            LOGGER.debug("Empty privat message from" + this.name);
            sendMessage("Empty message.");
        }
        return inputMessage;
    }

    private void changeName(String inputMessage) {
        inputMessage = inputMessage.substring(8);
        if (inputMessage.length() > 20) sendMessage("Nick is too long!");
        else {
            sendMessage(DataBaseService.changeNickName(this.name, inputMessage));
            this.name = DataBaseService.authentication(this.login);
            Server.sendMessageToAll(Server.createUserList());
        }
    }

    private void authentication(String inputMessage) {
        if (inputMessage.startsWith("/auth")) {
            String[] parts = inputMessage.split(" ");
            if (parts.length == 3) {

                String nick = (server.getAuthService()).authenticationAlgorithm(parts[1], parts[2]);
                if (!nick.equals("")) {
                    this.name = nick;


                    //проверка присутсвия в чате
                    if (Server.isAlreadyConnected(this.name)) {
                        sendMessage(Server.getTime() + "  " + this.name + " already in chat!");
                        LOGGER.debug(this.name + " already in chat!");
                        this.isAuthorized = false;

                    } else {
                        this.isAuthorized = true;
                        LOGGER.info("Client " + this.name + " authorized");
                        sendMessage("/authok");
                        Server.subScribe(this);
                        this.login = parts[1];
                    }
                } else {
                    sendMessage(Server.getTime() + "  " + "Wrong login or password. Try again.");
                    LOGGER.debug(this.name + " Wrong login or password.");
                }
            }
        }
    }

    void sendMessage(String message) {
        try {
            dos.writeUTF(message);
        } catch (IOException e) {
            LOGGER.error("IO error in DataOutStreamReader" + e);
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
                LOGGER.error("Flush error in DataOutStreamReader" + e);
            }
            try {
                dos.close();
            } catch (IOException e) {
                LOGGER.error("Close error in DataOutStreamReader" + e);
            }
        }
        if (dis != null) {
            try {
                dis.close();
            } catch (IOException e) {
                LOGGER.error("Close error in DataInputStreamReader" + e);
            }
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                LOGGER.error("Closing SOCKET error" + e);
            }
        }
    }
}
