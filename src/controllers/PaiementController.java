/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import models.Categorie;
import models.Commande;
import models.Paiement;
import models.Methode;

import utils.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author aziz khbou
 */
public class PaiementController implements Initializable {

    @FXML
    private JFXTextField pmail;
    @FXML
    private JFXTextField pcomp;
    @FXML
    private JFXTextField ppays;
    @FXML
    private Label errors_email;
    @FXML
    private Label errors_comp;
    @FXML
    private Label errors_dist;
    @FXML
    private Button btn_com;
    @FXML
    private JFXTextField pdist;
    @FXML
    private JFXTextField pcode;
    @FXML
    private Label errors_pays;
    private Paiement paiement;
    @FXML
    private Label errors_code;
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @FXML
    private BorderPane borderpan2;
    @FXML
    private ComboBox<String> paiement_commande;
    @FXML
    private ComboBox<String> paiement_methode;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AfficherCommande();
        AfficherMethode();
    }

    @FXML
    private void ajouterPaiement(ActionEvent event) throws SQLException {
        String conv1 = paiement_methode.getValue().toString();

        int methode = methodes(conv1);
        String conv = paiement_commande.getValue().toString();
        int commande = produit(conv);
        if (pmail.getText().isEmpty() || pcomp.getText().isEmpty()) {
            if (pmail.getText().isEmpty()) {
                errors_email.setTextFill(Color.TOMATO);
                errors_email.setText("l'email ne peut pas étre un champ vide");
            } else if (ppays.getText().isEmpty()) {
                errors_pays.setTextFill(Color.TOMATO);
                errors_pays.setText("Le pays ne peut pas étre un champ vide");
            } else if (pdist.getText().isEmpty()) {
                errors_dist.setTextFill(Color.TOMATO);
                errors_dist.setText("Le district ne peut pas étre un champ vide");
            } else {
                errors_email.setTextFill(Color.TOMATO);
                errors_email.setText("l'email ne peut pas étre un champ vide");
                errors_comp.setTextFill(Color.TOMATO);
                errors_comp.setText("Le nom de companie ne peut pas étre un champ vide");
            }
        } else {

            try {
                con = ConnectionUtil.conDB();

                String st = "INSERT INTO paiement (commande_id,methodedepaiement_id,email,nom_companie,pays,district,codePostal)Values (?,?,?,?,?,?,?)";
                preparedStatement = (PreparedStatement) con.prepareStatement(st);
                preparedStatement.setInt(1, commande);
                preparedStatement.setInt(2, methode);

                preparedStatement.setString(3, pmail.getText());
                preparedStatement.setString(4, pcomp.getText());
                preparedStatement.setString(5, ppays.getText());
                preparedStatement.setString(6, pdist.getText());
                preparedStatement.setInt(7, Integer.parseInt(pcode.getText()));

                preparedStatement.executeUpdate();
                new Alert(Alert.AlertType.INFORMATION, "sucess").show();

                clearFields();
                // AfficherCommannde) ;
                try {
                    BorderPane pane = FXMLLoader.load(getClass().getResource("/views/Livraison.fxml"));
                    borderpan2.getChildren().setAll(pane);
                } catch (IOException ex) {
                    Logger.getLogger(PaiementController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PaiementController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void clearFields() {
        pmail.clear();
        pcomp.clear();
        ppays.clear();
        pdist.clear();
        pcode.clear();

    }

    private int produit(String conv) throws SQLException {
        con = ConnectionUtil.conDB();
        String qry = "SELECT * from commande where produit =" + "'" + conv + "'";
        ResultSet res = con.createStatement().executeQuery(qry);
        while (res.next()) {
            return res.getInt(1);
        }
        return 0;

    }

    private void AfficherCommande() {
        con = ConnectionUtil.conDB();
        String qry = "SELECT * from commande";
        try {
            ResultSet res = con.createStatement().executeQuery(qry);
            while (res.next()) {
                Commande commande = new Commande();
                commande.setId(res.getInt("id"));
                commande.setPrixprod(res.getInt("prixprod"));
                commande.setPrixlivr(res.getInt("prixlivr"));
                commande.setAmount(res.getInt("amount"));
                paiement_commande.getItems().addAll(res.getString("produit"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaiementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void AfficherMethode() {
        con = ConnectionUtil.conDB();
        String qry = "SELECT * from methode";
        try {
            ResultSet res = con.createStatement().executeQuery(qry);
            while (res.next()) {
                Methode methode = new Methode();
                methode.setId(res.getInt("id"));
                methode.setName(res.getString("name"));
              
                paiement_methode.getItems().addAll(res.getString("name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaiementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int methodes(String conv1) throws SQLException {
        con = ConnectionUtil.conDB();
        String qry = "SELECT * from methode where name =" + "'" + conv1 + "'";
        ResultSet res = con.createStatement().executeQuery(qry);
        while (res.next()) {
            return res.getInt(1);
        }
        return 0;
    }

   
}
