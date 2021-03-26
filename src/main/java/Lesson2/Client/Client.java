package Lesson2.Client;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


import static java.lang.Thread.sleep;

public class Client extends JFrame {

    private Socket socket;
    private final int PORT = 8899;
    private final String HOST = "localhost";
    private DataInputStream dis;
    private DataOutputStream dos;
    private JTextField sayField;
    private JTextArea chat;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton buttonExit;
    private JLabel label;
    private JList userList;
    private DefaultListModel userModel;

    public static void main(String[] args) {
        Client frame = new Client();
    }

    private Client() {
        GUIClient();
        try {
            connection();
            readMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connection() throws IOException {
        chat.append("Connecting..");
        while (true) {
            try {
                socket = new Socket(HOST, PORT);
                if (socket.isConnected()) {
                    chat.append("Server connected \n");
                    break;
                }
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
            } catch (IOException io) {
                chat.append("....");
            }
        }
    }

    private void readMessage() throws IOException {
        chat.setText("\nConnected! Enter you login/password\n");
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());

        new Thread(() -> {
            try {
                while (true) {
                    String serverMess = dis.readUTF();
                    if (serverMess.startsWith("/")) {


                        if (serverMess.equals("/authok")) {
                            loginField.setVisible(false);
                            passwordField.setVisible(false);
                            loginButton.setVisible(false);
                            chat.setText("You connected! Say Hello.\nYou can change nick by /myname new_nick!\n");
                            label.setVisible(true);
                            buttonExit.setVisible(true);
                        }
                        if (serverMess.startsWith("/USERLIST")) {
                            updateUserList(serverMess);
                        }
                        if (serverMess.equals("/q")) {
                            // closeConnection();
                            chat.append("Connection closed. Plz, reopen client window \n");
                            break;
                        }
                    } else {
                        chat.append(serverMess + "\n");
                    }
                }
            } catch (IOException ignored) {
            }
        }).start();
    }

    private void sendMessage() {
        String message = sayField.getText().trim();

        if (!message.isEmpty()) {
            System.out.println(message);
            try {
                dos.writeUTF(message);
                sayField.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage(String message) {

        try {
            dos.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private void updateUserList(String users) {
        String[] parts = users.split("  ");
        userModel.removeAllElements();
        for (int i = 1; i < parts.length; i++) {
            userModel.addElement(parts[i]);
        }
    }

    private void GUIClient() {
        setBounds(200, 200, 550, 600);
        setTitle("GeekChat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel panelNorth = new JPanel();
        panelNorth.setLayout(new GridLayout());


        buttonExit = new JButton("Exit");
        buttonExit.setVisible(false);
        buttonExit.addActionListener(e -> {
            sendMessage("/q");
            closeConnection();
            System.exit(0);
        });

        label = new JLabel("/myname newnick");
        label.setVisible(false);

        loginField = new JTextField("A");
        panelNorth.add(loginField);

        passwordField = new JPasswordField("A");
        panelNorth.add(passwordField);

        loginButton = new JButton("Sing in");
        panelNorth.add(loginButton);
        loginButton.addActionListener(e -> sendMessage("/auth " + loginField.getText() + " " + passwordField.getText()));

        panelNorth.add(label);
        panelNorth.add(buttonExit);

        panel.add(panelNorth, BorderLayout.NORTH);

        chat = new JTextArea();
        chat.setEditable(false);


        JScrollPane scrollPane = new JScrollPane(chat, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(scrollPane, BorderLayout.CENTER);
        JPanel panelSouth = new JPanel();
        panelSouth.setLayout(new BorderLayout());


        sayField = new JTextField("text");
        panelSouth.add(sayField, BorderLayout.CENTER);
        sayField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    sendMessage();
                    System.out.println("1");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        JButton sendButton = new JButton("SEND");
        panelSouth.add(sendButton, BorderLayout.EAST);
        sendButton.addActionListener(e -> sendMessage());

        panel.add(panelSouth, BorderLayout.SOUTH);

        userModel = new DefaultListModel();
        userModel.add(0, "UserList");
        userList = new JList(userModel);
        JScrollPane scrollPaneUserList = new JScrollPane(userList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPaneUserList, BorderLayout.EAST);

        //send private message by click userName
        userList.addListSelectionListener(e -> {
            String to = (String) userList.getSelectedValue();
            if (to != null) {//фиксим баг если клиент вышел
                sayField.setText("/w " + to + " ");
            }
        });


        add(panel);


        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                if (dos != null) {
                    try {
                        dos.writeUTF("/q");
                    } catch (IOException exception) {
                        //exception.printStackTrace();
                    } finally {
                        closeConnection();
                    }
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        setVisible(true);
    }

}
