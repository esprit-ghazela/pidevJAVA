/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import models.Commande;
import models.Livraison;
import models.Paiement;
import services.ServiceLivraison;
import utils.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author aziz khbou
 */
public class LivraisonController implements Initializable {

    @FXML
    private BorderPane borderpan3;
    @FXML
    private JFXTextField etall;
    @FXML
    private JFXTextField adrl;
    @FXML
    private Label errors_livr;
    @FXML
    private Label errors_adr;
    @FXML
    private Label errors_etat;
    @FXML
    private JFXDatePicker datel;
    @FXML
    private Label errors_date;
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    java.sql.Date d1 = null;
    @FXML
    private ComboBox<String> paiement_livraison;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AfficherPaiement();  
    }

    @FXML
    private void ajouterlivraison(ActionEvent event) throws SQLException {
        String conv = paiement_livraison.getValue().toString();
        int paiement = email(conv);
        if (etall.getText().isEmpty() || adrl.getText().isEmpty()) {
            if (etall.getText().isEmpty()) {
                errors_etat.setTextFill(Color.TOMATO);
                errors_etat.setText("Etat ne peut pas étre un champ vide");
            } else if (datel.getValue() != null) {
                errors_date.setTextFill(Color.TOMATO);
                errors_date.setText("Le produit ne peut pas étre un champ vide");
            } else {
                errors_adr.setTextFill(Color.TOMATO);
                errors_adr.setText("Le prix de produit ne peut pas étre un champ vide");

            }
        } else {

            try {
                con = ConnectionUtil.conDB();
                Livraison l = new Livraison();

                String st = "INSERT INTO livraison (paiement_id,adresseLivr,etat,datelivr)Values (?,?,?,?)";
                preparedStatement = (PreparedStatement) con.prepareStatement(st);
                preparedStatement.setInt(1, paiement);
                preparedStatement.setString(2, adrl.getText());
                preparedStatement.setString(3, etall.getText());
                preparedStatement.setDate(4, java.sql.Date.valueOf(datel.getValue()));

                preparedStatement.executeUpdate();
                new Alert(Alert.AlertType.INFORMATION, "sucess").show();

                clearFields();
                // AfficherCommannde) ;
                try {
                    BorderPane pane = FXMLLoader.load(getClass().getResource("/views/Produit.fxml"));
                    borderpan3.getChildren().setAll(pane);
                } catch (IOException ex) {
                    Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(LivraisonController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void clearFields() {
        adrl.clear();
        etall.clear();
        datel.setValue(null);

    }

    private void AfficherPaiement() {
        con = ConnectionUtil.conDB();
        String qry = "SELECT * from paiement";
        try {
            ResultSet res = con.createStatement().executeQuery(qry);
            while (res.next()) {
                Paiement paiement = new Paiement();
                paiement.setId(res.getInt("id"));
                paiement.setEmail(res.getString("email"));
                paiement.setNom_companie(res.getString("nom_companie"));
                paiement.setPays(res.getString("pays"));
                paiement.setDistrict(res.getString("district"));
                paiement.setCodePostal(res.getInt("codePostal"));

                paiement_livraison.getItems().addAll(res.getString("email"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaiementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int email(String conv) throws SQLException {
        con = ConnectionUtil.conDB();
        String qry = "SELECT * from paiement where email =" + "'" + conv + "'";
        ResultSet res = con.createStatement().executeQuery(qry);
        while (res.next()) {
            return res.getInt(1);
        }
        return 0;

    }
}
