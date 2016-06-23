/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Cell;
import Model.Player;
import java.awt.Graphics;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Godievski
 */
public interface IGestorVirus extends Remote{
    
    public void createVirus() throws RemoteException;
    public int size() throws RemoteException;
    public void deleteVirus(int id) throws RemoteException;
    public void render(Graphics g, double scale) throws RemoteException;
    public void checkCollisions(Player p) throws RemoteException;
    public Cell getVirus(int index) throws RemoteException;
}
