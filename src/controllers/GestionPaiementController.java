/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import models.Categorie;
import models.Commande;
import models.Paiement;
import services.ServiceCommande;
import services.ServicePaiement;
import utils.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author aziz khbou
 */
public class GestionPaiementController implements Initializable {

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Statement ste;
    @FXML
    private BorderPane borderpane;
    @FXML
    private JFXTextField filterField;
    @FXML
    private TableView<Paiement> liste_paiement;
    @FXML
    private TableColumn<?, ?> id_pai;
    @FXML
    private TableColumn<?, ?> emailp;
    @FXML
    private TableColumn<?, ?> compp;
    @FXML
    private TableColumn<?, ?> paysp;
    @FXML
    private TableColumn<?, ?> distp;
    @FXML
    private TableColumn<?, ?> codep;
    ObservableList<Paiement> ListPaiement = FXCollections.observableArrayList();
    ServicePaiement paiementService = new ServicePaiement();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AfficherPaiement();
    }

    private void AfficherPaiement() {

        con = ConnectionUtil.conDB();

        String qry = "SELECT * from paiement";
        try {

            ResultSet res = con.createStatement().executeQuery(qry);

            while (res.next()) {
                Paiement p = new Paiement();
                p.setId(res.getInt("id"));
                p.setEmail(res.getString("email"));
                p.setNom_companie(res.getString("nom_companie"));
                p.setPays(res.getString("pays"));
                p.setDistrict(res.getString("district"));

                p.setCodePostal(res.getInt("codePostal"));

                ListPaiement.add(p);
                id_pai.setCellValueFactory(new PropertyValueFactory<>("id"));
                emailp.setCellValueFactory(new PropertyValueFactory<>("email"));
                compp.setCellValueFactory(new PropertyValueFactory<>("nom_companie"));
                paysp.setCellValueFactory(new PropertyValueFactory<>("pays"));
                distp.setCellValueFactory(new PropertyValueFactory<>("district"));
                codep.setCellValueFactory(new PropertyValueFactory<>("codePostal"));

                liste_paiement.setItems(ListPaiement);

            }
        } catch (SQLException ex) {
            Logger.getLogger(GestionPaiementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int PaiementSelectionner() {
        int selectedItem = liste_paiement.getSelectionModel().getSelectedItem().getId();
        int selectedIndex = liste_paiement.getSelectionModel().getSelectedIndex();
        System.out.println(selectedItem);
        return selectedItem;
    }

    private void Recherche() {
        FilteredList<Paiement> filteredData = new FilteredList<>(ListPaiement, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(paiement -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (paiement.getNom_companie().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else if (paiement.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
        SortedList<Paiement> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(liste_paiement.comparatorProperty());
        liste_paiement.setItems(sortedData);
    }

    @FXML
    private void modifier(MouseEvent event) {
    }

    @FXML
    private void ajouter(ActionEvent event) {
    }

    @FXML
    private void supprimer(ActionEvent event) {
         int x = PaiementSelectionner();
        Alert a1 = new Alert(Alert.AlertType.WARNING);
        a1.setTitle("Supprimer paiement");
        a1.setContentText("Vous voulez vraiment supprimer cet paiement ?");
        Optional<ButtonType> result = a1.showAndWait();
        if (result.get() == ButtonType.OK) {
            paiementService.supprimer(x);
            Alert a2 = new Alert(Alert.AlertType.INFORMATION);
            a2.setTitle("Supprimer commande");
            a2.setContentText("paiement supprimé avec succés!");
            a2.show();

            ListPaiement.clear();
            AfficherPaiement();

        } else {
            a1.close();
        }

    }

    @FXML
    private void modifier(ActionEvent event) {
    }

    @FXML
    private void reafficher_categorie(MouseEvent event) {
    }

    @FXML
    private void statistique(ActionEvent event) {
    }

}
