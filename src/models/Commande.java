/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDateTime;

public class Commande {

    private int id;
    private int user_id;

    private int prixprod;
    private int amount;
    private LocalDateTime dateCom;
    private int prixlivr;
    private String produit;

    public Commande(int id, int user_id, int prixprod, int amount, LocalDateTime dateCom, int prixlivr, String produit) {
        this.id = id;
        this.user_id = user_id;
        this.prixprod = prixprod;
        this.amount = amount;
        this.dateCom = dateCom;
        this.prixlivr = prixlivr;
        this.produit = produit;
    }

    public Commande(int prixprod, int prixlivr, String produit) {

        this.prixprod = prixprod;

        this.prixlivr = prixlivr;
        this.produit = produit;
    }

    public Commande(int prixprod, int amount, LocalDateTime dateCom, int prixlivr, String produit) {
        this.prixprod = prixprod;
        this.amount = amount;
        this.prixlivr = prixlivr;
        this.produit = produit;
        this.dateCom = dateCom;

    }

    public Commande(int prixprod, int prixlivr, LocalDateTime dateCom, String produit) {
        this.prixprod = prixprod;
        this.prixlivr = prixlivr;
        this.produit = produit;
        this.dateCom = dateCom;

    }

    

    public Commande() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrixprod() {
        return prixprod;
    }

    public void setPrixprod(int prixprod) {
        this.prixprod = prixprod;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;

    }

    public int getPrixlivr() {
        return prixlivr;
    }

    public void setPrixlivr(int prixlivr) {
        this.prixlivr = prixlivr;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public LocalDateTime getDateCom() {
        return dateCom;
    }

    public void setDateCom(LocalDateTime dateCom) {
        this.dateCom = dateCom;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Commande{" + "id=" + id + ", user_id=" + user_id + ", prixprod=" + prixprod + ", amount=" + amount + ", dateCom=" + dateCom + ", prixlivr=" + prixlivr + ", produit=" + produit + '}';
    }

}
