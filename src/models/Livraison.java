/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author aziz khbou
 */
public class Livraison {

    private int id;
    private String adresseLivr;
    private String etat;
    private Date datelivr;
    private int paiement_id;

    public Livraison(int id, String adresseLivr, String etat, Date datelivr, int paiement_id) {
        this.id = id;
        this.adresseLivr = adresseLivr;
        this.etat = etat;
        this.datelivr = datelivr;
        this.paiement_id = paiement_id;
    }

    public Livraison(int id, String adresseLivr, String etat, Date datelivr) {
        this.id = id;
        this.adresseLivr = adresseLivr;
        this.etat = etat;
        this.datelivr = datelivr;
    }
    public Livraison( String adresseLivr, String etat, Date datelivr) {
       
        this.adresseLivr = adresseLivr;
        this.etat = etat;
        this.datelivr = datelivr;
    }
       public Livraison( ) {
       
        
    }

   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdresseLivr() {
        return adresseLivr;
    }

    public void setAdresseLivr(String adresseLivr) {
        this.adresseLivr = adresseLivr;
    }

    public int getPaiement_id() {
        return paiement_id;
    }

    public void setPaiement_id(int paiement_id) {
        this.paiement_id = paiement_id;
    }

  

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Date getDatelivr() {
        return datelivr;
    }

    public void setDatelivr(Date datelivr) {
        this.datelivr = datelivr;
    }

    @Override
    public String toString() {
        return "Livraison{" + "id=" + id + ", adresseLivr=" + adresseLivr + ", etat=" + etat + ", datelivr=" + datelivr + ", paiement_id=" + paiement_id + '}';
    }

    

    
   

   

    

   

}
