package Lesson2.Server.service;

import java.sql.*;
import java.util.List;

public class DataBaseService {

    private static final String DB_NAME = "GB_chat";
    private static final String TABLE_USERS = "Users";
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement ps;

    public static void createUserTable() {
        connectDB();

        try {
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            System.out.println("Error: Can not create statement!");
        }


        StringBuilder stringCreator = new StringBuilder();

        stringCreator.append("CREATE TABLE  IF NOT EXISTS  ")
                .append(TABLE_USERS)
                .append(" (id       INTEGER PRIMARY KEY AUTOINCREMENT,")
                .append(" Login    TEXT    UNIQUE NOT NULL,")
                .append(" Password TEXT    NOT NULL,")
                .append(" Nickname TEXT    NOT NULL); ");
        // System.out.println(stringCreator);


        try {
            statement.executeUpdate(String.valueOf(stringCreator));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        disconnectDB();
    }


    public static void connectDB() {
        try {
            Class.forName("org.sqlite.JDBC");

        } catch (ClassNotFoundException e) {
            System.out.println("Error: \"org.sqlite.JDBC\" could not load to memory!");
        }

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME + ".db");
        } catch (SQLException throwables) {
            System.out.println("Error: Can not connect to DB!");
        }
    }

    public static void disconnectDB() {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException throwables) {
                System.out.println("Error:  could not close statement!");
                throwables.printStackTrace();
            }
        }

        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException throwables) {
                System.out.println("Error:  could not close connect to DB!");
                throwables.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                System.out.println("Error:  could not close connect to DB!");
                throwables.printStackTrace();
            }
        }
    }

    public static void createUsers(List<String[]> users) {
        connectDB();

        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement("insert into " + TABLE_USERS + "(Login, Password,Nickname) values (?, ?, ?)");
            for (String[] user : users) {
                ps.setString(1, user[0]);
                ps.setString(2, user[1]);
                ps.setString(3, user[2]);
                ps.execute();
            }
            connection.setAutoCommit(true);
        } catch (SQLException throwables) {
            //  throwables.printStackTrace();
        }

        disconnectDB();
    }

    public static String authentication(String login, String pass) {
        connectDB();
        String nickName = "";

        try {
            statement = connection.createStatement();
            String query = "select Nickname from " + TABLE_USERS + " where Login = '" + login + "'and Password ='" + pass + "';";
            //  System.out.println(query);
            ResultSet rs = statement.executeQuery(query);
            nickName = rs.getString("Nickname");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        disconnectDB();
        return nickName;
    }

    public static String authentication(String login) {
        connectDB();
        String nickName = "";

        try {
            statement = connection.createStatement();
            String query = "select Nickname from " + TABLE_USERS + " where Login = '" + login + "';";
            ResultSet rs = statement.executeQuery(query);
            nickName = rs.getString("Nickname");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        disconnectDB();
        return nickName;
    }

    public static String changeNickName(String name, String newName) {
        String returnMessage = "";
        if (name.equalsIgnoreCase(newName)){
            returnMessage ="Same Name((";
        }
        connectDB();

        //TODO возвращать только номер результата, а фомирование текста вынести в клиентхендлер, чтобы зря необновлять список юзеров,
        try {
            statement = connection.createStatement();
            String query = "select count (Nickname) from " + TABLE_USERS + " where NickName = '" + newName + "';";
            ResultSet rs = statement.executeQuery(query);
            // System.out.println(query);
            if (rs.getInt(1) != 0) returnMessage = "NickName is busy!!";
            else {
                query = "update " + TABLE_USERS + " SET Nickname = '" + newName + "' WHERE Nickname = '" + name + "';";
                System.out.println(query);
                if (statement.executeUpdate(query) == 1) {
                    returnMessage = "You are " + newName + ", now. ";
                    Server.sendMessageToAll(Server.getTime() + " User " + name + " changed name to " + newName);
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return ("Something wrong");
        }


        disconnectDB();
        return returnMessage;

    }

}
