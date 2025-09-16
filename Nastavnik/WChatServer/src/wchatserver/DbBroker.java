/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wchatserver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import wchatcommon.Message;
import wchatcommon.User;

/**
 *
 * @author Ljubomir
 */
public class DbBroker {

    Connection connection;

    public DbBroker() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wchat", "root", "@Ghostfreak123!");
    }

    User loginUser(User u) throws SQLException, Exception {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, u.getEmail());
        ps.setString(2, u.getPassword());

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            User user = new User();
            user.setEmail(rs.getString("email"));
            user.setName(rs.getString("name"));
            user.setSurname(rs.getString("surname"));
            System.out.println(user);
            return user;
        }

        throw new Exception("Korisnik ne postoji u bazi !");

    }

    List<Message> getMessageHistory() throws SQLException {
        String query = "SELECT * FROM messages";
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(query);
        List<Message> messages = new ArrayList<>();
        while (rs.next()) {
            Message m = new Message();
            User sender = new User();
            User receiver = new User();
            sender.setName(rs.getString("sender"));
            receiver.setName(rs.getString("receiver"));
            m.setSender(sender);
            m.setReceiver(receiver);
            m.setText(rs.getString("text"));
            m.setTime(rs.getTimestamp("date").toLocalDateTime());
            messages.add(m);

        }

        System.out.println("returning messages");

        return messages;
    }

    void saveMessage(Message m) throws SQLException {

        String query = "INSERT INTO messages (sender,receiver,text,date) VALUES (?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, m.getSender().getName());
        ps.setString(2, m.getReceiver().getName());
        ps.setString(3, m.getText());
        ps.setTimestamp(4, Timestamp.valueOf(m.getTime()));
        ps.executeUpdate();

        System.out.println("message saved");

    }

}
/// JOS CUKU ZA OSTALO