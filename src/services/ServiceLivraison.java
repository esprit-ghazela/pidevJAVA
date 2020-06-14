/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import models.Livraison;
import services.IServiceLivraison;
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
public class ServiceLivraison implements IServiceLivraison<Livraison> {

    Connection cnx = ConnectionUtil.getInstance().conDB();
    private Statement pst;
    private PreparedStatement pre;
    Connection con = null;

    @Override
    public void ajouter(Livraison l) {
        try {
            String requete = "INSERT INTO livraison (paiement_id,adresseLivr,etat,datelivr)Values (?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, l.getPaiement_id());
            pst.setString(1, l.getAdresseLivr());           
            pst.setString(2, l.getEtat());
            pst.setDate(3, (Date) l.getDatelivr());

            pst.executeUpdate();
            System.out.println("Livraison ajoutée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    @Override
    public List<Livraison> afficher() throws SQLException {
        List<Livraison> list = new ArrayList<>();
        pst = cnx.createStatement();

        ResultSet rs = pst.executeQuery("select * from livraison");
        while (rs.next()) {
            java.util.Date dl = new java.util.Date(rs.getDate(5).getTime());
            int id = rs.getInt("id");
            String adresseLivr = rs.getString("adresseLivr");
            String etat = rs.getString("etat");

            Livraison l = new Livraison(id, adresseLivr, etat, dl);
            list.add(l);
        }
        return list;

    }

    @Override
    public void supprimer(int x) {
       String sql = "DELETE FROM livraison WHERE id = ? ";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, x);

            statement.executeUpdate();

            System.out.println("Livraison Supprimer");

        } catch (SQLException ex) {
            Logger.getLogger(ServiceLivraison.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Livraison non Supprimer");
        }
    }

    @Override
    public void modifier(Livraison l, int id) {
        try {
            Date dl = new java.sql.Date(l.getDatelivr().getTime());

            pre = cnx.prepareStatement("UPDATE livraison SET adresseLivr = ? ,etat =? ,datelivr =? WHERE id=?");
            pre.setString(1, l.getAdresseLivr());
            pre.setString(2, l.getEtat());
            pre.setDate(3, dl);
            pre.setInt(4, id);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCommande.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


