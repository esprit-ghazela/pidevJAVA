/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import models.Commande;
import utils.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aziz khbou
 */
public class ServiceCommande implements IServiceCommande<Commande> {

    private Statement pst;
    private PreparedStatement pre;
    Connection con = null;
    
    public ServiceCommande(){
        
        con = ConnectionUtil.conDB();

    }

    @Override
    public void ajouter(Commande c) {

        try {

            String requete = "INSERT INTO commande (user_id,prixprod,amount,prixlivr,produit)Values (?,?,?,?,?)";

            PreparedStatement pst = con.prepareStatement(requete);
            pst.setInt(1, c.getPrixprod());

            pst.setInt(2, c.getPrixprod());
            pst.setInt(3, c.getPrixprod() + c.getPrixlivr());
            pst.setInt(4, c.getPrixlivr());
            pst.setString(5, c.getProduit());
            pst.executeUpdate();
            System.out.println("Commande ajoutée !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public List afficher() throws SQLException {

        List<Commande> list = new ArrayList<>();
        pst = con.createStatement();

        ResultSet rs = pst.executeQuery("select * from commande");
        while (rs.next()) {
            int id = rs.getInt("id");
            int user_id = rs.getInt("user_id");
            int prixprod = rs.getInt("prixprod");
            int amount = rs.getInt("amount");
            int prixlivr = rs.getInt("prixlivr");
            String produit = rs.getString(7);
            LocalDateTime dateCom = rs.getTimestamp("dateCom").toLocalDateTime();

            Commande c = new Commande(id, user_id, prixprod, amount, dateCom, prixlivr, produit);
            list.add(c);
        }
        return list;
    }

    @Override
    public void supprimer(int x) {

        String sql = "DELETE FROM commande WHERE id = ? ";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, x);

            statement.executeUpdate();

            System.out.println("Commande Supprimer");

        } catch (SQLException ex) {
            Logger.getLogger(ServiceCommande.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Commande non Supprimer");
        }
    }

    @Override
    public void modifier(int prixprod, int prixlivr, String produit) {
        String sql = "UPDATE Commande SET prixprod = ? ,prixlivr =?,produit =? WHERE id=?";

        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, prixprod);
            statement.setInt(2, prixlivr);
            statement.setInt(3, prixprod + prixlivr);
            statement.setString(4, produit);
            statement.executeUpdate();
            System.out.println("Commande Modifiée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Commande> getInfo(int c) {
        String sql = "SELECT * from commande where id=" + "'" + c + "'";
        List<Commande> list = new ArrayList<>();

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Commande cc = new Commande();
                cc.setId(rs.getInt(1));
                cc.setPrixprod(rs.getInt(4));
                cc.setPrixlivr(rs.getInt(6));
                cc.setAmount(rs.getInt((5)));
                cc.setProduit(rs.getString(7));
                cc.setDateCom(rs.getTimestamp("dateCom").toLocalDateTime());

                list.add(cc);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServiceCommande.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void ecrirePdf(int idc, File SavePath) {

        List<Commande> list = new ArrayList<>();
        list = this.getInfo(idc);

        Document doc = new Document();
        PdfWriter docWriter = null;
        DecimalFormat df = new DecimalFormat("0.00");
        try {
            //special font sizes

            //file path
            System.out.println(SavePath.toString() + "\\Commande.pdf");
            System.out.println("\\");
            docWriter = PdfWriter.getInstance(doc, new FileOutputStream(SavePath.toString() + "\\commande.pdf"));
            //Image background = Image.getInstance("./publicitePdfBackground.png");
            //document header attributes
            doc.addAuthor("Overdose of knowledge");
            doc.addCreationDate();
            doc.addProducer();
            doc.addCreator("vélo.tn");
            doc.addTitle("Report with Column Headings");
            doc.setPageSize(PageSize.LETTER);
            doc.setMargins(50, 50, 150, 50);

            //open document
            doc.open();
            float width = doc.getPageSize().getWidth();
            float height = doc.getPageSize().getHeight();

            //create a paragraph
            float[] columnWidths = {4f, 1.5f};
            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(2);
            // set table width a percentage of the page width

            //just some random data to fill 
            for (int x = 0; x < list.size(); x++) {

                insertCell(table, "Prix Totale");
                table.addCell(String.valueOf(list.get(x).getPrixprod()));
                insertCell(table, "Prix Livraison");
                table.addCell(String.valueOf(list.get(x).getPrixlivr()));
                insertCell(table, "Prix Total");
                table.addCell(String.valueOf(list.get(x).getAmount()));
                insertCell(table, "Produit");
                table.addCell(list.get(x).getProduit());
                insertCell(table, "Date Commande");
                table.addCell(list.get(x).getDateCom().toString());
            }

            table.setSpacingAfter(30.2f);
            //paragraph.setSpacingAfter(30.2f);

            //add the PDF table to the paragraph
            DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();

            doc.add(table);
            // doc.add(paragraph);
            doc.add(new Phrase("Rapport crée avec les derniers mis à jour:" + sdf.format(date).toString()));

        } catch (DocumentException dex) {
            dex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (doc != null) {
                //close the document
                doc.close();
            }
            if (docWriter != null) {
                //close the writer
                docWriter.close();
            }
        }
    }

    private void insertCell(PdfPTable table, String text) {

        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim()));
        //set the cell alignment

        //in case there is no text and you wan to create an empty row
        //add the call to the table
        table.addCell(cell);

    }
}
