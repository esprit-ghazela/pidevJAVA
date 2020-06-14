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
public interface IServicePaiement<P> {
    public void ajouter(P p);
    public void supprimer(int x );
    public void modifier(P p,int id);
    public List<P> afficher()throws SQLException;
    
}
