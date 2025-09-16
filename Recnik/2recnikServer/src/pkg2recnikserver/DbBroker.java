/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg2recnikserver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import pkg2recnikcommon.Recnik;
import pkg2recnikcommon.User;

/**
 *
 * @author Ljubomir
 */
public class DbBroker {

    Connection connection;

    public DbBroker() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/recnici", "root", "@Ghostfreak123!");
    }

    User loginUser(User u) throws SQLException, Exception {
        String query = "SELECT * FROM users WHERE email = ? AND sifra = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, u.getEmail());
        ps.setString(2, u.getSifra());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            u.setIme(rs.getString("ime"));
            u.setPrezime(rs.getString("prezime"));
            return u;
        }

        throw new Exception("Korisnik ne postoji u bazu");
    }

    public List<User> getAllUsers() throws SQLException {
        String query = "SELECT * FROM users";
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(query);
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User u = new User(rs.getString("ime"), rs.getString("prezime"), rs.getString("email"), null);
            users.add(u);
        }

        return users;

    }

    public List<Recnik> getAllRecnici() throws SQLException {
        String query = "SELECT * FROM recnik";
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(query);
        List<Recnik> recnici = new ArrayList<>();

        while (rs.next()) {
            Recnik r = new Recnik(rs.getInt("id"), rs.getString("srpski"), rs.getString("engleski"));
            recnici.add(r);
        }

        return recnici;

    }

    public Recnik getPrevod(Recnik srpski) throws SQLException, Exception {
        String query = "SELECT * FROM recnik WHERE srpski = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, srpski.getSrpski());
        
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            srpski.setEngleski(rs.getString("engleski"));
            return srpski;
        }
        
        throw new Exception("Prevod za datu rec ne postoji, ako zelite, unesite svoju !");
        
    }

    public void saveRecnik(Recnik r) throws SQLException {
        String query = "INSERT INTO recnik (srpski, engleski) VALUES (?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, r.getSrpski());
        ps.setString(2, r.getEngleski());

        ps.executeUpdate();

    }

}
