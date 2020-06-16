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
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import utils.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author aziz khbou
 */
public class CommandeController implements Initializable {

    @FXML
    private BorderPane borderpane1;
    @FXML
    private JFXTextField prixp;
    @FXML
    private JFXTextField prixl;
    @FXML
    private JFXTextField prod;
    @FXML
    private Label errors_livr;
    @FXML
    private Label errors_prix;
    @FXML
    private Label errors_produit;
    @FXML
    private Button btn_com;
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void ajouterCommande(ActionEvent event) {
        if (prixp.getText().isEmpty() || prixl.getText().isEmpty()) {
            if (prixp.getText().isEmpty()) {
                errors_prix.setTextFill(Color.TOMATO);
                errors_prix.setText("Le prix produit ne peut pas étre un champ vide");
            } else if (prixl.getText().isEmpty()) {
                errors_livr.setTextFill(Color.TOMATO);
                errors_livr.setText("La prix de la livraison ne peut pas étre un champ vide");
            } else {
                errors_prix.setTextFill(Color.TOMATO);
                errors_prix.setText("Le prix produit ne peut pas étre un champ vide");
                errors_produit.setTextFill(Color.TOMATO);
                errors_produit.setText("Le produit ne peut pas étre un champ vide");
            }
        } else {

            try {
                con = ConnectionUtil.conDB();

                String st = "INSERT INTO commande (prixprod,amount,prixlivr,produit)Values (?,?,?,?)";
                preparedStatement = (PreparedStatement) con.prepareStatement(st);
                preparedStatement.setInt(1, Integer.parseInt(prixp.getText()));
                preparedStatement.setInt(2, Integer.parseInt(prixl.getText()) + Integer.parseInt(prixp.getText()));
                preparedStatement.setInt(3, Integer.parseInt(prixl.getText()));
                preparedStatement.setString(4, prod.getText());
                preparedStatement.executeUpdate();
                new Alert(Alert.AlertType.INFORMATION, "sucess").show();

                clearFields();
                // AfficherCategorie() ;
                try {
                    BorderPane pane = FXMLLoader.load(getClass().getResource("/views/Paiement.fxml"));
                    borderpane1.getChildren().setAll(pane);
                } catch (IOException ex) {
                    Logger.getLogger(CommandeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(CommandeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void clearFields() {
        prixp.clear();
        prixl.clear();
        prod.clear();
    }


}
