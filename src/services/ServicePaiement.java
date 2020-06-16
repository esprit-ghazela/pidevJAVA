/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import models.Paiement;
import services.IServicePaiement;
import utils.ConnectionUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.ConnectionUtil;

/**
 *
 * @author aziz khbou
 */
public class ServicePaiement implements IServicePaiement<Paiement> {

    private PreparedStatement pre;
    Connection con = null;

    private Statement pst;

    @Override
    public void ajouter(Paiement p) {
        try {
            String requete = "INSERT INTO paiement (commande_id,methodedepaiement_id,email,nom_companie,pays,district,codePostal)Values (?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(requete);
            pst.setInt(1, p.getCommande_id());
            pst.setInt(2, p.getMethodedepaiement_id());
            pst.setString(3, p.getEmail());
            pst.setString(4, p.getNom_companie());
            pst.setString(5, p.getPays());
            pst.setString(6, p.getDistrict());
            pst.setInt(7, p.getCodePostal());

            pst.executeUpdate();
            System.out.println("Paiement ajoutée !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List afficher() throws SQLException {
        List<Paiement> list = new ArrayList<>();
        pst = con.createStatement();

        ResultSet rs = pst.executeQuery("select * from paiement");
        while (rs.next()) {
            int id = rs.getInt(1);
            String email = rs.getString(4);
            String nom_companie = rs.getString(5);
            String pays = rs.getString(6);
            String district = rs.getString(7);
            int codePostal = rs.getInt("codePostal");

            Paiement p = new Paiement(id, email, nom_companie, pays, district, codePostal);
            list.add(p);
        }
        return list;
    }

    @Override
    public void supprimer(int x) {
        String sql = "DELETE FROM paiement WHERE id = ? ";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, x);

            statement.executeUpdate();

            System.out.println("Paiement Supprimer");

        } catch (SQLException ex) {
            Logger.getLogger(ServicePaiement.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Paiement non Supprimer");
        }
    }

    @Override
    public void modifier(Paiement p, int id) {
        try {

            pre = con.prepareStatement("UPDATE Paiement SET email = ? ,nom_companie =? ,pays =?,district =? ,codePostal =? WHERE id=?");
            pre.setString(1, p.getEmail());
            pre.setString(2, p.getNom_companie());
            pre.setString(3, p.getPays());
            pre.setString(4, p.getDistrict());
            pre.setInt(5, p.getCodePostal());
            pre.setInt(6, id);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCommande.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
