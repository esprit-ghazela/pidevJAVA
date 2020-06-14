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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import models.Livraison;
import models.Paiement;
import utils.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author aziz khbou
 */
public class GestionLivraisonController implements Initializable {

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Statement ste;
    @FXML
    private BorderPane borderpane;
    @FXML
    private JFXTextField filterField;
    @FXML
    private TableView<Livraison> liste_livraison;
    @FXML
    private TableColumn<?, ?> id_liv;
    @FXML
    private TableColumn<?, ?> adr;
    @FXML
    private TableColumn<?, ?> etatl;
    @FXML
    private TableColumn<?, ?> datel;
    ObservableList<Livraison> ListLivraison = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AfficherLivraison();
    }

    private void AfficherLivraison() {

        con = ConnectionUtil.conDB();

        String qry = "SELECT * from livraison";
        try {

            ResultSet res = con.createStatement().executeQuery(qry);

            while (res.next()) {
                Livraison l = new Livraison();
                l.setId(res.getInt("id"));
                l.setAdresseLivr(res.getString("adresseLivr"));
                l.setEtat(res.getString("etat"));
                l.setDatelivr(res.getDate("datelivr"));
               
                ListLivraison.add(l);
                id_liv.setCellValueFactory(new PropertyValueFactory<>("id"));
                adr.setCellValueFactory(new PropertyValueFactory<>("adresseLivr"));
                etatl.setCellValueFactory(new PropertyValueFactory<>("etat"));
                datel.setCellValueFactory(new PropertyValueFactory<>("datelivr"));
                

                liste_livraison.setItems(ListLivraison);

            }
        } catch (SQLException ex) {
            Logger.getLogger(GestionLivraisonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int LivraisonSelectionner() {
        int selectedItem = liste_livraison.getSelectionModel().getSelectedItem().getId();
        int selectedIndex = liste_livraison.getSelectionModel().getSelectedIndex();
        System.out.println(selectedItem);
        return selectedItem;
    }
 private void Recherche() {
        FilteredList<Livraison> filteredData = new FilteredList<>(ListLivraison, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(livraison -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (livraison.getAdresseLivr().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else if (livraison.getEtat().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
        SortedList<Livraison> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(liste_livraison.comparatorProperty());
        liste_livraison.setItems(sortedData);
    }
}
