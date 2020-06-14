/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author aziz khbou
 */
interface IServiceCommande<C> {
    public void ajouter(C c);
    public void supprimer(int x);
    public void modifier(int prixprod,int prixlivr,String produit);
    public List<C> afficher()throws SQLException ;
    
}
