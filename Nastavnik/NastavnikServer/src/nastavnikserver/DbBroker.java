/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nastavnikserver;

import nastavnikcommon.Nastavnik;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import nastavnikcommon.Angazovanje;
import nastavnikcommon.Predmet;

/**
 *
 * @author Ljubomir
 */
public class DbBroker {

    Connection connection;

    public DbBroker() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/2nastavnici", "root", "@Ghostfreak123!");
        connection.setAutoCommit(false);
    }

    // SVUDA UZIMAJ SVE VREDNOSTI IZ BAZE !!!!!!!!!
    Nastavnik login(Nastavnik nastavnik) throws SQLException, Exception {

        String query = "SELECT * FROM nastavnici WHERE email = ? AND sifra = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, nastavnik.getEmail());
        ps.setString(2, nastavnik.getSifra());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Nastavnik n = new Nastavnik();
            n.setIme(rs.getString("ime"));
            n.setPrezime(rs.getString("prezime"));
            n.setEmail(rs.getString("email"));
            n.setId(rs.getInt("id"));
            return n;
        }
        throw new Exception("Korisnik ne postoji u bazi !");
    }

    List<Predmet> getListaPredmetaByNastavnil(Nastavnik nastavnik) throws SQLException {
        String query = "SELECT DISTINCT p.naziv, p.id "
                + "FROM angazovanja AS a "
                + "JOIN predmeti AS p ON a.id_predmet = p.id "
                + "WHERE a.id_nastavnik = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, nastavnik.getId());

        ResultSet rs = ps.executeQuery();
        List<Predmet> predmeti = new ArrayList<>();

        while (rs.next()) {
            Predmet predmet = new Predmet();
            predmet.setId(rs.getInt("id"));
            predmet.setNaziv(rs.getString("naziv"));
            predmeti.add(predmet);
        }

        return predmeti;
    }

    List<Angazovanje> getAllAngazovanjaByPredmet(Predmet predmet) throws SQLException {
        String query = "SELECT *\n"
                + "FROM angazovanja AS a JOIN nastavnici AS n ON a.id_nastavnik = n.id JOIN predmeti AS p ON a.id_predmet = p.id\n"
                + "WHERE id_predmet = ?;";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, predmet.getId());
        ResultSet rs = ps.executeQuery();
        ArrayList<Angazovanje> angazovanja = new ArrayList<>();
        while (rs.next()) {
            Angazovanje a = new Angazovanje();
            a.setId(rs.getInt("id"));
            Predmet p = new Predmet();
            p.setNaziv(rs.getString("naziv"));
            p.setId(rs.getInt("id_predmet"));
            a.setPredmet(predmet);
            Nastavnik n = new Nastavnik();
            n.setIme(rs.getString("ime"));
            n.setId(rs.getInt("id_nastavnik"));
            a.setNastavnik(n);
            a.setTipAngazovanja(rs.getString("tip_nastave"));
            angazovanja.add(a);

        }

        return angazovanja;

    }

    void saveAngazovanje(List<Angazovanje> an) throws Exception {

        try {
            String query = "DELETE FROM angazovanja WHERE id_nastavnik = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, an.get(0).getNastavnik().getId());
            ps.executeUpdate();

            for (Angazovanje a : an) {
                if (!predmetExists(a.getPredmet())) {
                    savePredmet(a.getPredmet());
                }
            }
            
            for (Angazovanje a : an) {
                if(!isBrojAngazovanjaNaPredmetuGreaterThanThree(a.getPredmet()) && !isNastavnikAngazovanNaViseOdPetPredmeta(a.getNastavnik())){
                    String queryq = "INSERT INTO angazovanja (id_predmet, id_nastavnik, tip_nastave) VALUES (?,?,?)";
                    PreparedStatement ps1 = connection.prepareStatement(queryq);
                    ps1.setInt(1, a.getPredmet().getId());
                    ps1.setInt(2, a.getNastavnik().getId());
                    ps1.setString(3, a.getTipAngazovanja());
                    ps1.executeUpdate();
                
                }
            }
            
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw e;
        }
    }



    void savePredmet(Predmet p) throws SQLException {
        String query = "INSERT INTO predmeti (naziv) VALUES (?)";
        PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, p.getNaziv());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            p.setId(rs.getInt(1));
        }
    }

    boolean predmetExists(Predmet p) throws SQLException {
        String query = "SELECT * FROM predmeti WHERE naziv = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, p.getNaziv());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return true;
        }

        return false;

    }

    boolean isBrojAngazovanjaNaPredmetuGreaterThanThree(Predmet p) throws SQLException, Exception {
        String query = "SELECT COUNT(*) as broj FROM angazovanja WHERE id_predmet = ? and tip_nastave = 'predavanja'";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, p.getId());
        int broj = 0;
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            broj = rs.getInt("broj");
        }
        if (broj >= 3) {
            throw new Exception("Na predmetu ne moze biti vise od 3 nastavnika angazovana na predavanjim !");
        }

        return false;

    }

    boolean isNastavnikAngazovanNaViseOdPetPredmeta(Nastavnik n) throws SQLException, Exception {
        String query = "SELECT COUNT(*) as broj FROM angazovanja WHERE id_nastavnik = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, n.getId());
        ResultSet rs = ps.executeQuery();
        int broj = 0;
        if (rs.next()) {
            broj = rs.getInt("broj");
        }
        if (broj >= 5) {
            throw new Exception("Ves ste angazovani na maksimalnom broju predmeta !");
        }

        return false;

    }

    

    List<String> getTipPredavanjaCount(Nastavnik n) throws SQLException {
        String query = "SELECT tip_nastave, COUNT(*) AS broj\n"
                + "FROM angazovanja\n"
                + "WHERE id_nastavnik = ?\n"
                + "GROUP BY tip_nastave";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, n.getId());
        ResultSet rs = ps.executeQuery();
        List<String> list = new ArrayList<>();
        while (rs.next()) {
            StringBuilder sb = new StringBuilder();
            sb.append(rs.getString("tip_nastave")).
                    append(" : ")
                    .append(String.valueOf(rs.getInt("broj")));

            list.add(sb.toString());
        }

        return list;
    }
}
