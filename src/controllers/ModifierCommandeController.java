/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Commande;
import services.ServiceCommande;

/**
 * FXML Controller class
 *
 * @author aziz khbou
 */
public class ModifierCommandeController implements Initializable {

    @FXML
    private Button btn_close;
    @FXML
    private JFXTextField prixpp;
    @FXML
    private Label errors_prod;
    @FXML
    private JFXTextField prixl;
    @FXML
    private Label errors_livr;
    @FXML
    private JFXTextArea prod;
    @FXML
    private Label errors_lprod;
    private Commande commande;
    @FXML
    private JFXButton modifier_commande_btn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void Close_btn(ActionEvent event) {
        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.close();
    }


    void setCommande(Commande c) {
        commande = c;
        System.out.println(c);
        prixpp.setText(String.valueOf(commande.getPrixprod()));
        prixl.setText(String.valueOf(commande.getPrixlivr()));
        prod.setText(commande.getProduit());
    }

   

    @FXML
    private void ModifierCommande(ActionEvent event) {
        if (prixpp.getText().isEmpty() || prixl.getText().isEmpty()) {
            if (errors_lprod.getText().isEmpty()) {
                errors_lprod.setTextFill(Color.TOMATO);
                errors_lprod.setText("Le prix produit ne peut pas étre un champ vide");

            } else if (prod.getText().isEmpty()){
                errors_livr.setTextFill(Color.TOMATO);
                errors_livr.setText("Le prix livraison ne peut pas étre un champ vide");
                errors_prod.setTextFill(Color.TOMATO);
                errors_prod.setText("Le produit ne peut pas étre un champ vide");
            }
        } else {

            ServiceCommande sc = new ServiceCommande();
            int prixprod = Integer.valueOf(prixpp.getText());
            int prixlivr = Integer.valueOf(prixl.getText());
            int amount = Integer.parseInt(prixl.getText()) + Integer.parseInt(prixpp.getText());

            String produit = prod.getText();

            Alert a1 = new Alert(Alert.AlertType.CONFIRMATION);
            a1.setTitle("Modification d'une commande");
            a1.setContentText("vous voulez vraiment Mdofier cette commande ?");
            Optional<ButtonType> result = a1.showAndWait();
            if (result.get() == ButtonType.OK) {
                sc.modifier(prixprod, prixlivr, produit);

            } else if (result.get() == ButtonType.CANCEL) {

            }

        }
    }
}
