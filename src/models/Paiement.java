/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Objects;

/**
 *
 * @author aziz khbou
 */
public class Paiement {

    private int id;
    private String email;
    private String nom_companie;
    private String pays;
    private String district;
    private int codePostal;
    private int commande_id;
    private int methodedepaiement_id;

    public Paiement(int id, String email, String nom_companie, String pays, String district, int codePostal) {
        this.id = id;
        this.email = email;
        this.nom_companie = nom_companie;
        this.pays = pays;
        this.district = district;
        this.codePostal = codePostal;
    }

    public Paiement(int id, String email, String nom_companie, String pays, String district, int codePostal, int commande_id, int methodedepaiement_id) {
        this.id = id;
        this.email = email;
        this.nom_companie = nom_companie;
        this.pays = pays;
        this.district = district;
        this.codePostal = codePostal;
        this.commande_id = commande_id;
        this.methodedepaiement_id = methodedepaiement_id;
    }

    

    public Paiement(String email, String nom_companie, String pays, String district, int codePostal) {
        this.email = email;
        this.nom_companie = nom_companie;
        this.pays = pays;
        this.district = district;
        this.codePostal = codePostal;
    }

    public Paiement() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public int getCommande_id() {
        return commande_id;
    }

    public void setCommande_id(int commande_id) {
        this.commande_id = commande_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public String getNom_companie() {
        return nom_companie;
    }

    public void setNom_companie(String nom_companie) {
        this.nom_companie = nom_companie;
    }

    public int getMethodedepaiement_id() {
        return methodedepaiement_id;
    }

    public void setMethodedepaiement_id(int methodedepaiement_id) {
        this.methodedepaiement_id = methodedepaiement_id;
    }

    @Override
    public String toString() {
        return "Paiement{" + "id=" + id + ", email=" + email + ", nom_companie=" + nom_companie + ", pays=" + pays + ", district=" + district + ", codePostal=" + codePostal + ", commande_id=" + commande_id + ", methodedepaiement_id=" + methodedepaiement_id + '}';
    }

   
    

   

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.email);
        hash = 29 * hash + Objects.hashCode(this.nom_companie);
        hash = 29 * hash + Objects.hashCode(this.pays);
        hash = 29 * hash + Objects.hashCode(this.district);
        hash = 29 * hash + this.codePostal;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Paiement other = (Paiement) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.codePostal != other.codePostal) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.nom_companie, other.nom_companie)) {
            return false;
        }
        if (!Objects.equals(this.pays, other.pays)) {
            return false;
        }
        if (!Objects.equals(this.district, other.district)) {
            return false;
        }

        return true;
    }

}
