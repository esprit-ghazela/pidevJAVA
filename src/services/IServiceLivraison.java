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
public interface IServiceLivraison <L>{
     public void ajouter(L l);
    public void supprimer(int x);
    public void modifier(L l,int id);
    public List<L> afficher()throws SQLException;
}
