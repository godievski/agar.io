/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Player;
import java.awt.Graphics;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Godievski
 */
public interface IGestorPlayer extends Remote{
    public Player getPlayerID(int id) throws RemoteException;
    public int addNewPlayer(String nickname,int xMax, int yMax) throws RemoteException;
    public int size() throws RemoteException;
    public void checkCollisions() throws RemoteException;
    public void render(Graphics g, double scale) throws RemoteException;
    public boolean mover(int id,double x, double y) throws RemoteException;
    public void checkCollisionVirus(int id) throws RemoteException;
    public void split(int id) throws RemoteException;
    public ArrayList getTop() throws RemoteException;
    public Player getPlayerIterator(int index) throws RemoteException;
    public void incrementTimeDuration(int id,int time) throws RemoteException;
    public void fusion(int id)throws RemoteException;
}
