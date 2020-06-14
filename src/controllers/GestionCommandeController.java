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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.StageStyle;
import javax.swing.JFileChooser;
import models.Categorie;
import models.Commande;
import services.CategorieService;
import services.ServiceCommande;
import utils.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author aziz khbou
 */
public class GestionCommandeController implements Initializable {

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Statement ste;
    @FXML
    private BorderPane borderpane;
    @FXML
    private JFXTextField filterField;
    @FXML
    private TableView<Commande> liste_commande;
    @FXML
    private TableColumn<?, ?> id_com;
    @FXML
    private TableColumn<?, ?> prixp;
    @FXML
    private TableColumn<?, ?> prixl;
    @FXML
    private TableColumn<?, ?> prod;
    @FXML
    private TableColumn<?, ?> datec;
    @FXML
    private TableColumn<?, ?> prixt;
    ObservableList<Commande> ListCommande = FXCollections.observableArrayList();
    ServiceCommande commandeService = new ServiceCommande();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AfficherCommande();
        Recherche();
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
                commande.setProduit(res.getString("produit"));
                commande.setDateCom(res.getTimestamp("dateCom").toLocalDateTime());

                ListCommande.add(commande);

                id_com.setCellValueFactory(new PropertyValueFactory<>("id"));
                prixp.setCellValueFactory(new PropertyValueFactory<>("prixprod"));
                prixl.setCellValueFactory(new PropertyValueFactory<>("prixlivr"));
                prixt.setCellValueFactory(new PropertyValueFactory<>("amount"));
                prod.setCellValueFactory(new PropertyValueFactory<>("produit"));
                datec.setCellValueFactory(new PropertyValueFactory<>("dateCom"));

                liste_commande.setItems(ListCommande);

            }
        } catch (SQLException ex) {
            Logger.getLogger(GestionCommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int CommandeSelectionner() {
        int selectedItem = liste_commande.getSelectionModel().getSelectedItem().getId();
        int selectedIndex = liste_commande.getSelectionModel().getSelectedIndex();
        System.out.println(selectedItem);
        return selectedItem;
    }

   

    @FXML
    private void ajouter(ActionEvent event) {
    }

    @FXML
    private void supprimer(ActionEvent event) {
        int x = CommandeSelectionner();
        Alert a1 = new Alert(Alert.AlertType.WARNING);
        a1.setTitle("Supprimer commande");
        a1.setContentText("Vous voulez vraiment supprimer cette commande ?");
        Optional<ButtonType> result = a1.showAndWait();
        if (result.get() == ButtonType.OK) {
            commandeService.supprimer(x);
            Alert a2 = new Alert(Alert.AlertType.INFORMATION);
            a2.setTitle("Supprimer commande");
            a2.setContentText("commande supprimé avec succés!");
            a2.show();

            ListCommande.clear();
            AfficherCommande();

        } else {
            a1.close();
        }

    }

    @FXML
    private void modifier(ActionEvent event) {
        try {
            Commande c = liste_commande.getSelectionModel().getSelectedItem();
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("/views/ModifierCommande.fxml"));
            Parent p = Loader.load();
            ModifierCommandeController display = Loader.getController();
            display.setCommande(c);
            Dialog dialog = new Dialog();
            dialog.getDialogPane().setContent(p);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.show();
        } catch (IOException ex) {
            Logger.getLogger(GestionCommandeController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    private void Recherche() {

        FilteredList<Commande> filteredData = new FilteredList<>(ListCommande, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(commande -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (commande.getProduit().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else if (String.valueOf(commande.getAmount()).indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
        SortedList<Commande> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(liste_commande.comparatorProperty());
        liste_commande.setItems(sortedData);
    }

    @FXML
    private void exporterPDF(ActionEvent event) {
        Commande selectedItem = liste_commande.getSelectionModel().getSelectedItem();
        System.out.println(selectedItem.getId());
        if (selectedItem != null) {

            ServiceCommande sc = new ServiceCommande();

            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("choose title");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
                System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
                sc.ecrirePdf(selectedItem.getId(), chooser.getSelectedFile());
            } else {
                System.out.println("No Selection ");
            }

        } else {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un element à exporter.");

            alert.showAndWait();
        }
    }

    @FXML
    private void reafficher_commande(MouseEvent event) {
        ListCommande.clear();
        AfficherCommande();
    }
}
