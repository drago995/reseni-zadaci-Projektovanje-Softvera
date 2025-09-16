/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bingoserver;

import bingocommon.Game;
import bingocommon.User;
import java.sql.*;

/**
 *
 * @author Ljubomir
 */
public class DbBroker {

    Connection conn;

    public DbBroker() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat", "root", "@Ghostfreak123!");
    }

    User login(User u) throws SQLException, Exception {

        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, u.getUsername());
        ps.setString(2, u.getPassword());

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));
            return u;
        }

        throw new Exception("Korisnik ne postoji u bazi");

    }

    void saveGame(Game game, User pobednik) throws SQLException {
        String query = "INSERT INTO igra (igrac,vreme,dobitna,pobednik,kombinacije) VALUES (?,?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, game.getUser().getUsername());
        ps.setTimestamp(2, Timestamp.valueOf(game.getTime()));
        ps.setString(3, game.getDobitna().toString());
        ps.setString(4, pobednik.getUsername());
        ps.setString(5, game.getIstorija().toString());
        
        ps.executeUpdate();
        System.out.println("sacuvane poruke");
    }

}
